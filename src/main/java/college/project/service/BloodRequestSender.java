package college.project.service;

import college.project.EmailSender.AllDataFormat;
import college.project.EmailSender.EmailServiceImpl;
import college.project.TemporaryClass.AvailableDetails.AvailabilityStatus;
import college.project.TemporaryClass.AvailableDetails.BloodbankDetails;
import college.project.TemporaryClass.AvailableDetails.DonorDetails;
import college.project.entity.UserRegistration;
import college.project.exception.UserNotFound;
import college.project.repository.UserRegistrationRepo;
import college.project.repository.bloodbank.BloodBankStatusRepo;
import college.project.repository.donor.DonorStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BloodRequestSender {

    @Autowired
    private BloodBankStatusRepo bloodBankStatusRepo;
    @Autowired
    private DonorStatusRepo donorStatusRepo;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private UserRegistrationRepo userRegistrationRepo;
    @Autowired
    private AllDataFormat allDataFormat;


    public  void sendRequestToAll(AvailabilityStatus availabilityStatus){

        List<DonorDetails>donorDetails =availabilityStatus.donorDetails();
        List<BloodbankDetails>bloodbankDetails=availabilityStatus.bloodbankDetails();

        UserRegistration userRegistration= userRegistrationRepo.findById(availabilityStatus.requestId())
                .orElseThrow(()->new UserNotFound("User Not Found Please Register First"));

        if (donorDetails != null && !donorDetails.isEmpty()) {
            sendToDonor(donorDetails, userRegistration);
        }

        if (bloodbankDetails != null && !bloodbankDetails.isEmpty()) {
            sendToBloodbank(bloodbankDetails, userRegistration);
        }


    }

    public void sendToDonor(List<DonorDetails>donorDetails,UserRegistration userRegistration){

        List<String> donorEmails = donorDetails.stream()
                .map(DonorDetails::email)
                .toList();

        String subject = "Urgent Blood Request for " + userRegistration.getBlood_group();
        String message = allDataFormat.RequestForBlood(
                userRegistration.getName(),
                userRegistration.getCurrent_location(),
                userRegistration.getBlood_group(),
                userRegistration.getBlood_unit(),
                userRegistration.getLocalDateTime()
        );


        emailService.sendEmailWithHtml(donorEmails.toArray(new String[0]),subject, message);
    }

    public void sendToBloodbank(List<BloodbankDetails>bloodbankDetails,UserRegistration userRegistration){


        List<String>bloodbankEmail=bloodbankDetails.stream()
                .map(BloodbankDetails::email)
                .toList();


        String subject = "Urgent Blood Request for " + userRegistration.getBlood_group();
        String message = allDataFormat.RequestForBlood(
                userRegistration.getName(),
                userRegistration.getCurrent_location(),
                userRegistration.getBlood_group(),
                userRegistration.getBlood_unit(),
                userRegistration.getLocalDateTime()
        );


        emailService.sendEmailWithHtml(bloodbankEmail.toArray(new String[0]),subject, message);
    }

}
