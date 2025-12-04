package college.project.service.donor;


import college.project.dto.AllDonorLoginDTO;
import college.project.dto.DonorRegistrationDTO;
import college.project.entity.AllDonorLogin;
import college.project.entity.DonorRegistration;
import college.project.exception.DonorAlreadyExistsException;
import college.project.exception.DonorNotFound;
import college.project.mapper.AllLoginMapper;
import college.project.mapper.DonorRegistrationMapper;
import college.project.repository.donor.AllDonorLoginRepo;
import college.project.repository.donor.DonorRegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
public class DonorRegistrationService {

    @Autowired
    private DonorRegistrationRepo donorRegistrationRepo;
    @Autowired
    private AllDonorLoginRepo allLoginRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AllDonorLoginRepo loginRepo;

    @Transactional
    public DonorRegistrationDTO DonorRegister(DonorRegistrationDTO registrationDTO){
        if(registrationDTO == null){
            throw new DonorNotFound("Invalid Data Please Try Again");
        }

        if(donorRegistrationRepo.existsByEmail(registrationDTO.getEmail())){
            throw new DonorAlreadyExistsException("Donor already registered with this email.");
        }

        if(loginRepo.existsByUsername(registrationDTO.getEmail())){
            throw new DonorAlreadyExistsException("Email Already Exist Use Another One");
        }

        int age = Period.between(registrationDTO.getDateOfBirth(), LocalDate.now()).getYears();
        if (age < 18 || age > 65) {
            throw new IllegalArgumentException("Age must be between 18 and 65");
        }


        DonorRegistration registration = DonorRegistrationMapper.toDonorRegistration(registrationDTO);

        registration=donorRegistrationRepo.save(registration);

        return DonorRegistrationMapper.toDonorRegistrationDTO(registration);
    }


    public DonorRegistrationDTO showProfile(int id){

        DonorRegistration registration=donorRegistrationRepo.findById(id).orElseThrow(()->new DonorNotFound("Donor not found"));
        return DonorRegistrationMapper.toDonorRegistrationDTO(registration);
    }

    @Transactional
    public DonorRegistrationDTO updateProfile(int id,DonorRegistrationDTO registrationDTO){

        DonorRegistration registration=donorRegistrationRepo.findById(id).orElseThrow(()->new DonorNotFound("Donor not found"));


        if (registrationDTO.getLastDonateDate() != null)
            registration.setLastDonateDate(registrationDTO.getLastDonateDate());

        if (registrationDTO.getEmail() != null)
            registration.setEmail(registrationDTO.getEmail());

        if (registrationDTO.getAddress() != null)
            registration.setAddress(registrationDTO.getAddress());

        if (registrationDTO.getPhone_no() != null)
            registration.setPhone_no(registrationDTO.getPhone_no());

        donorRegistrationRepo.save(registration);
        return DonorRegistrationMapper.toDonorRegistrationDTO(registration);

    }

    @Transactional
    public AllDonorLoginDTO updateLogin(DonorRegistration registration , String username, String password){


        AllDonorLogin allLogin=allLoginRepo.findByDonorId(registration.getId());

        if (allLogin == null) {
            throw new DonorNotFound("Please Register First");
        }

        if (username != null && !username.isEmpty()) {
            if (loginRepo.existsByUsername(username)) {
                throw new DonorAlreadyExistsException("Username already exists");
            }
            allLogin.setUsername(username);
        }
        if(password!=null && !password.isEmpty()){
            allLogin.setPassword(passwordEncoder.encode(password));

        }

       allLogin= allLoginRepo.save(allLogin);
        return AllLoginMapper.toAllDonorLoginDTO(allLogin);

    }


}
