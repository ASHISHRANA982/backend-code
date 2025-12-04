package college.project.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "DonorTable")
public class DonorRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone_no;
    @Column(name = "aadhar_no", unique = true)
    private String aadharNo;
    @Column(name = "blood_group")
    private String bloodGroup;
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String gender;
    @Column(unique = true)
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "last_donate_date")
    private Date lastDonateDate;

    public DonorRegistration(){super();}

    public DonorRegistration(int id,String name, String phone_no, String aadharNo,
                             String bloodGroup, String address, LocalDate dateOfBirth,
                             String gender, String email, Date lastDonateDate) {
        this.id = id;
        this.name=name;
        this.phone_no = phone_no;
        this.aadharNo = aadharNo;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.lastDonateDate = lastDonateDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastDonateDate() {
        return lastDonateDate;
    }

    public void setLastDonateDate(Date lastDonateDate) {
        this.lastDonateDate = lastDonateDate;
    }
}
