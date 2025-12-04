package college.project.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BloodbankStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String recipientName;
    private int orderQuantity;
    private String availability="PENDING";
    private String distance;
    @Column(name = "recipient_location")
    private String location;
    @Column(name = "recipient_donation_date")
    private LocalDate donationDate;
    @Column(name = "recipient_blood_type")
    private String bloodType;
    @Column(name = "recipient_blood_group")
    private String bloodGroup;
    @Column(name = "recipient_phone_no")
    private String phoneNo;


    @ManyToOne
    @JoinColumn(name = "bloodbank_id")
    private BloodBankRegistration bloodBankRegistration;

    public BloodbankStatus(){super();}

    public BloodbankStatus(int id, String recipientName, int orderQuantity,
                           String availability, String distance, String location,LocalDate donationDate,
                           BloodBankRegistration bloodBankRegistration,String bloodType,String bloodGroup,String phoneNo) {
        this.id = id;
        this.recipientName = recipientName;
        this.orderQuantity = orderQuantity;
        this.availability = availability;
        this.distance = distance;
        this.location = location;
        this.donationDate=donationDate;
        this.bloodBankRegistration=bloodBankRegistration;
        this.bloodType=bloodType;
        this.bloodGroup=bloodGroup;
        this.phoneNo=phoneNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public BloodBankRegistration getBloodBankRegistration() {
        return bloodBankRegistration;
    }

    public void setBloodBankRegistration(BloodBankRegistration bloodBankRegistration) {
        this.bloodBankRegistration = bloodBankRegistration;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
