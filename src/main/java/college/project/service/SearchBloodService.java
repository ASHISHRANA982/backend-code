package college.project.service;

import college.project.MapServices.DistanceMatrixService;
import college.project.TemporaryClass.ReturnSearchedInfo;
import college.project.TemporaryClass.SearchBlood;
import college.project.entity.BloodBankRegistration;
import college.project.entity.BloodStock;
import college.project.exception.BloodBankNotFound;
import college.project.repository.bloodbank.BloodBankRegistrationRepo;
import college.project.repository.bloodbank.BloodStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SearchBloodService {

    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private DistanceMatrixService distanceMatrixService;
    @Autowired
    private BloodBankRegistrationRepo bloodBankRegistrationRepo;

    public  List<ReturnSearchedInfo> searchBlood(SearchBlood searchBlood){

        List<BloodStock>bloodStockList=bloodStockRepo.
                findByBloodGroup(searchBlood.getBloodGroup(), searchBlood.getBloodType());
        System.out.println("Enter Point");

        if(bloodStockList==null || bloodStockList.isEmpty()){
            throw new BloodBankNotFound("Blood not available for this blood group");
        }

        List<BloodStock>bloodStockList1=calculateShortestDistance(bloodStockList, searchBlood.getAddress());

        List<ReturnSearchedInfo> searchedInfos=new ArrayList<>();
        for(BloodStock stock:bloodStockList1){
           ReturnSearchedInfo info=new ReturnSearchedInfo();

           info.setQuantity(stock.getQuantity());
           info.setBloodGroup(stock.getBloodGroup());
           info.setStatus(stock.getStatus());
           info.setCollectionDate(stock.getCollectionDate());
           info.setBloodBankAddress(stock.getBloodBank().getAddress());
           info.setBloodBankName(stock.getBloodBank().getBloodBankName());
           info.setBloodBankEmail(stock.getBloodBank().getEmail());
           info.setBloodBankPhoneNo(stock.getBloodBank().getPhoneNo());
           info.setBloodBankLicenceType(stock.getBloodBank().getLicenceType());
           info.setBloodBankWorkingHour(stock.getBloodBank().getWorkingHour());

           searchedInfos.add(info);
        }

        return searchedInfos;
    }

    public List<BloodStock> calculateShortestDistance(List<BloodStock> bloodStockList, String userAddress) {
        double maxAllowedDistance = 35.0;
        List<BloodStock> nearbyBloodStocks = new ArrayList<>();

        for (BloodStock b : bloodStockList) {
            BloodBankRegistration registration = b.getBloodBank();
            String distanceStr = distanceMatrixService.getDistanceOfLocations(registration.getAddress(), userAddress);

            double currentDistance = parseDistance(distanceStr);
            System.out.println("nearest distance is "+currentDistance);

            if (currentDistance <= maxAllowedDistance) {
                System.out.println("nearest distance is "+currentDistance);
                nearbyBloodStocks.add(b);
            }
        }

        System.out.println("calculation of distance complete");

        return nearbyBloodStocks;
    }

    public double parseDistance(String distance) {
        distance = distance.trim();
        if (distance.endsWith("km")) {
            return Double.parseDouble(distance.replace("km", "").trim());
        } else if (distance.endsWith("m")) {
            double meters = Double.parseDouble(distance.replace("m", "").trim());

            return meters / 1000.0; // convert meters to km
        } else {
            return 0.0;
        }
    }


}
