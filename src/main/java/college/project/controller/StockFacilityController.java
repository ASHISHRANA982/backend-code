package college.project.controller;

import college.project.TemporaryClass.SecurityIdChecker;
import college.project.entity.BloodStock;
import college.project.entity.Facility;
import college.project.repository.bloodbank.BloodStockRepo;
import college.project.repository.bloodbank.FacilityRepo;
import college.project.service.bloodbank.StockFacilityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('BLOODBANK')")
@RequestMapping("/Bloodbank")
public class StockFacilityController {

    @Autowired
    private StockFacilityService stockFacilityService;
    @Autowired
    private SecurityIdChecker securityIdChecker;
    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private FacilityRepo facilityRepo;

    @GetMapping("/displayStock")
    public ResponseEntity<List<BloodStock>> getStocks(HttpServletRequest request) {

        int bId=securityIdChecker.checkOwnerId(request);

        List<BloodStock> list = stockFacilityService.getStocks(bId);
        return ResponseEntity.ok(list);
    }


    @PutMapping("/updateStock/{id}")
    public ResponseEntity<String> editStock(@PathVariable int id, @RequestBody Map<String,Integer>requestBody){

        BloodStock bloodStockC=bloodStockRepo.findById(id).orElseThrow(()->new RuntimeException("stock not found"));


        BloodStock bloodStock=stockFacilityService.updateBloodStock(id,requestBody.get("quantity"));

        if(bloodStock==null){
            return  ResponseEntity.badRequest().body("Stock Update Unsuccessful Please Try Again");
        }
        return ResponseEntity.ok("Blood Stock Update Successfully your blood packet SERIAL NO : "+bloodStock.getId());
    }


    @PostMapping("/addStock") //for adding stock you have to integrate bloodbank id with bloodStock Json
    public ResponseEntity<String>addStock(@Valid  @RequestBody BloodStock bloodStock){

        BloodStock stock=stockFacilityService.addNewStock(bloodStock);
        if(stock==null){
            return ResponseEntity.badRequest().body("Please Try Again Adding unsuccessful");
        }
        return ResponseEntity.ok("stock added successfully your blood packet SERIAL NO : "+stock.getId());
    }

    @DeleteMapping("/deleteStock/{id}")
    public ResponseEntity<String>removeStock(@PathVariable int id){
        BloodStock bloodStock=bloodStockRepo.findById(id).orElseThrow(()->new RuntimeException("stock not found"));
        stockFacilityService.removeStock(id);

        return ResponseEntity.ok("stock deleted successfully");
    }

    @GetMapping("/displayFacility")
    public ResponseEntity<List<Facility>>getFacilities(HttpServletRequest request){
        int bId=securityIdChecker.checkOwnerId(request);
       List<Facility> facility= stockFacilityService.getFacilities(bId);
        return ResponseEntity.ok(facility);
    }

    @DeleteMapping("/removeFacility/{id}")
    public ResponseEntity<String>removeFacility(@PathVariable int id){
        Facility facility=facilityRepo.findById(id).orElseThrow(()->new RuntimeException("facility not found"));

        stockFacilityService.removeFacility(id);
        return ResponseEntity.ok("facility delete successfully");
    }


}
