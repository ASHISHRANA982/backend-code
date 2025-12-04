package college.project.service.security;

import college.project.dto.AllBloodbankLoginDTO;
import college.project.dto.AllDonorLoginDTO;
import college.project.dto.BloodBankRegistrationDTO;
import college.project.dto.DonorRegistrationDTO;
import college.project.entity.AllBloodbankLogin;
import college.project.entity.AllDonorLogin;
import college.project.entity.BloodBankRegistration;
import college.project.entity.DonorRegistration;
import college.project.exception.BloodBankNotFound;
import college.project.exception.DonorNotFound;
import college.project.mapper.AllLoginMapper;
import college.project.repository.bloodbank.AllBloodbankLoginRepo;
import college.project.repository.donor.AllDonorLoginRepo;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.repository.donor.DonorRegistrationRepo;
import college.project.security.JwtAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AllDonorLoginRepo allDonorLoginRepo;
    @Autowired
    private AllBloodbankLoginRepo allBloodbankLoginRepo;
    @Autowired
    private DonorRegistrationRepo donorRegistrationRepo;

    @Autowired
    private BloodBankRegistrationRepo bloodBankRegistrationRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuth jwtAuth;

    public AllDonorLogin saveDonorLogin(DonorRegistrationDTO donorRegistrationDTO) {

        // fetch donor with generated ID
        DonorRegistration registration = donorRegistrationRepo.findById(donorRegistrationDTO.getId())
                .orElseThrow(() -> new DonorNotFound("Donor not found. Please Register First."));

        StringBuilder pass=new StringBuilder(
               donorRegistrationDTO.getPhone_no().substring(0,3)
        );

        AllDonorLoginDTO loginDTO = new AllDonorLoginDTO();
        loginDTO.setUsername(donorRegistrationDTO.getEmail());
        loginDTO.setPassword(passwordEncoder.encode("donor@"+pass));
        loginDTO.setRole("DONOR");

        AllDonorLogin login = AllLoginMapper.toAllDonorLogin(loginDTO, registration);

        AllDonorLogin savedLogin = allDonorLoginRepo.save(login);

        return savedLogin;
    }

    public AllBloodbankLogin saveBloodBankLogin(BloodBankRegistrationDTO bankRegistrationDTO) {

        // fetch donor with generated ID
        BloodBankRegistration registration = bloodBankRegistrationRepo.findById(bankRegistrationDTO.getId())
                .orElseThrow(() -> new BloodBankNotFound("Blood Bank not found. Please Register First."));

        StringBuilder pass=new StringBuilder(
                bankRegistrationDTO.getPhoneNo().substring(0,3)
        );


        // prepare login object
        AllBloodbankLoginDTO loginDTO = new AllBloodbankLoginDTO();
        loginDTO.setUsername(bankRegistrationDTO.getEmail());
        loginDTO.setPassword(passwordEncoder.encode("bloodbank@"+pass));
        loginDTO.setRole("BLOODBANK");

        // map to entity
        AllBloodbankLogin login = AllLoginMapper.toAllBloodbankLogin(loginDTO,  registration);

        // save login entity


        AllBloodbankLogin savedLogin =allBloodbankLoginRepo .save(login);

        return savedLogin;
    }


    public ResponseEntity<String> donorLogin(AllDonorLoginDTO loginDTO, String expectedRole){
        try {

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )

            );

                AllDonorLogin login=allDonorLoginRepo.findAllLoginByUsername(loginDTO.getUsername());

                if(!login.getRole().equalsIgnoreCase(expectedRole)){
                    return ResponseEntity.status(403)
                            .body("Access denied! You are not authorized to login as " + expectedRole);
                }

                String token= jwtAuth.generateDonorAccessToken(login);

            return ResponseEntity.ok(token);




        }
        catch (Exception e) {

            return ResponseEntity.status(401).body("Invalid username and password ");
        }


    }



    public ResponseEntity<String> bloodBankLogin(AllBloodbankLoginDTO loginDTO, String expectedRole){
        try {

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )

            );

           AllBloodbankLogin allBloodbankLogin=allBloodbankLoginRepo.findAllLoginByUsername(loginDTO.getUsername());

            if(!allBloodbankLogin.getRole().equalsIgnoreCase(expectedRole)){
                return ResponseEntity.status(403)
                        .body("Access denied! You are not authorized to login as " + expectedRole);
            }

            String token= jwtAuth.generateBloodbankAccessToken(allBloodbankLogin);

            return ResponseEntity.ok(token);

        }
        catch (Exception e) {

            return ResponseEntity.status(401).body("Invalid username and password ");
        }


    }




}
