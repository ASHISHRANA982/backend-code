package college.project.dto;

import jakarta.validation.constraints.NotBlank;

public class FacilityDTO {

    @NotBlank(message = "Facility name is required")
    private String facilityName;

    public FacilityDTO(){super();}

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}
