package college.project.TemporaryClass;

import college.project.dto.BloodStockDTO;
import college.project.entity.BloodStock;
import college.project.mapper.BloodStockMapper;
import college.project.repository.bloodbank.BloodStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class AutoUpdateClass {
    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private BloodStockConversion bloodStockConversion;


    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void updateBloodStockStatus(){

        List<BloodStock>bloodStockList=bloodStockRepo.findAll();

        List<BloodStock>updatedStockList=new ArrayList<>();

        for(BloodStock bloodStock:bloodStockList){
          BloodStockDTO bloodStockDTO= bloodStockConversion.getBloodStockStatus(BloodStockMapper.toBloodStockDTO(bloodStock));

          updatedStockList.add(BloodStockMapper.toBloodStock(bloodStockDTO));
        }

        bloodStockRepo.saveAll(updatedStockList);

    }


}
