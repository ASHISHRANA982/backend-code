package college.project.controller;

import college.project.TemporaryClass.AvailableDetails.AvailabilityStatus;
import college.project.entity.BloodbankStatus;
import college.project.entity.DonorStatus;
import college.project.entity.UserRegistration;
import college.project.exception.UserNotFound;
import college.project.repository.UserRegistrationRepo;
import college.project.service.BloodRequestSender;
import college.project.service.SearchBloodService;
import college.project.service.bloodbank.BloodbankStatusService;
import college.project.service.donor.DonorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/userProfile")
public class UserProfile {

    @Autowired
    private BloodRequestSender bloodRequestSender;
    @Autowired
    private BloodbankStatusService bloodbankStatusService;
    @Autowired
    private DonorStatusService donorStatusService;
    @Autowired
    private UserRegistrationRepo userRegistrationRepo;
    @Autowired
    private SearchBloodService searchBloodService;

    @PostMapping("/requestForBlood")
    public ResponseEntity<String> sendingRequest(@RequestBody AvailabilityStatus availabilityStatus){

        bloodRequestSender.sendRequestToAll(availabilityStatus);

        return ResponseEntity.ok("request send successful");
    }

    public record statusDetails(List<DonorStatus>donorStatuses, List<BloodbankStatus>bloodbankStatuses){}
    @GetMapping("/requestedInfo/{id}")
    public ResponseEntity<statusDetails>requestedInfo(@PathVariable int id){
        UserRegistration userRegistration=userRegistrationRepo.findById(id).
                orElseThrow(()->new UserNotFound("validate user id"));

        List<DonorStatus> donorStatusesList=new ArrayList<>();

        if(userRegistration.getBlood_unit()==1 && userRegistration.getBloodType().equalsIgnoreCase("Whole Blood")) {
           donorStatusesList = donorStatusService.getDonorInfo(userRegistration);
        }
       List<BloodbankStatus>bloodbankStatusesList=bloodbankStatusService.getBloodBankInfo(userRegistration);

        return ResponseEntity.ok(new statusDetails(donorStatusesList,bloodbankStatusesList));

    }

    public record allStatusIds(List<Integer>donorStatusIds,List<Integer>bloodbankStatusIds){}
    @PostMapping("/checkStatus")
    public ResponseEntity<statusDetails>checkStatus(@RequestBody allStatusIds statusIds){

        List<DonorStatus> donorStatus=new ArrayList<>();
       List<BloodbankStatus> bloodbankStatus=new ArrayList<>();

        if(statusIds.donorStatusIds()!=null && !statusIds.donorStatusIds().isEmpty()){
               donorStatus= donorStatusService.getStatus(statusIds.donorStatusIds());
        }

        if(statusIds.bloodbankStatusIds()!=null && !statusIds.bloodbankStatusIds().isEmpty()){
                bloodbankStatus=bloodbankStatusService.getStatus(statusIds.bloodbankStatusIds());
        }



        return ResponseEntity.ok(new statusDetails(donorStatus,bloodbankStatus));
    }


    @PutMapping("/chooseDelivery")
    public ResponseEntity<String>choosingRequest(@RequestBody allStatusIds allStatusIds){
        if (allStatusIds == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be null");
        }

        if(allStatusIds.bloodbankStatusIds()!=null && !allStatusIds.bloodbankStatusIds().isEmpty()){

          BloodbankStatusService.GetUserInfo userInfo = bloodbankStatusService.cancelBloodbankStatus(allStatusIds.bloodbankStatusIds().get(0));
            donorStatusService.updateDonorLastDonateDate(userInfo.phoneNo(),userInfo.date());

        } else if (allStatusIds.donorStatusIds()!=null && !allStatusIds.donorStatusIds().isEmpty()) {

            donorStatusService.cancelDonorStatus(allStatusIds.donorStatusIds().get(0));
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Select Any One Of Them");

        }

        return ResponseEntity.ok("request cancel successfully");
    }

}
