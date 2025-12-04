package college.project.TemporaryClass;

import jakarta.validation.constraints.NotBlank;

public class SearchBlood {

    @NotBlank(message = "please select the blood group")
    private String bloodGroup;
    @NotBlank(message = "please enter the valid address")
    private String address;
    @NotBlank(message = "blood type required")
    private String bloodType;

    public SearchBlood(){super();}

    public SearchBlood(String bloodGroup, String address,String bloodType) {
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.bloodType=bloodType;
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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
