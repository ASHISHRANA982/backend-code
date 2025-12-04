package college.project.repository;

import college.project.entity.Logout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface LogoutRepo extends JpaRepository<Logout,String> {

    public boolean existsByToken(String token);
    void deleteByExpiryDateBefore(Date date);
}
