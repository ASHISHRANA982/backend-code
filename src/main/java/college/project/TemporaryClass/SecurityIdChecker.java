package college.project.TemporaryClass;

import college.project.security.JwtAuth;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityIdChecker {
    @Autowired
    private JwtAuth jwtAuth;

    public int checkOwnerId(HttpServletRequest request){

        String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token=authHeader.substring(7);
        Claims claims= jwtAuth.getId(token);
        return (Integer) claims.get("id");
    }
}
