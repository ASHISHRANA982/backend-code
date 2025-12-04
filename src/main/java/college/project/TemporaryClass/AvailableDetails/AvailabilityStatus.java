package college.project.TemporaryClass.AvailableDetails;


import java.util.List;

public record AvailabilityStatus(
        int requestId,
        List<DonorDetails>donorDetails,
        List<BloodbankDetails>bloodbankDetails
) {}


