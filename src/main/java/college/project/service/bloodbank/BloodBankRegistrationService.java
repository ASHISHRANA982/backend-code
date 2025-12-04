package college.project.service.bloodbank;

import college.project.TemporaryClass.BloodStockConversion;
import college.project.dto.AllBloodbankLoginDTO;
import college.project.dto.BloodBankRegistrationDTO;
import college.project.dto.BloodStockDTO;
import college.project.entity.AllBloodbankLogin;
import college.project.entity.BloodBankRegistration;
import college.project.exception.BloodBankAlreadyExistsException;
import college.project.exception.BloodBankNotFound;
import college.project.mapper.AllLoginMapper;
import college.project.mapper.BloodBankRegistrationMapper;
import college.project.repository.bloodbank.AllBloodbankLoginRepo;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.repository.bloodbank.BloodStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodBankRegistrationService {

        @Autowired
        private BloodBankRegistrationRepo bloodBankRegistrationRepo;

        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private BloodStockRepo bloodStockRepo;
        @Autowired
        private BloodStockConversion bloodStockConversion;
        @Autowired
        private AllBloodbankLoginRepo allBloodbankLoginRepo;


        @Transactional
        public BloodBankRegistrationDTO saveBloodBank(BloodBankRegistrationDTO dto) {

            if(dto==null){
                throw new BloodBankNotFound("Invalid Data Please Try Again");
            }
            if(bloodBankRegistrationRepo.existsByLicenceNo(dto.getLicenceNo())){
                throw  new BloodBankAlreadyExistsException("Bloodbank already registered with this licence.");
            }
            if ( allBloodbankLoginRepo.existsByUsername(dto.getEmail())) {
                throw  new BloodBankAlreadyExistsException(" Email Already Used Try Another One.");
            }

            List<BloodStockDTO>updateExpiryList=new ArrayList<>();
            for(int i=0;i<dto.getStockList().size();i++){
                BloodStockDTO bloodStockDTO=bloodStockConversion.
                        getExpiryDate(dto.getStockList().get(i));
                updateExpiryList.add(bloodStockDTO);
            }
            dto.setStockList(updateExpiryList);


            List<BloodStockDTO> updatedStockStatusList = new ArrayList<>();
            for(int i=0;i<dto.getStockList().size();i++){
               BloodStockDTO bloodStockDTO= bloodStockConversion.
                       getBloodStockStatus(dto.getStockList().get(i));
               updatedStockStatusList.add(bloodStockDTO);
            }
            dto.setStockList(updatedStockStatusList);

            BloodBankRegistration bank= BloodBankRegistrationMapper.toBloodBankRegistration(dto);
            bank= bloodBankRegistrationRepo.save(bank);

            return BloodBankRegistrationMapper.toBloodBankRegistrationDTO(bank);
        }

        public BloodBankRegistrationDTO getProfile(int id){
            BloodBankRegistration bloodBankRegistration=bloodBankRegistrationRepo.findById(id)
                    .orElseThrow(()->new BloodBankNotFound("Bloodbank Not Found"));

            return BloodBankRegistrationMapper.toBloodBankRegistrationDTO(bloodBankRegistration);
        }

        @Transactional
    public BloodBankRegistrationDTO updateBloodBank(int id,
                                                 String bloodBankName,
                                                 String address,
                                                 String phoneNo,
                                                 String email,
                                                 String licenceNo,
                                                 String licenceType,
                                                 String licenceValidityDate,
                                                 String issuingAuthority,
                                                 String workingHour,
                                                 MultipartFile file) throws IOException {

        BloodBankRegistration existing = bloodBankRegistrationRepo.findById(id)
                .orElseThrow(() -> new BloodBankNotFound("Blood bank not found"));

        existing.setBloodBankName(bloodBankName);
        existing.setAddress(address);
        existing.setPhoneNo(phoneNo);
        existing.setEmail(email);
        existing.setLicenceNo(licenceNo);
        existing.setLicenceType(licenceType);
        existing.setLicenceValidityDate(java.sql.Date.valueOf(licenceValidityDate));
        existing.setIssuingAuthority(issuingAuthority);
        existing.setWorkingHour(workingHour);

        // Handle new file upload if provided
        if (file != null && !file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/images/";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) uploadFolder.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadFolder, fileName);
            file.transferTo(dest);

            existing.setLicenceCopy(fileName);
        }

        return BloodBankRegistrationMapper.toBloodBankRegistrationDTO( bloodBankRegistrationRepo.save(existing));
    }

    @Transactional
    public AllBloodbankLoginDTO updateLogin(int id, String newUsername, String newPassword){

        AllBloodbankLogin allBloodbankLogin= allBloodbankLoginRepo.findByBloodBankId(id);

        if (allBloodbankLogin == null) {
            throw new BloodBankNotFound("Please Register First");
        }

        if (newUsername != null && !newUsername.isEmpty()) {
            if (allBloodbankLoginRepo.existsByUsername(newUsername)) {
                throw new BloodBankAlreadyExistsException("Username already exists");
            }
            allBloodbankLogin.setUsername(newUsername);
        }
        if(newPassword!=null && !newPassword.isEmpty()){
            allBloodbankLogin.setPassword(passwordEncoder.encode(newPassword));
        }
        return AllLoginMapper.toAllBloodbankLoginDTO( allBloodbankLoginRepo.save(allBloodbankLogin));
    }


}
