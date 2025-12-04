package college.project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class BloodStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String componentType;  // Whole Blood, Plasma, etc.
    private String bloodGroup;  // A+, O-, etc.
    private int quantity; // Number of units
    private LocalDateTime collectionDate;
    private LocalDateTime expiryDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "blood_bank_id")
    @JsonBackReference
    private BloodBankRegistration bloodBank;

    public BloodStock(){super();}

    public BloodStock(int id, String componentType, String bloodGroup, int quantity, LocalDateTime collectionDate,
                      LocalDateTime expiryDate, String status, BloodBankRegistration bloodBank) {
        this.id = id;
        this.componentType = componentType;
        this.bloodGroup = bloodGroup;
        this.quantity = quantity;
        this.collectionDate = collectionDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.bloodBank = bloodBank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDateTime collectionDate) {
        this.collectionDate = collectionDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BloodBankRegistration getBloodBank() {
        return bloodBank;
    }

    public void setBloodBank(BloodBankRegistration bloodBank) {
        this.bloodBank = bloodBank;
    }
}
