package college.project.ImageConfig;

import college.project.entity.BloodBankRegistration;
import jakarta.persistence.*;

@Entity
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String publicId;

    @OneToOne
    @JoinColumn(name = "bloodbankId")
    private BloodBankRegistration bloodBankRegistration;

    public ImageEntity(){super();}

    public ImageEntity(int id, String publicId, BloodBankRegistration bloodBankRegistration) {
        this.id = id;
        this.publicId = publicId;
        this.bloodBankRegistration = bloodBankRegistration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public BloodBankRegistration getBloodBankRegistration() {
        return bloodBankRegistration;
    }

    public void setBloodBankRegistration(BloodBankRegistration bloodBankRegistration) {
        this.bloodBankRegistration = bloodBankRegistration;
    }
}
