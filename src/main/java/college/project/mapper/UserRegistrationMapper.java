package college.project.mapper;

import college.project.dto.UserRegistrationDTO;
import college.project.entity.UserRegistration;

public class UserRegistrationMapper {

    public static UserRegistration toUserRegistration(UserRegistrationDTO userRegistrationDTO){


        UserRegistration registration=new UserRegistration(
                userRegistrationDTO.getId(),
                userRegistrationDTO.getName(),
                userRegistrationDTO.getPhone_no(),
                userRegistrationDTO.getBlood_group(),
                userRegistrationDTO.getAddress(),
                userRegistrationDTO.getAge(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getGender(),
                userRegistrationDTO.getLocalDateTime(),
                userRegistrationDTO.getBlood_unit(),
                userRegistrationDTO.getCurrent_location(),
                userRegistrationDTO.getBloodType()
        );
        return registration;
    }

    public static UserRegistrationDTO toUserRegistrationDTO(UserRegistration userRegistration){


        UserRegistrationDTO dto=new UserRegistrationDTO(
                userRegistration.getId(),
                userRegistration.getName(),
                userRegistration.getPhone_no(),
                userRegistration.getBlood_group(),
                userRegistration.getAddress(),
                userRegistration.getAge(),
                userRegistration.getEmail(),
                userRegistration.getGender(),
                userRegistration.getLocalDateTime(),
                userRegistration.getBlood_unit(),
                userRegistration.getCurrent_location(),
                userRegistration.getBloodType()
        );

        return dto;

    }

}
