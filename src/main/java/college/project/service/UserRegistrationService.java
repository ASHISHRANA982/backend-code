package college.project.service;

import college.project.dto.UserRegistrationDTO;
import college.project.entity.UserRegistration;
import college.project.exception.UserAlreadyExistException;
import college.project.exception.UserNotFound;
import college.project.mapper.UserRegistrationMapper;
import college.project.repository.UserRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRegistrationRepo registrationRepo;

    @Transactional
    public UserRegistrationDTO UserRegister(UserRegistrationDTO registration){

        if(registration==null){
            throw  new UserNotFound("Invalid Data Try Again");
        }
        if(registrationRepo.existsByEmail(registration.getEmail())){
            throw new UserAlreadyExistException("User already registered with this email.");
        }
        UserRegistration userRegistration= UserRegistrationMapper.toUserRegistration(registration);

        userRegistration =registrationRepo.save(userRegistration);

        return UserRegistrationMapper.toUserRegistrationDTO(userRegistration);
    }

}
