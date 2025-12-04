package college.project.service.security;

import college.project.repository.LogoutRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TokenLogoutService {
    @Autowired
    private LogoutRepo logoutRepo;

    //  Scheduled cleanup — runs every hour
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void cleanupExpiredTokens() {
        Date now = new Date();
        logoutRepo.deleteByExpiryDateBefore(now);
        System.out.println(" Expired tokens cleaned at: " + now);
    }
}
