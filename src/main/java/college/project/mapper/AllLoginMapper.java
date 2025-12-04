package college.project.mapper;

import college.project.dto.AllBloodbankLoginDTO;
import college.project.dto.AllDonorLoginDTO;
import college.project.entity.AllBloodbankLogin;
import college.project.entity.AllDonorLogin;
import college.project.entity.BloodBankRegistration;
import college.project.entity.DonorRegistration;

public class AllLoginMapper {


    public static AllDonorLogin toAllDonorLogin(AllDonorLoginDTO loginDTO, DonorRegistration donorRegistration){

        AllDonorLogin login=new AllDonorLogin(
                loginDTO.getId(),
                loginDTO.getUsername(),
                loginDTO.getPassword(),
                loginDTO.getRole(),
                donorRegistration
        );

        return login;
    }

    public static AllDonorLoginDTO toAllDonorLoginDTO(AllDonorLogin login){
        return new AllDonorLoginDTO(
                login.getId(),
                login.getUsername(),
                login.getPassword(),
                login.getRole(),
                login.getDonor() != null ? login.getDonor().getId() : null
        );
    }

    public static AllBloodbankLoginDTO  toAllBloodbankLoginDTO(AllBloodbankLogin login){
        return new AllBloodbankLoginDTO(
                login.getId(),
                login.getUsername(),
                login.getPassword(),
                login.getRole(),
                login.getBloodBank() != null ? login.getBloodBank().getId() : null
        );
    }

    public static AllBloodbankLogin toAllBloodbankLogin(AllBloodbankLoginDTO loginDTO, BloodBankRegistration bloodBankRegistration){

        AllBloodbankLogin login=new AllBloodbankLogin(
                loginDTO.getId(),
                loginDTO.getUsername(),
                loginDTO.getPassword(),
                loginDTO.getRole(),
               bloodBankRegistration
        );

        return login;
    }

}
