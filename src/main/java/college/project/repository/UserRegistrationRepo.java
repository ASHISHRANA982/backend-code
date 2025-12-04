package college.project.repository;

import college.project.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepo extends JpaRepository<UserRegistration,Integer> {
    public boolean existsByEmail(String email);
}
