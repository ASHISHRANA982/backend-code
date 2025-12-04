package college.project.repository.donor;

import college.project.entity.AllDonorLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllDonorLoginRepo extends JpaRepository<AllDonorLogin,Integer> {

    public AllDonorLogin findAllLoginByUsername(String username);
    public boolean existsByUsername(String username);
    AllDonorLogin findByDonorId(Integer donorId);

}
