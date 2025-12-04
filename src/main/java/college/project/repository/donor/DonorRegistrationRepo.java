package college.project.repository.donor;

import college.project.entity.DonorRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonorRegistrationRepo extends JpaRepository<DonorRegistration,Integer> {

    boolean existsByEmail(String email);
   DonorRegistration findByAadharNo(String AadharNo);

    @Query("SELECT d FROM DonorRegistration d " +
            "WHERE UPPER(d.bloodGroup) = UPPER(:bloodGroup) " +
            "AND d.gender = 'male' " +
            "AND d.lastDonateDate <= :lastDonateDate")
    List<DonorRegistration> findEligibleMaleDonor(
            @Param("bloodGroup") String bloodGroup,
            @Param("lastDonateDate") LocalDate lastDonateDate);

    @Query("SELECT d FROM DonorRegistration d " +
            "WHERE UPPER(d.bloodGroup) = UPPER(:bloodGroup) " +
            "AND d.gender = 'female' " +
            "AND d.lastDonateDate <= :lastDonateDate")
    List<DonorRegistration> findEligibleFemaleDonor(
            @Param("bloodGroup") String bloodGroup,
            @Param("lastDonateDate") LocalDate lastDonateDate);

}
