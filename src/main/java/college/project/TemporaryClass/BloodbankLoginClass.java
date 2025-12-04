package college.project.TemporaryClass;

import jakarta.validation.constraints.NotBlank;

public class BloodbankLoginClass {

    @NotBlank(message = "LicenceNo Required")
    private String licenceNo;
    @NotBlank(message = "New Username Required")
    private String newUsername;
    @NotBlank(message = "New Password Required")
    private String newPassword;

    public BloodbankLoginClass(){super();}

    public BloodbankLoginClass(String licenceNo, String newUsername, String newPassword) {
        this.licenceNo = licenceNo;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
