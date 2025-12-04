package college.project.TemporaryClass;

import college.project.dto.BloodStockDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BloodStockConversion {

    public BloodStockDTO getExpiryDate(BloodStockDTO bloodStockDTOS) {

            LocalDateTime collectionDate = bloodStockDTOS.getCollectionDate();
            if (collectionDate == null) {

                collectionDate = LocalDateTime.now();
                bloodStockDTOS.setCollectionDate(collectionDate);
            }

            LocalDateTime expiryDate;

            switch (bloodStockDTOS.getComponentType().toUpperCase()) {
                case "WHOLE BLOOD":
                case "RBC":
                    expiryDate = collectionDate.plusDays(35);
                    break;
                case "PLATELETS":
                    expiryDate = collectionDate.plusDays(5);
                    break;
                case "PLASMA":
                case "CRYOPRECIPITATE":
                    expiryDate = collectionDate.plusYears(1);
                    break;
                default:
                    expiryDate = collectionDate.plusDays(30);
            }

            bloodStockDTOS.setExpiryDate(expiryDate);

            return bloodStockDTOS;

    }

    public BloodStockDTO getBloodStockStatus(BloodStockDTO bloodStockDTOS){

        if(!CheckExpiry(bloodStockDTOS) && bloodStockDTOS.getQuantity()>0){
            bloodStockDTOS.setStatus("AVAILABLE");
        } else if (!CheckExpiry(bloodStockDTOS) && bloodStockDTOS.getQuantity()==0) {
            bloodStockDTOS.setStatus("USED");
        }
        else {

            bloodStockDTOS.setStatus("EXPIRED");
            if (bloodStockDTOS.getQuantity() > 0) {
                bloodStockDTOS.setQuantity(0);
            }
        }
        return bloodStockDTOS;
    }

    public boolean CheckExpiry(BloodStockDTO bloodStockDTO){
        if (LocalDateTime.now().isAfter(bloodStockDTO.getExpiryDate())) {
            return true;
        } else {
           return false;
        }

    }

}
