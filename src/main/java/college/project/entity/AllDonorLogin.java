package college.project.entity;

import jakarta.persistence.*;

@Entity
public class AllDonorLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "donor_id")
    private DonorRegistration donor;


    public AllDonorLogin(){super();}

    public AllDonorLogin(int id, String username, String password, String role,
                         DonorRegistration donor) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.donor = donor;

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

    public DonorRegistration getDonor() {
        return donor;
    }

    public void setDonor(DonorRegistration donor) {
        this.donor = donor;
    }

}
