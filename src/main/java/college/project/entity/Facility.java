package college.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String facilityName;

    @ManyToOne
    @JoinColumn(name = "blood_bank_id")
    @JsonBackReference
    private BloodBankRegistration bloodBank;


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public BloodBankRegistration getBloodBank() {
        return bloodBank;
    }

    public void setBloodBank(BloodBankRegistration bloodBank) {
        this.bloodBank = bloodBank;
    }
}
