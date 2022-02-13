package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.jdbc.UserRepository;
import tacos.security.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    @Value("${spring.security.user.name}")
    private String defaultUserName;

    @Value("${spring.security.user.password}")
    private String defaultUserPassword;

    @Value("${spring.security.user.roles}")
    private String defaultUserRole;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
        generateDefaultUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    private User generateDefaultUser(){
        User defaultUser = userRepo.findByUsername(defaultUserName);
        if(defaultUser == null){
            defaultUser = new User(defaultUserName,
                    defaultUserPassword,
                    "defaultUserName",
                    "defaultUserStreet",
                    "defaultUserCity",
                    "defaultUserState",
                    "DefaultUserZip",
                    "(000)000-00-00");
            userRepo.save(defaultUser);
        }
        return defaultUser;
    }
}
