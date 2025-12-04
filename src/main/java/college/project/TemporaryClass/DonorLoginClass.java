package college.project.TemporaryClass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DonorLoginClass {


    @NotBlank(message = "Aadhar number is required")
    @Pattern(regexp = "^[2-9]{1}[0-9]{11}$", message = "Aadhar number must be a valid 12-digit number")
    String aadharNo;
    @NotBlank(message = "Username is required")
    String username;
    @NotBlank(message = "Password is required")
    String password;

    public DonorLoginClass(){super();}

    public DonorLoginClass(String aadharNo, String username, String password) {
        this.aadharNo = aadharNo;
        this.username = username;
        this.password = password;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
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
}
