package college.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "UserTable")
public class UserRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String phone_no;
    private String blood_group;
    private String address;
    private int age;
    @Column(unique = true)
    private String email;
    private String gender;
    private LocalDateTime date;
    private int blood_unit;
    private String current_location;
    private String bloodType;



    public UserRegistration(){super();}

    public UserRegistration(int id, String name, String phone_no,
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
