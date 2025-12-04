package college.project.repository.bloodbank;

import college.project.entity.AllBloodbankLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllBloodbankLoginRepo extends JpaRepository<AllBloodbankLogin,Integer> {

    public AllBloodbankLogin findAllLoginByUsername(String username);
    public boolean existsByUsername(String username);
    AllBloodbankLogin findByBloodBankId(Integer bloodBankId);
}
