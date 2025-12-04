package college.project.service;

import college.project.TemporaryClass.AvailableDetails.AvailabilityStatus;
import college.project.TemporaryClass.AvailableDetails.BloodbankDetails;
import college.project.TemporaryClass.AvailableDetails.DonorDetails;
import college.project.dto.UserRegistrationDTO;
import college.project.entity.BloodBankRegistration;
import college.project.entity.BloodbankStatus;
import college.project.entity.DonorRegistration;
import college.project.entity.DonorStatus;
import college.project.exception.BloodBankNotFound;
import college.project.exception.DonorNotFound;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.repository.bloodbank.BloodStockRepo;
import college.project.repository.donor.DonorRegistrationRepo;
import college.project.service.bloodbank.BloodbankStatusService;
import college.project.service.donor.DonorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TemporaryBloodRequestService {

    @Autowired
    private DonorRegistrationRepo donorRegistrationRepo;
    @Autowired
    private BloodBankRegistrationRepo bloodBankRegistrationRepo;
    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private DonorStatusService donorStatusService;
    @Autowired
    private BloodbankStatusService bloodbankStatusService;


    public AvailabilityStatus checkAvailability(UserRegistrationDTO userRegistrationDTO){

        if(userRegistrationDTO==null){
            throw new RuntimeException("invalid request try again");
        }

        String bloodGroup=userRegistrationDTO.getBlood_group().toUpperCase();
        int unitOfBlood=userRegistrationDTO.getBlood_unit();
        String currentLocation=userRegistrationDTO.getCurrent_location();
        LocalDateTime currentDateTime=userRegistrationDTO.getLocalDateTime();
        String bloodType=userRegistrationDTO.getBloodType();

        List<DonorRegistration>listOfDonor=new ArrayList<>();


        if(bloodType.equalsIgnoreCase("WHOLE BLOOD") && unitOfBlood==1){
            listOfDonor=filterDonorFromDB(bloodGroup,currentDateTime);
        }
      List<DonorStatus>donorStatuses= donorStatusService.saveDonorStatus(listOfDonor,currentLocation,currentDateTime,userRegistrationDTO.getName(),
                                           bloodGroup,userRegistrationDTO.getPhone_no() );


        List<BloodBankRegistration>listOfBloodbank =filterBloodbankFromDB(bloodGroup,bloodType);

        List<BloodbankStatus>bloodbankStatuses = bloodbankStatusService.saveBloodBankStatus(listOfBloodbank,userRegistrationDTO.getName(),
                    currentDateTime,unitOfBlood,currentLocation,bloodType,bloodGroup,userRegistrationDTO.getPhone_no());

        List<DonorDetails> donorDetails=donorStatuses.stream()
                .map(d->new DonorDetails(
                        d.getDonorRegistration().getName(),
                        d.getDonorRegistration().getEmail(),
                        d.getDistance()

                )).toList();
        List<BloodbankDetails>bloodbankDetails=bloodbankStatuses.stream()
                .map(b->new BloodbankDetails(
                        b.getBloodBankRegistration().getBloodBankName(),
                        b.getBloodBankRegistration().getEmail(),
                        b.getDistance()
                )).toList();

        return new AvailabilityStatus(userRegistrationDTO.getId(), donorDetails,bloodbankDetails);

    }

    public List<BloodBankRegistration> filterBloodbankFromDB(String bloodGroup, String bloodType) {

        if (bloodGroup == null || bloodGroup.isEmpty() || bloodType == null || bloodType.isEmpty()) {
            throw new BloodBankNotFound("Blood group or blood type cannot be empty");
        }

        List<Integer> integerList = bloodStockRepo.findEligibleBloodbankIds(bloodGroup, bloodType);

        return bloodBankRegistrationRepo.findAllById(integerList);
    }


    public List<DonorRegistration> filterDonorFromDB(String bloodGroup, LocalDateTime localDateTime) {

        if (bloodGroup == null || bloodGroup.isEmpty() || localDateTime == null) {
            throw new DonorNotFound("Blood group or date cannot be empty");
        }

        List<DonorRegistration> maleList = donorRegistrationRepo
                .findEligibleMaleDonor(bloodGroup, localDateTime.minusDays(90).toLocalDate());

        List<DonorRegistration> femaleList = donorRegistrationRepo
                .findEligibleFemaleDonor(bloodGroup, localDateTime.minusDays(120).toLocalDate());


        return Stream.concat(maleList.stream(), femaleList.stream())
                .distinct()
                .collect(Collectors.toList());
    }


}
