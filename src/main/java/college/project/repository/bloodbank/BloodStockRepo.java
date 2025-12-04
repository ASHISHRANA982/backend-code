package college.project.repository.bloodbank;

import college.project.entity.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BloodStockRepo extends JpaRepository<BloodStock,Integer> {

    BloodStock findByBloodBankId(int id);
    List<BloodStock> findAllByBloodBankId(int id);

    @Query("SELECT b.bloodBank.id FROM BloodStock b " +
            "WHERE b.bloodGroup = :bloodGroup " +
            "AND b.componentType = :componentType " +
            "AND b.status = 'AVAILABLE'")
    List<Integer> findEligibleBloodbankIds(
            @Param("bloodGroup") String bloodGroup,
            @Param("componentType") String componentType);

    @Modifying
    @Query("UPDATE BloodStock b SET b.quantity = b.quantity - :quantity " +
            "WHERE b.id = :id ")
    void updateQuantityByBloodBank(@Param("quantity") int quantity,
                                   @Param("id") int id);

    @Query("SELECT b FROM BloodStock b " +
            "WHERE b.bloodGroup = :bloodGroup " +
            "AND b.componentType = :componentType " +
            "AND b.status = 'AVAILABLE'")
    List<BloodStock> findByBloodGroup(
            @Param("bloodGroup") String bloodGroup,
            @Param("componentType") String componentType);



}
