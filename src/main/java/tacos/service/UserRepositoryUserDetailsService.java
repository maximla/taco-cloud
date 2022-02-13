package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tacos.jdbc.UserRepository;
import tacos.security.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.name}")
    private String defaultUserName;

    @Value("${spring.security.user.password}")
    private String defaultUserPassword;

    @Value("${spring.security.user.roles}")
    private String defaultUserRole;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if(defaultUserName.equals(username)){
            user = generateDefaultUserIfNeed();
        } else {
            user = userRepo.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        return user;
    }

    private User generateDefaultUserIfNeed(){
        User defaultUser = userRepo.findByUsername(defaultUserName);
        if(defaultUser == null){
            defaultUser = new User(defaultUserName,
                    passwordEncoder.encode(defaultUserPassword),
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
