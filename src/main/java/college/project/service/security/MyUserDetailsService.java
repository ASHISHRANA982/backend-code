package college.project.service.security;

import college.project.entity.AllBloodbankLogin;
import college.project.entity.AllDonorLogin;
import college.project.repository.bloodbank.AllBloodbankLoginRepo;
import college.project.repository.donor.AllDonorLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AllDonorLoginRepo allDonorLoginRepo;
    @Autowired
    private AllBloodbankLoginRepo allBloodbankLoginRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AllDonorLogin allDonorLogin=allDonorLoginRepo.findAllLoginByUsername(username);
        AllBloodbankLogin allBloodbankLogin=allBloodbankLoginRepo.findAllLoginByUsername(username);

        if(allBloodbankLogin!=null){
            return User.withUsername(allBloodbankLogin.getUsername())
                    .password(allBloodbankLogin.getPassword())
                    .roles(allBloodbankLogin.getRole())
                    .build();
        }

        if(allDonorLogin!=null){
            return User.withUsername(allDonorLogin.getUsername())
                    .password(allDonorLogin.getPassword())
                    .roles(allDonorLogin.getRole())
                    .build();
        }

        throw new  UsernameNotFoundException("Invalid Username: "+username);
    }
}
