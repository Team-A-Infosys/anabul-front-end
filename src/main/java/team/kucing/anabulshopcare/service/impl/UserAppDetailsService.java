package team.kucing.anabulshopcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.UserAppDetails;
import team.kucing.anabulshopcare.repository.UserAppRepository;


public class UserAppDetailsService implements UserDetailsService {
    @Autowired
    private UserAppRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = this.userRepo.findByEmail(email);
        if (user!=null){
            return new UserAppDetails(user);
        }
        throw new UsernameNotFoundException("User is not found, email: " + email);
    }
}