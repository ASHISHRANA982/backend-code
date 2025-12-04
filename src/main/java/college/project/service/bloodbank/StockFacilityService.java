package college.project.service.bloodbank;

import college.project.TemporaryClass.BloodStockConversion;
import college.project.entity.BloodStock;
import college.project.entity.Facility;
import college.project.exception.BloodBankNotFound;
import college.project.mapper.BloodStockMapper;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.repository.bloodbank.BloodStockRepo;
import college.project.repository.bloodbank.FacilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class StockFacilityService {

    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private FacilityRepo facilityRepo;
    @Autowired
    private BloodStockConversion bloodStockConversion;
    @Autowired
    private BloodBankRegistrationRepo bloodBankRegistrationRepo;

    public List<BloodStock> getStocks(int bId){
        List<BloodStock>list=(List<BloodStock>) bloodStockRepo.findAllByBloodBankId(bId);

        if (list.isEmpty()) {
            throw new RuntimeException("No stock found for blood bank ID ");
        }
        return list;

    }

    public List<Facility>getFacilities(int bId){
        List<Facility>list=facilityRepo.findAllByBloodBankId(bId);
        if (list.isEmpty()) {
            throw new RuntimeException("No facility found for blood bank ID");
        }
        return list;
    }

    @Transactional
    public BloodStock updateBloodStock(int id, int quantity){

        BloodStock bloodStock=bloodStockRepo.findById(id).
                orElseThrow(()->new   BloodBankNotFound("Stock Details Not Found"));
                ;

        if(!Objects.equals(bloodStock.getCollectionDate().toLocalDate(), LocalDateTime.now().toLocalDate())){
            throw  new RuntimeException("update not allowed date expired please add new stock");
        }

        if(bloodStock==null || bloodStock.getBloodBank()==null){
            throw new RuntimeException("Empty Stock");
        }

        bloodStock.setQuantity(quantity);

        return bloodStockRepo.save(bloodStock);
    }

    @Transactional
    public BloodStock addNewStock(BloodStock bloodStock){

        if (bloodStock == null || bloodStock.getBloodBank() == null || bloodStock.getBloodBank().getId() == 0) {
            throw new RuntimeException("Please validate the bloodStock details and bloodBank id");
        }

//            if(bloodStockRepo.existsByComponentTypeAndBloodGroupAndBloodBank_Id(bloodStock.getComponentType(),
//                   bloodStock.getBloodGroup(), bloodStock.getBloodBank().getId())){
//                throw new BloodBankAlreadyExistsException("Blood Stock Already Exist");
//            }

        bloodStock.setExpiryDate(bloodStockConversion.getExpiryDate(BloodStockMapper.toBloodStockDTO(bloodStock)).getExpiryDate());
        bloodStock.setStatus(bloodStockConversion.getBloodStockStatus(BloodStockMapper.toBloodStockDTO(bloodStock)).getStatus());

        return bloodStockRepo.save(bloodStock);

    }

    @Transactional
    public void removeStock(int id){
        BloodStock bloodStock=bloodStockRepo.findById(id).
                orElseThrow(()->new   BloodBankNotFound("Stock Details Not Found"));

         bloodStockRepo.delete(bloodStock);
    }

    @Transactional
    public void removeFacility(int id){
        Facility facility=facilityRepo.findById(id).
                orElseThrow(()->new   BloodBankNotFound("Facility Details Not Found"));
        facilityRepo.delete(facility);
    }

}
