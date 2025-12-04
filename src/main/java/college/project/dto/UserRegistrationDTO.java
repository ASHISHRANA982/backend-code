package college.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


public class UserRegistrationDTO {

    private int id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name should be between 2 to 50 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be a valid 10-digit Indian number")
    private String phone_no;

    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group (e.g., A+, O-, AB+)")
    private String blood_group;

    @NotBlank(message = "Address is required")
    private String address;

    @Min(value = 1, message = "Age must be at least 18")
    @Max(value = 90, message = "Age must be less than 65")
    private int age;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotNull(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Min(value = 1, message = "Blood unit must be at least 1")
    private int blood_unit;

    @NotBlank(message = "Current location is required")
    private String current_location;
    @NotBlank(message = "Blood Type Must Required")
    private String bloodType;



    public UserRegistrationDTO(){super();}

    public UserRegistrationDTO(int id, String name, String phone_no,
                            String blood_group, String address, int age, String email,
                            String gender, LocalDateTime date, int blood_unit, String current_location,String bloodType) {
        this.id = id;
        this.name = name;
        this.phone_no = phone_no;
        this.blood_group = blood_group;
        this.address = address;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.date = date;
        this.blood_unit = blood_unit;
        this.current_location = current_location;
        this.bloodType=bloodType;
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

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getLocalDateTime() {
        return date;
    }

    public void setLocalDateTime(LocalDateTime date) {
        this.date = date;
    }

    public int getBlood_unit() {
        return blood_unit;
    }

    public void setBlood_unit(int blood_unit) {
        this.blood_unit = blood_unit;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }
    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
