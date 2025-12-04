package college.project.ImageConfig;

import college.project.dto.BloodBankRegistrationDTO;
import college.project.entity.BloodBankRegistration;
import college.project.mapper.BloodBankRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageEntityService {

    @Autowired
    private ImageRepo imageRepo;

    @Transactional
    public void saveImage(String publicId, BloodBankRegistrationDTO bloodBankRegistrationDTO){

        ImageEntity imageEntity=new ImageEntity();

        BloodBankRegistration bloodBankRegistration= BloodBankRegistrationMapper.toBloodBankRegistration(bloodBankRegistrationDTO);

        imageEntity.setPublicId(publicId);
        imageEntity.setBloodBankRegistration(bloodBankRegistration);
        imageRepo.save(imageEntity);
    }
}
