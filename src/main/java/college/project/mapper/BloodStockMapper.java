package college.project.mapper;

import college.project.dto.BloodStockDTO;
import college.project.entity.BloodBankRegistration;
import college.project.entity.BloodStock;

public class BloodStockMapper {

    public static BloodStockDTO toBloodStockDTO(BloodStock bloodStock){
        BloodStockDTO bloodStockDTO=new BloodStockDTO(
               bloodStock.getId(),
                bloodStock.getComponentType(),
                bloodStock.getBloodGroup(),
                bloodStock.getQuantity(),
                bloodStock.getCollectionDate(),
                bloodStock.getExpiryDate(),
                bloodStock.getStatus(),
                bloodStock.getBloodBank().getId()
        );
        return bloodStockDTO;
    }

    public static BloodStock toBloodStock(BloodStockDTO bloodStockDTO){
        BloodStock bloodStock=new BloodStock();

        bloodStock.setId(bloodStockDTO.getId());
        bloodStock.setComponentType(bloodStockDTO.getComponentType());
        bloodStock.setBloodGroup(bloodStockDTO.getBloodGroup());
        bloodStock.setQuantity(bloodStockDTO.getQuantity());
        bloodStock.setCollectionDate(bloodStockDTO.getCollectionDate());
        bloodStock.setExpiryDate(bloodStockDTO.getExpiryDate());
        bloodStock.setStatus(bloodStockDTO.getStatus());
        if (bloodStockDTO.getBloodBankId() != 0) {
            BloodBankRegistration bank = new BloodBankRegistration();
            bank.setId(bloodStockDTO.getBloodBankId());
            bloodStock.setBloodBank(bank);
        }

        return bloodStock;
    }
}
