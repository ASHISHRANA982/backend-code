package college.project.repository.bloodbank;

import college.project.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepo extends JpaRepository<Facility,Integer> {

    List<Facility>findAllByBloodBankId(int id);
}
