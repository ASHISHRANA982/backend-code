package college.project.repository.donor;

import college.project.entity.DonorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonorStatusRepo extends JpaRepository<DonorStatus,Integer> {

    @Query("SELECT d FROM DonorStatus d " +
            "WHERE d.donorRegistration.id= :donorId " +
            "AND donationDate >= :currentDate")
    List<DonorStatus>findDonationDonor(@Param("donorId")int donorId, @Param("currentDate")LocalDate currentDate);

    @Query("SELECT d FROM DonorStatus d " +
            "WHERE d.donorRegistration.id= :donorId ")
    List<DonorStatus>findDonor(@Param("donorId") int donorId);

    @Modifying
    @Query("UPDATE DonorStatus dr SET dr.availability = 'CANCELLED' " +
            "WHERE dr.phoneNo = :phoneNo " +
            "AND dr.availability = 'PENDING'")
    void cancelPendingRequests(@Param("phoneNo") String phoneNo);

    @Modifying
    @Query("UPDATE DonorStatus ds SET ds.availability = 'CANCELLED' " +
            "WHERE ds.id = :id ")
    void updateSingleDonorRequest(@Param("id")int id);

    List<DonorStatus> findByDonationDateAndPhoneNo(LocalDate donationDate,String phoneNo);

    @Query("SELECT d FROM DonorStatus d " +
            "WHERE d.donationDate = :donationDate AND d.phoneNo = :phoneNo AND d.availability = 'ACCEPTED' ")
    List<DonorStatus> findByDonationDateAndPhoneNoStatus(@Param("donationDate") LocalDate donationDate,@Param("phoneNo") String phoneNo);


    List<DonorStatus> findAllByIdInAndAvailability(List<Integer> ids, String availability);


}
