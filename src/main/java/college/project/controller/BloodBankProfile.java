package college.project.controller;


import college.project.TemporaryClass.SecurityIdChecker;
import college.project.dto.BloodBankRegistrationDTO;
import college.project.entity.BloodbankStatus;
import college.project.entity.Logout;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.repository.LogoutRepo;
import college.project.security.JwtAuth;
import college.project.service.bloodbank.BloodBankRegistrationService;
import college.project.service.bloodbank.BloodbankStatusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('BLOODBANK')")
@RequestMapping("/BloodbankProfile")
public class BloodBankProfile {

    @Autowired
    private BloodBankRegistrationService bloodBankRegistrationService;
    @Autowired
    private JwtAuth jwtAuth;
    @Autowired
    private LogoutRepo logoutRepo;
    @Autowired
    private BloodBankRegistrationRepo bloodBankRegistrationRepo;
    @Autowired
    private SecurityIdChecker securityIdChecker;
    @Autowired
    private BloodbankStatusService bloodbankStatusService;


    @GetMapping("/profile")
   public ResponseEntity<BloodBankRegistrationDTO> showProfile( HttpServletRequest request){

       int id= securityIdChecker.checkOwnerId(request);

        BloodBankRegistrationDTO dto=bloodBankRegistrationService.getProfile(id);

        if(dto==null){
            ResponseEntity.notFound().build();
        }

        dto.setStockList(null);
        dto.setFacilities(null);

        return ResponseEntity.ok(dto);
    }

    @PutMapping (value = "/update" , consumes = "multipart/form-data")
    public ResponseEntity<BloodBankRegistrationDTO> updateProfile(@Valid HttpServletRequest request, @RequestParam("bloodBankName") String bloodBankName,
                                                                  @RequestParam("address") String address,
                                                                  @RequestParam("phoneNo") String phoneNo,
                                                                  @RequestParam("email") String email,
                                                                  @RequestParam("licenceNo") String licenceNo,
                                                                  @RequestParam("licenceType") String licenceType,
                                                                  @RequestParam("licenceValidityDate") String licenceValidityDate,
                                                                  @RequestParam("issuingAuthority") String issuingAuthority,
                                                                  @RequestParam("workingHour") String workingHour,
                                                                  @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        int id=securityIdChecker.checkOwnerId(request);

        BloodBankRegistrationDTO updated = bloodBankRegistrationService.updateBloodBank(
                id, bloodBankName, address, phoneNo, email, licenceNo, licenceType,
                licenceValidityDate, issuingAuthority, workingHour, file
        );

        updated.setFacilities(null);
        updated.setStockList(null);
        return ResponseEntity.ok(updated);

    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization")String authHeader){
        if(authHeader!=null && authHeader.startsWith("Bearer")){
            String token=authHeader.substring(7);
            Date expiryDate=jwtAuth.getExpiryDate(token);

            Logout logout=new Logout(
                    token,
                    expiryDate
            );
            logoutRepo.save(logout);

            return ResponseEntity.ok("Logout successful");
        }
        return ResponseEntity.badRequest().body("Token Not Found");
    }


    public record BloodbankStatusDTO(
            int id,
            String recipientName,
            int orderQuantity,
            String availability,
            String distance,
            String location,
            LocalDate donationDate,
            String bloodType,
            String bloodGroup,
            String phoneNo,
            int bloodbankId
    ) { }

    @GetMapping("/status")
    public ResponseEntity<List<BloodbankStatusDTO>>showBloodbankStatus(HttpServletRequest request){

        int id=securityIdChecker.checkOwnerId(request);

        List<BloodbankStatus>bloodbankStatuses=bloodbankStatusService.getCurrentStatus(id);
        if(bloodbankStatuses==null){
            ResponseEntity.noContent();
        }

        List<BloodbankStatusDTO>bloodbankStatusDTOS=bloodbankStatuses.stream()
                .map(entity->new BloodbankStatusDTO(
                        entity.getId(),
                        entity.getRecipientName(),
                        entity.getOrderQuantity(),
                        entity.getAvailability(),
                        entity.getDistance(),
                        entity.getLocation(),
                        entity.getDonationDate(),
                        entity.getBloodType(),
                        entity.getBloodGroup(),
                        entity.getPhoneNo(),
                        entity.getBloodBankRegistration().getId()
                )).toList();

       return ResponseEntity.ok(bloodbankStatusDTOS);
    }

    @PutMapping("/updateStatus/{bsId}")
    public ResponseEntity<String> updateBloodbankStatus(@PathVariable int bsId ,@RequestBody Map<String,Integer>bloodId){

        bloodbankStatusService.updateBloodbankStatus(bsId,bloodId.get("bloodId"));
        return ResponseEntity.ok("Status update successful");
    }

    @PutMapping("/cancelRequest/{id}")
    public ResponseEntity<String>updateSingleStatus(@PathVariable int id){
        bloodbankStatusService.cancelBloodbankStatus(id);
        return ResponseEntity.ok("Request cancel successful");
    }


}
