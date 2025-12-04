package college.project.service.security;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final ConcurrentHashMap<String, OtpData> otpStore = new ConcurrentHashMap<>();

    public String generateOtp(String email) {
        SecureRandom random = new SecureRandom();
        String otp = String.valueOf(100000 + random.nextInt(900000)); // 6-digit
        otpStore.put(email, new OtpData(otp, Instant.now().plusSeconds(600))); // valid 10 min
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        OtpData data = otpStore.get(email);
        if (data == null || Instant.now().isAfter(data.expiry())) {
            otpStore.remove(email);
            return false;
        }
        boolean valid = data.otp().equals(otp);
        if (valid) otpStore.remove(email);
        return valid;
    }

    record OtpData(String otp, Instant expiry) {}
}
