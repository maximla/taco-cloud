package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.User;
import tacos.data.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    private DefaultUserSupplier defaultUserSupplier;
    private PasswordEncoderHolder passwordEncoderHolder;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo,
                                            DefaultUserSupplier defaultUserSupplier,
                                            PasswordEncoderHolder passwordEncoderHolder) {
        this.userRepo = userRepo;
        this.defaultUserSupplier = defaultUserSupplier;
        this.passwordEncoderHolder = passwordEncoderHolder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals(defaultUserSupplier.DEFAULT_USER_NAME)){
            initDefaultUser();
        }
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }

    private void initDefaultUser(){
        User defaultUser = userRepo.findByUsername(defaultUserSupplier.DEFAULT_USER_NAME);
        if(defaultUser == null){
            defaultUser = defaultUserSupplier.get();
            User finalUser = new User(defaultUser.getUsername(),
                    passwordEncoderHolder.getInstance().encode(defaultUser.getPassword()),
                    defaultUser.getFullName(),
                    defaultUser.getStreet(),
                    defaultUser.getCity(),
                    defaultUser.getState(),
                    defaultUser.getZip(),
                    defaultUser.getPhoneNumber());
            userRepo.save(finalUser);
        }
    }
}