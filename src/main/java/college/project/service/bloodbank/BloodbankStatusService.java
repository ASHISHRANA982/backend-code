package college.project.service.bloodbank;

import college.project.MapServices.DistanceMatrixService;
import college.project.entity.BloodBankRegistration;
import college.project.entity.BloodbankStatus;
import college.project.entity.UserRegistration;
import college.project.exception.BloodBankNotFound;
import college.project.exception.DonorNotFound;
import college.project.repository.bloodbank.BloodBankStatusRepo;
import college.project.repository.bloodbank.BloodStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodbankStatusService {
    @Autowired
    private BloodBankStatusRepo bloodBankStatusRepo;
    @Autowired
    private DistanceMatrixService distanceMatrixService;
    @Autowired
    private BloodStockRepo bloodStockRepo;

    @Transactional
    public List<BloodbankStatus>saveBloodBankStatus(List<BloodBankRegistration>registrationList, String name,
                                                    LocalDateTime donationDate, int unitOfBlood,String location,String bloodType,String bloodGroup,String phoneNo){

        List<BloodbankStatus>bloodbankStatuses=new ArrayList<>();
        for(BloodBankRegistration list:registrationList){
            BloodbankStatus status=new BloodbankStatus();

            status.setRecipientName(name);
            status.setOrderQuantity(unitOfBlood);
            status.setDistance(
                    distanceMatrixService.getDistanceOfLocations(list.getAddress(),location)
            );
            status.setLocation(location);
            status.setDonationDate(donationDate.toLocalDate());
            status.setBloodType(bloodType);
            status.setBloodGroup(bloodGroup);
            status.setPhoneNo(phoneNo);
            status.setBloodBankRegistration(list);

            bloodbankStatuses.add(status);
        }
        return bloodBankStatusRepo.saveAll(bloodbankStatuses);
    }

    public List<BloodbankStatus>getCurrentStatus(int id){

//        return bloodBankStatusRepo.findDonationBloodbank(id, LocalDate.now());
        return bloodBankStatusRepo.findDonationBloodbank(id);

    }

    @Transactional
    public void updateBloodbankStatus(int id,int bloodSerialNo){
       BloodbankStatus bloodbankStatus= bloodBankStatusRepo.findById(id)
               .orElseThrow(()->new BloodBankNotFound("Bloodbank have no blood request"));

       bloodbankStatus.setAvailability("ACCEPTED");
       bloodbankStatus=bloodBankStatusRepo.save(bloodbankStatus);



        bloodStockRepo.updateQuantityByBloodBank(bloodbankStatus.getOrderQuantity(),bloodSerialNo);

       bloodBankStatusRepo.cancelPendingRequests(bloodbankStatus.getPhoneNo());

    }

    public record GetUserInfo(String phoneNo,LocalDate date){}
    @Transactional
    public GetUserInfo cancelBloodbankStatus(int id){
        BloodbankStatus bloodbankStatus=bloodBankStatusRepo.findById(id)
                .orElseThrow(()->new DonorNotFound("bloodbank have not blood request"));
        bloodbankStatus.setAvailability("CANCELLED");

        bloodBankStatusRepo.save(bloodbankStatus);
        return new GetUserInfo(bloodbankStatus.getPhoneNo(), bloodbankStatus.getDonationDate());
    }

    public List<BloodbankStatus> getBloodBankInfo(UserRegistration userRegistration){

        String phoneNo=userRegistration.getPhone_no();
        LocalDate registerDate=userRegistration.getLocalDateTime().toLocalDate();

        List<BloodbankStatus>bloodbankStatuses=bloodBankStatusRepo.findByDonationDateAndPhoneNo(registerDate,phoneNo);

        bloodbankStatuses.forEach(b->{
            BloodBankRegistration reg=b.getBloodBankRegistration();
            reg.setEmail(null);
            reg.setId(0);
            reg.setFacilities(null);
            reg.setIssuingAuthority(null);
            reg.setLicenceCopy(null);
            reg.setLicenceType(null);
            reg.setLicenceNo(null);
            reg.setLicenceValidityDate(null);
            reg.setStockList(null);
//            reg.setPhoneNo(null);
            reg.setWorkingHour(null);
            reg.setAddress(null);
        });


        return bloodbankStatuses;

    }

    public List<BloodbankStatus> getStatus(List<Integer>ids){
        List<BloodbankStatus>bloodbankStatuses= bloodBankStatusRepo.findAllByIdInAndAvailability(ids,"ACCEPTED");

        bloodbankStatuses.forEach(b->{
            BloodBankRegistration reg=b.getBloodBankRegistration();
            reg.setEmail(null);
            reg.setId(0);
            reg.setFacilities(null);
            reg.setIssuingAuthority(null);
            reg.setLicenceCopy(null);
            reg.setLicenceType(null);
            reg.setLicenceNo(null);
            reg.setLicenceValidityDate(null);
            reg.setStockList(null);
        });

        return bloodbankStatuses;
    }


}
