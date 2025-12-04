package college.project.mapper;

import college.project.dto.DonorRegistrationDTO;
import college.project.entity.DonorRegistration;

public class DonorRegistrationMapper {

    public static DonorRegistration toDonorRegistration(DonorRegistrationDTO donorRegistrationDTO){


        DonorRegistration registration=new DonorRegistration();

        registration.setId(donorRegistrationDTO.getId());
        registration.setName(donorRegistrationDTO.getName());
        registration.setPhone_no(donorRegistrationDTO.getPhone_no());
        registration.setAadharNo( donorRegistrationDTO.getAadharNo());
        registration.setBloodGroup(donorRegistrationDTO.getBloodGroup());
        registration.setAddress(donorRegistrationDTO.getAddress());
        registration.setDateOfBirth(donorRegistrationDTO.getDateOfBirth());
        registration.setGender( donorRegistrationDTO.getGender());
        registration.setEmail(donorRegistrationDTO.getEmail());
        registration.setLastDonateDate( donorRegistrationDTO.getLastDonateDate());

        return registration;
    }

    public static DonorRegistrationDTO toDonorRegistrationDTO(DonorRegistration registration){

        DonorRegistrationDTO registrationDTO=new DonorRegistrationDTO();
        registrationDTO.setId(registration.getId());
        registrationDTO.setName(registration.getName());
        registrationDTO.setPhone_no(registration.getPhone_no());
        registrationDTO.setAadharNo( registration.getAadharNo());
        registrationDTO.setBloodGroup(registration.getBloodGroup());
        registrationDTO.setAddress(registration.getAddress());
        registrationDTO.setDateOfBirth(registration.getDateOfBirth());
        registrationDTO.setGender( registration.getGender());
        registrationDTO.setEmail(registration.getEmail());
        registrationDTO.setLastDonateDate( registration.getLastDonateDate());
        return registrationDTO;
    }

}
