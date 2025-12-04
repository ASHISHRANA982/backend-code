package college.project.controller;


import college.project.EmailSender.AllDataFormat;
import college.project.EmailSender.EmailService;
import college.project.ImageConfig.CloudinaryService;
import college.project.ImageConfig.ImageEntityService;
import college.project.TemporaryClass.BloodbankLoginClass;
import college.project.dto.*;
import college.project.entity.AllBloodbankLogin;
import college.project.entity.BloodBankRegistration;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.service.bloodbank.BloodBankRegistrationService;
import college.project.service.security.LoginService;
import college.project.service.security.OtpService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/bloodbank")
public class BloodBankController {

    @Autowired
    private BloodBankRegistrationService registrationService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private BloodBankRegistrationRepo bloodBankRegistrationRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AllDataFormat allDataFormat;
    @Autowired
    private OtpService otpService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImageEntityService imageEntityService;

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<String> bloodBankRegister(@Valid
            @RequestParam("bloodBankName") String bloodBankName,
            @RequestParam("address") String address,
            @RequestParam("phoneNo") String phoneNo,
            @RequestParam("email") String email,
            @RequestParam("licenceNo") String licenceNo,
            @RequestParam("licenceType") String licenceType,
            @RequestParam("licenceValidityDate") String licenceValidityDate,
            @RequestParam("issuingAuthority") String issuingAuthority,
            @RequestParam("workingHour") String workingHour,
            @RequestParam("stockList") String stockListJson,
            @RequestParam("facilities") String facilitiesJson,
            @RequestPart("file") MultipartFile file
    ) throws IOException {


        String publicId="";

        BloodBankRegistrationDTO registrationDTO = new BloodBankRegistrationDTO();
        registrationDTO.setBloodBankName(bloodBankName);
        registrationDTO.setAddress(address);
        registrationDTO.setPhoneNo(phoneNo);
        registrationDTO.setEmail(email);
        registrationDTO.setLicenceNo(licenceNo);
        registrationDTO.setLicenceType(licenceType);
        registrationDTO.setLicenceValidityDate(java.sql.Date.valueOf(licenceValidityDate));
        registrationDTO.setIssuingAuthority(issuingAuthority);
        registrationDTO.setWorkingHour(workingHour);

        ObjectMapper mapper = new ObjectMapper();
        List<BloodStockDTO> stockList = mapper.readValue(stockListJson,
                new TypeReference<List<BloodStockDTO>>() {});
        registrationDTO.setStockList(stockList);


        List<FacilityDTO> facilities = mapper.readValue(facilitiesJson,
                new TypeReference<List<FacilityDTO>>() {});
        registrationDTO.setFacilities(facilities);

boolean emailSent=false;
        try {

            Map result =cloudinaryService.uploadFile(file);
            String fileName=(String)result.get("secure_url");
            publicId=(String)result.get("public_id");
            registrationDTO.setLicenceCopy(fileName);

            BloodBankRegistrationDTO newDto = registrationService.saveBloodBank(registrationDTO);
            imageEntityService.saveImage(publicId,newDto);
            System.out.println("image posted to cloudinary");

            if (newDto == null) {
                cloudinaryService.deleteFile(publicId);
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Registration failed");
            }


            String password = newDto.getPhoneNo().substring(0, 3);
            AllBloodbankLogin allBloodbankLogin = loginService.saveBloodBankLogin(newDto);


            try {
                emailService.sendEmailWithHtml(
                        allBloodbankLogin.getUsername(),
                        "Your One-Time Password for Verification\n",
                        allDataFormat.OneTimePasswordSend(
                                "bloodbank@" + password,
                                allBloodbankLogin.getUsername(),
                                newDto.getBloodBankName()
                        )
                );
                emailSent = true;
            } catch (Exception e) {
                System.err.println(" Email sending failed: " + e.getMessage());
            }

            if (emailSent) {
                return ResponseEntity.ok("Registration successful and email sent successfully");
            } else {
                return ResponseEntity.ok("Registration successful but email sending failed");
            }

        } catch (Exception e) {
            cloudinaryService.deleteFile(publicId);
            System.err.println(" Registration failed: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed "+e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> bloodBankLogin(@RequestBody AllBloodbankLoginDTO loginDTO){
      ResponseEntity<String> res=loginService.bloodBankLogin(loginDTO,"BLOODBANK");
      return res;
    }


    public record getLicenceNo(String licenceNo){}
    @PostMapping("/requestOtp")
    public ResponseEntity<String> requestOtp(@RequestBody getLicenceNo licenceNo) {

        System.out.println(licenceNo);

        BloodBankRegistration bank = bloodBankRegistrationRepo.findByLicenceNo(licenceNo.licenceNo());


        if (bank == null || bank.getEmail() == null || bank.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid licence number or email not found");
        }

        String otp = otpService.generateOtp(bank.getEmail());

        emailService.sendEmailWithHtml(bank.getEmail(), "Verification Code - Update Login",
                allDataFormat.sendOTP(otp,bank.getBloodBankName()));

        return ResponseEntity.ok("OTP sent to " + bank.getEmail());
    }

    public record GetLoginOtpBlood(BloodbankLoginClass loginClass,String otp){};
    @PutMapping("/updateLogin")
    public ResponseEntity<String>updateLogin(@Valid @RequestBody GetLoginOtpBlood getLoginOtp){

        BloodbankLoginClass loginClass=getLoginOtp.loginClass();
        String otp=getLoginOtp.otp();

        BloodBankRegistration bloodBankRegistration=bloodBankRegistrationRepo.
                findByLicenceNo(loginClass.getLicenceNo());


        if (bloodBankRegistration == null || bloodBankRegistration.getEmail() == null
                || bloodBankRegistration.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Licence No");
        }
        boolean isValid=otpService.verifyOtp(bloodBankRegistration.getEmail(),otp);
        if(!isValid){
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }


        AllBloodbankLoginDTO allBloodbankLogin=registrationService.
                updateLogin(bloodBankRegistration.getId(),loginClass.getNewUsername()
                        ,loginClass.getNewPassword());

        if (allBloodbankLogin==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update username and password");
        }

        return ResponseEntity.ok("Username and Password Update Successfully");
    }


}
