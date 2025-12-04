package college.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AllBloodbankLoginDTO {

    private int id;

    @NotBlank(message = "Username required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8 , max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
    @NotBlank(message = "role is required")
    private String role;

    @NotNull(message = "donorId is required")
    private Integer donorId;

    public AllBloodbankLoginDTO(){super();}

    public AllBloodbankLoginDTO(int id, String username, String password, String role, Integer donorId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.donorId = donorId;
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

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }
}
