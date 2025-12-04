package college.project.controller;

import college.project.EmailSender.AllDataFormat;
import college.project.EmailSender.EmailService;
import college.project.TemporaryClass.DonorLoginClass;
import college.project.dto.AllDonorLoginDTO;
import college.project.dto.DonorRegistrationDTO;
import college.project.entity.AllDonorLogin;
import college.project.entity.DonorRegistration;
import college.project.repository.donor.DonorRegistrationRepo;
import college.project.service.donor.DonorRegistrationService;
import college.project.service.security.LoginService;
import college.project.service.security.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorRegistrationService registrationService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private DonorRegistrationRepo donorRegistrationRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AllDataFormat allDataFormat;
    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody DonorRegistrationDTO registrationDTO) {

            DonorRegistrationDTO savedDonor = registrationService.DonorRegister(registrationDTO);

          String password=savedDonor.getPhone_no().substring(0,3);

           AllDonorLogin allDonorLogin = loginService.saveDonorLogin(savedDonor);

           emailService.sendEmailWithHtml(allDonorLogin.getUsername(),"Your One-Time Password for Verification\n",
                   allDataFormat.OneTimePasswordSend("donor@"+password,allDonorLogin.getUsername(),
                           savedDonor.getName()));

            return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AllDonorLoginDTO loginDTO){

        System.out.println(loginDTO.getUsername());
       ResponseEntity<String>res= loginService.donorLogin(loginDTO,"DONOR");
       return res;
    }

    public record GetAadhar(String aadhar){};
    @PostMapping("/requestOtp")
    public ResponseEntity<String> requestOtp(@RequestBody GetAadhar aadharNo) {

        DonorRegistration donorRegistration=donorRegistrationRepo.findByAadharNo(aadharNo.aadhar());

        if (donorRegistration == null || donorRegistration.getEmail() == null || donorRegistration.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Aadhar No number or email not found");
        }

        String otp = otpService.generateOtp(donorRegistration.getEmail());

        emailService.sendEmailWithHtml(donorRegistration.getEmail(), "Verification Code - Update Login",
                allDataFormat.sendOTP(otp,donorRegistration.getName()));

        return ResponseEntity.ok("OTP sent to " + donorRegistration.getEmail());
    }

    public record GetLoginOtpDonor(DonorLoginClass loginClass, String otp){};
    @PutMapping("/updateLogin")
    public ResponseEntity<String> UpdateLogin(@Valid @RequestBody GetLoginOtpDonor getLoginOtpDonor){

        DonorLoginClass loginClass=getLoginOtpDonor.loginClass();
        String otp=getLoginOtpDonor.otp();

        DonorRegistration donorRegistration=donorRegistrationRepo.findByAadharNo(loginClass.getAadharNo());

        if (donorRegistration == null || donorRegistration.getAadharNo() == null || donorRegistration.getAadharNo().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Donor Not Found Enter Valid Aadhar Number");
        }

        boolean isValid=otpService.verifyOtp(donorRegistration.getEmail(),otp);
        if(!isValid){
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        AllDonorLoginDTO loginDTO= registrationService.updateLogin(donorRegistration,
                loginClass.getUsername(),loginClass.getPassword());
        if (loginDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update login");
        }
        return ResponseEntity.ok("Username and Password Update Successfully");
    }


}
