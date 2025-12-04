package college.project.controller;

import college.project.EmailSender.AllDataFormat;
import college.project.EmailSender.EmailService;
import college.project.TemporaryClass.AvailableDetails.AvailabilityStatus;
import college.project.dto.UserRegistrationDTO;
import college.project.service.TemporaryBloodRequestService;
import college.project.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRegistrationService registrationService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AllDataFormat allDataFormat;
    @Autowired
    private TemporaryBloodRequestService temporaryBloodRequestService;

    @PostMapping("/register")
    public ResponseEntity<AvailabilityStatus> UserRegister(@Valid  @RequestBody UserRegistrationDTO userRegistrationDTO){

           userRegistrationDTO= registrationService.UserRegister(userRegistrationDTO);

          AvailabilityStatus availabilityStatus= temporaryBloodRequestService.checkAvailability(userRegistrationDTO);

            return  ResponseEntity.status(HttpStatus.CREATED).body(availabilityStatus);

    }


}
