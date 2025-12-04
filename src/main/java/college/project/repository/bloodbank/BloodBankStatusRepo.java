package college.project.repository.bloodbank;

import college.project.entity.BloodbankStatus;
import college.project.entity.DonorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BloodBankStatusRepo extends JpaRepository<BloodbankStatus,Integer> {

//    @Query("SELECT b FROM BloodbankStatus b " +
//            "WHERE b.bloodBankRegistration.id = :bloodId " +
//            "AND b.donationDate >= :currentDate")
//    List<BloodbankStatus> findDonationBloodbank(@Param("bloodId") int bloodId,
//                                                @Param("currentDate") LocalDate currentDate);

    @Query("SELECT b FROM BloodbankStatus b " +
            "WHERE b.bloodBankRegistration.id = :bloodId ")
    List<BloodbankStatus> findDonationBloodbank(@Param("bloodId") int bloodId);

    @Modifying
    @Query("UPDATE BloodbankStatus bs SET bs.availability = 'CANCELLED' " +
            "WHERE bs.phoneNo = :phoneNo " +
            "AND bs.availability = 'PENDING'")
    void cancelPendingRequests(@Param("phoneNo") String phoneNo);

    List<BloodbankStatus> findByDonationDateAndPhoneNo(LocalDate donationDate, String phoneNo);

    List<BloodbankStatus> findAllByIdInAndAvailability(List<Integer> ids, String availability);


}
