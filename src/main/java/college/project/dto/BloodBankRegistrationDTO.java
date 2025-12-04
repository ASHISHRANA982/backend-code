package college.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BloodBankRegistrationDTO {

    private int id;

    @NotBlank(message = "Blood bank name is required")
    @Size(min = 3, max = 100, message = "Blood bank name must be between 3 and 100 characters")
    private String bloodBankName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNo;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Licence number is required")
    private String licenceNo;

    @NotBlank(message = "Licence type is required")
    private String licenceType;

    @NotBlank(message = "Licence copy is required")
    private String licenceCopy;

    @NotNull(message = "Licence validity date is required")
    @Future(message = "Licence validity date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date licenceValidityDate;

    @NotBlank(message = "Issuing authority is required")
    private String issuingAuthority;

    @NotBlank(message = "Working hour is required")
    private String workingHour;

    @NotEmpty(message = "Stock list cannot be empty")
    private List<@Valid BloodStockDTO> stockList=new ArrayList<>();

    @NotEmpty(message = "Facilities list cannot be empty")
    private List<@Valid FacilityDTO> facilities=new ArrayList<>();

    public BloodBankRegistrationDTO(){super();}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBloodBankName() {
        return bloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        this.bloodBankName = bloodBankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getLicenceType() {
        return licenceType;
    }

    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public String getLicenceCopy() {
        return licenceCopy;
    }

    public void setLicenceCopy(String licenceCopy) {
        this.licenceCopy = licenceCopy;
    }

    public Date getLicenceValidityDate() {
        return licenceValidityDate;
    }

    public void setLicenceValidityDate(Date licenceValidityDate) {
        this.licenceValidityDate = licenceValidityDate;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(String workingHour) {
        this.workingHour = workingHour;
    }

    public List<BloodStockDTO> getStockList() {
        return stockList;
    }

    public void setStockList(List<BloodStockDTO> stockList) {
        this.stockList = stockList;
    }

    public List<FacilityDTO> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FacilityDTO> facilities) {
        this.facilities = facilities;
    }
}
