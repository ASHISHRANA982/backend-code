package college.project.service.donor;


import college.project.MapServices.DistanceMatrixService;
import college.project.entity.DonorRegistration;
import college.project.entity.DonorStatus;
import college.project.entity.UserRegistration;
import college.project.exception.DonorNotFound;
import college.project.repository.donor.DonorRegistrationRepo;
import college.project.repository.donor.DonorStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DonorStatusService {
    @Autowired
    private DonorStatusRepo donorStatusRepo;
    @Autowired
    private DistanceMatrixService distanceMatrixService;
    @Autowired
    private DonorRegistrationRepo donorRegistrationRepo;

    @Transactional
    public List<DonorStatus> saveDonorStatus(List<DonorRegistration>donorRegistrations, String location, LocalDateTime localDateTime,
                                             String name,String bloodGroup,String phoneNo){

        List<DonorStatus>donorStatuses=new ArrayList<>();

        for(DonorRegistration list:donorRegistrations){
            DonorStatus donorStatus=new DonorStatus();

            donorStatus.setDonationDate(localDateTime.toLocalDate());
            donorStatus.setRecipientName(name);
            donorStatus.setDistance(distanceMatrixService.getDistanceOfLocations(
                    list.getAddress(),location
            ));
            donorStatus.setLocation(location);
            donorStatus.setBloodGroup(bloodGroup);
            donorStatus.setPhoneNo(phoneNo);
            donorStatus.setDonorRegistration(list);

            donorStatuses.add(donorStatus);
        }
     return donorStatusRepo.saveAll(donorStatuses);

    }


    public List<DonorStatus>getCurrentStatus(int id){

//      return donorStatusRepo.findDonationDonor(id, LocalDate.now());
        return donorStatusRepo.findDonor(id);
    }

    @Transactional
    public void updateDonorStatus(int id){
            DonorStatus donorStatus=donorStatusRepo.findById(id)
                    .orElseThrow(()->new DonorNotFound("donor have not blood request"));
            donorStatus.setAvailability("ACCEPTED");

          donorStatus=  donorStatusRepo.save(donorStatus);

            donorStatusRepo.cancelPendingRequests(donorStatus.getPhoneNo());

    }

    @Transactional
    public void updateDonorLastDonateDate(String phoneNo,LocalDate date){
       List <DonorStatus> donorStatus=donorStatusRepo.findByDonationDateAndPhoneNoStatus(date,phoneNo);


        DonorRegistration donorRegistration=donorRegistrationRepo.findById(donorStatus.get(0).getDonorRegistration().getId())
                .orElseThrow(()->new DonorNotFound("donor not found"));

        donorRegistration.setLastDonateDate(Date.from(donorStatus.get(0).getDonationDate().
                atStartOfDay(ZoneId.systemDefault()).toInstant()));

        donorRegistrationRepo.save(donorRegistration);
    }

    @Transactional
    public void cancelDonorStatus(int id){
        DonorStatus donorStatus=donorStatusRepo.findById(id)
                .orElseThrow(()->new DonorNotFound("donor have not blood request"));
        donorStatus.setAvailability("CANCELLED");

        donorStatusRepo.save(donorStatus);
    }

    public List<DonorStatus> getDonorInfo(UserRegistration userRegistration){

        String phoneNo=userRegistration.getPhone_no();
        LocalDate registerDate=userRegistration.getLocalDateTime().toLocalDate();

        List<DonorStatus>donorStatuses=donorStatusRepo.findByDonationDateAndPhoneNo(registerDate,phoneNo);

        donorStatuses.forEach(d->{
            DonorRegistration reg=d.getDonorRegistration();
            reg.setLastDonateDate(null);
            reg.setDateOfBirth(null);
            reg.setGender(null);
            reg.setAadharNo(null);
            reg.setId(0);
            reg.setBloodGroup(null);
            reg.setEmail(null);
//            reg.setPhone_no(null);
            reg.setAddress(null);
            reg.setBloodGroup(null);
        });

        return donorStatuses;
    }

    public List<DonorStatus> getStatus(List<Integer>ids){
        List<DonorStatus>donorStatuses=donorStatusRepo.findAllByIdInAndAvailability(ids,"ACCEPTED");

        donorStatuses.forEach(d->{
            DonorRegistration reg=d.getDonorRegistration();
            reg.setLastDonateDate(null);
            reg.setDateOfBirth(null);
            reg.setGender(null);
            reg.setAadharNo(null);
            reg.setId(0);
            reg.setBloodGroup(null);
            reg.setEmail(null);
        });

        return donorStatuses;
    }

}
