package college.project.mapper;

import college.project.dto.BloodBankRegistrationDTO;
import college.project.dto.BloodStockDTO;
import college.project.dto.FacilityDTO;
import college.project.entity.BloodBankRegistration;
import college.project.entity.BloodStock;
import college.project.entity.Facility;

import java.util.List;

public class BloodBankRegistrationMapper {

    public static BloodBankRegistration toBloodBankRegistration(BloodBankRegistrationDTO dto){
        BloodBankRegistration bank = new BloodBankRegistration();
        bank.setId(dto.getId());
        bank.setBloodBankName(dto.getBloodBankName());
        bank.setAddress(dto.getAddress());
        bank.setPhoneNo(dto.getPhoneNo());
        bank.setEmail(dto.getEmail());
        bank.setLicenceNo(dto.getLicenceNo());
        bank.setLicenceType(dto.getLicenceType());
        bank.setLicenceCopy(dto.getLicenceCopy());
        bank.setLicenceValidityDate(dto.getLicenceValidityDate());
        bank.setIssuingAuthority(dto.getIssuingAuthority());
        bank.setWorkingHour(dto.getWorkingHour());

        // Convert Stock DTO → Entity
        List<BloodStock> stockList = dto.getStockList().stream().map(stockDTO -> {
            BloodStock stock = new BloodStock();
            stock.setComponentType(stockDTO.getComponentType());
            stock.setBloodGroup(stockDTO.getBloodGroup());
            stock.setQuantity(stockDTO.getQuantity());
            stock.setCollectionDate(stockDTO.getCollectionDate());
            stock.setExpiryDate(stockDTO.getExpiryDate());
            stock.setStatus(stockDTO.getStatus());
            stock.setBloodBank(bank);
            return stock;
        }).toList();

        // Convert Facility DTO → Entity
        List<Facility> facilities = dto.getFacilities().stream().map(facilityDTO -> {
            Facility facility = new Facility();
            facility.setFacilityName(facilityDTO.getFacilityName());
            facility.setBloodBank(bank);
            return facility;
        }).toList();

        bank.setStockList(stockList);
        bank.setFacilities(facilities);

        return bank;
    }


    public static BloodBankRegistrationDTO toBloodBankRegistrationDTO(BloodBankRegistration registration){

        BloodBankRegistrationDTO bank = new BloodBankRegistrationDTO();
        bank.setId(registration.getId());
        bank.setBloodBankName(registration.getBloodBankName());
        bank.setAddress(registration.getAddress());
        bank.setPhoneNo(registration.getPhoneNo());
        bank.setEmail(registration.getEmail());
        bank.setLicenceNo(registration.getLicenceNo());
        bank.setLicenceType(registration.getLicenceType());
        bank.setLicenceCopy(registration.getLicenceCopy());
        bank.setLicenceValidityDate(registration.getLicenceValidityDate());
        bank.setIssuingAuthority(registration.getIssuingAuthority());
        bank.setWorkingHour(registration.getWorkingHour());

        // Convert Stock → EntityDTO
        List<BloodStockDTO> stockList = registration.getStockList().stream().map(stock -> {
            BloodStockDTO stockDTO = new BloodStockDTO();
            stockDTO.setComponentType(stock.getComponentType());
            stockDTO.setBloodGroup(stock.getBloodGroup());
            stockDTO.setQuantity(stock.getQuantity());
            stockDTO.setCollectionDate(stock.getCollectionDate());
            stockDTO.setExpiryDate(stock.getExpiryDate());
            stockDTO.setStatus(stock.getStatus());
            return stockDTO;
        }).toList();

        // Convert Facility DTO → Entity
        List<FacilityDTO> facilitiesDTO = registration.getFacilities().stream().map(facility -> {
            FacilityDTO facilityDTO = new FacilityDTO();
            facilityDTO.setFacilityName(facility.getFacilityName());
            return facilityDTO;
        }).toList();

        bank.setStockList(stockList);
        bank.setFacilities(facilitiesDTO);

        return bank;
    }

}
