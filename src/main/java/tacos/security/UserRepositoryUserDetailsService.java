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
    private UserRepository userRepository;
    private DefaultUserHolder defaultUserHolder;
    private PasswordEncoderHolder passwordEncoderHolder;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository,
                                            DefaultUserHolder defaultUserHolder,
                                            PasswordEncoderHolder passwordEncoderHolder) {
        this.userRepository = userRepository;
        this.defaultUserHolder = defaultUserHolder;
        this.passwordEncoderHolder = passwordEncoderHolder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals(defaultUserHolder.getUserName())){
            initDefaultUser();
        }
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }

    private void initDefaultUser(){
        User defaultUser = userRepository.findByUsername(defaultUserHolder.getUserName());
        if(defaultUser == null){
            User finalUser = new User(defaultUserHolder.getUserName(),
                    passwordEncoderHolder.getInstance().encode(defaultUserHolder.getPassword()),
                    defaultUserHolder.getName(),
                    defaultUserHolder.getStreet(),
                    defaultUserHolder.getCity(),
                    defaultUserHolder.getState(),
                    defaultUserHolder.getZip(),
                    defaultUserHolder.getPhoneNumber());
            userRepository.save(finalUser);
            defaultUserHolder.setUser(finalUser);
        }
    }
}