package college.project.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class DonorStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate donationDate;
    private String recipientName;
    private String availability="PENDING";
    private String distance;
    @Column(name = "recipient_location")
    private String location;
    @Column(name = "recipient_blood_group")
    private String bloodGroup;
    @Column(name = "recipient_phone_no")
    private String phoneNo;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private DonorRegistration donorRegistration;


    public DonorStatus(){super();}

    public DonorStatus(int id, LocalDate donationDate, String recipientName,
                       String availability, String distance, String location,
                       DonorRegistration donorRegistration,String bloodGroup,String phoneNo) {
        this.id = id;
        this.donationDate = donationDate;
        this.recipientName = recipientName;
        this.availability = availability;
        this.distance = distance;
        this.location = location;
        this.donorRegistration=donorRegistration;
        this.bloodGroup=bloodGroup;
        this.phoneNo=phoneNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
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

    public DonorRegistration getDonorRegistration() {
        return donorRegistration;
    }

    public void setDonorRegistration(DonorRegistration donorRegistration) {
        this.donorRegistration = donorRegistration;
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
