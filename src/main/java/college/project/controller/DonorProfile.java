package college.project.controller;

import college.project.TemporaryClass.SecurityIdChecker;
import college.project.dto.DonorRegistrationDTO;
import college.project.entity.DonorStatus;
import college.project.entity.Logout;
import college.project.repository.donor.DonorRegistrationRepo;
import college.project.repository.LogoutRepo;
import college.project.security.JwtAuth;
import college.project.service.donor.DonorRegistrationService;
import college.project.service.donor.DonorStatusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@PreAuthorize("hasRole('DONOR')")
@RequestMapping("/DonorProfile")
public class DonorProfile {

    @Autowired
    private DonorRegistrationService donorRegistrationService;
    @Autowired
    private DonorRegistrationRepo donorRegistrationRepo;
    @Autowired
    private JwtAuth jwtAuth;
    @Autowired
    private LogoutRepo logoutRepo;
    @Autowired
    private SecurityIdChecker securityIdChecker;
    @Autowired
    private DonorStatusService donorStatusService;

    @GetMapping("/profile")
    public ResponseEntity<DonorRegistrationDTO> ShowProfile(HttpServletRequest request){

       int id= securityIdChecker.checkOwnerId(request);

       DonorRegistrationDTO registrationDTO= donorRegistrationService.showProfile(id);
       if(registrationDTO==null){
          return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(registrationDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<String>UpdateProfile(@Valid  @RequestBody DonorRegistrationDTO registrationDTO ,HttpServletRequest request ){

      int id= securityIdChecker.checkOwnerId(request);

      DonorRegistrationDTO dto=  donorRegistrationService.updateProfile(id,registrationDTO);
        if(dto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Profile Update Successfully");
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

    //these controller only for show and update donor status
    public record DonorStatusDTO(
            int id,
            LocalDate donationDate,
            String recipientName,
            String availability,
            String distance,
            String location,
            String bloodGroup,
            String phoneNo,
            int donorId
    ) { }

    @GetMapping("/status")
    public ResponseEntity<List<DonorStatusDTO>> showDonorStatus(HttpServletRequest request){

        int id=securityIdChecker.checkOwnerId(request);

        List<DonorStatus>donorStatuses=donorStatusService.getCurrentStatus(id);
        if(donorStatuses==null){
            ResponseEntity.noContent();
        }

        List<DonorStatusDTO>donorStatusDTOS=donorStatuses.stream()
                .map(ds->new DonorStatusDTO(
                        ds.getId(),
                        ds.getDonationDate(),
                        ds.getRecipientName(),
                        ds.getAvailability(),
                        ds.getDistance(),
                        ds.getLocation(),
                        ds.getBloodGroup(),
                        ds.getPhoneNo(),
                        ds.getDonorRegistration().getId()
                )).toList();

        return ResponseEntity.ok(donorStatusDTOS);
    }

    @PutMapping("/updateStatus/{dsId}")
    public ResponseEntity<String>updateDonorStatus(@PathVariable int dsId){

        donorStatusService.updateDonorStatus(dsId);
        return ResponseEntity.ok("update successful");
    }

    @PutMapping("/cancelRequest/{id}")
    public ResponseEntity<String>updateSingleStatus(@PathVariable int id){
        donorStatusService.cancelDonorStatus(id);
        return ResponseEntity.ok("Request cancel successful");
    }


}


