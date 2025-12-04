package college.project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BloodBankTable")
public class BloodBankRegistration {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String bloodBankName;
        private String address;
        private String phoneNo;
        @Column(unique = true)
        private String email;
        @Column(unique = true)
        private String licenceNo;
        private String licenceType;
        private String licenceCopy;
        @Temporal(TemporalType.DATE)
        private Date licenceValidityDate;
        private String issuingAuthority;
        private String workingHour;


        @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<BloodStock> stockList=new ArrayList<>();

        @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Facility> facilities=new ArrayList<>();


        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getBloodBankName() { return bloodBankName; }
        public void setBloodBankName(String bloodBankName) { this.bloodBankName = bloodBankName; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhoneNo() { return phoneNo; }
        public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getLicenceNo() { return licenceNo; }
        public void setLicenceNo(String licenceNo) { this.licenceNo = licenceNo; }

        public String getLicenceType() { return licenceType; }
        public void setLicenceType(String licenceType) { this.licenceType = licenceType; }

        public String getLicenceCopy() { return licenceCopy; }
        public void setLicenceCopy(String licenceCopy) { this.licenceCopy = licenceCopy; }

        public Date getLicenceValidityDate() { return licenceValidityDate; }
        public void setLicenceValidityDate(Date licenceValidityDate) { this.licenceValidityDate = licenceValidityDate; }

        public String getIssuingAuthority() { return issuingAuthority; }
        public void setIssuingAuthority(String issuingAuthority) { this.issuingAuthority = issuingAuthority; }

        public String getWorkingHour() { return workingHour; }
        public void setWorkingHour(String workingHour) { this.workingHour = workingHour; }

        public List<BloodStock> getStockList() { return stockList; }
        public void setStockList(List<BloodStock> stockList) { this.stockList = stockList; }

        public List<Facility> getFacilities() { return facilities; }
        public void setFacilities(List<Facility> facilities) { this.facilities = facilities; }



}
