package college.project.entity;

import jakarta.persistence.*;

@Entity
public class AllBloodbankLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;


    @OneToOne
    @JoinColumn(name = "blood_bank_id")
    private BloodBankRegistration bloodBank;

    public AllBloodbankLogin(){super();}


    public AllBloodbankLogin(int id, String username, String password, String role, BloodBankRegistration bloodBank) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.bloodBank = bloodBank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BloodBankRegistration getBloodBank() {
        return bloodBank;
    }

    public void setBloodBank(BloodBankRegistration bloodBank) {
        this.bloodBank = bloodBank;
    }
}
