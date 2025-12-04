package college.project.repository.bloodbank;

import college.project.entity.BloodBankRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodBankRegistrationRepo extends JpaRepository<BloodBankRegistration,Integer> {

    boolean existsByLicenceNo(String licenceNo);

    BloodBankRegistration findByLicenceNo(String licenceNo);



}
