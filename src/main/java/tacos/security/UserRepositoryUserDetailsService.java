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
        if(username.equals(defaultUserHolder.DEFAULT_USER_USER_NAME)){
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
        User defaultUser = userRepository.findByUsername(defaultUserHolder.DEFAULT_USER_USER_NAME);
        if(defaultUser == null){
            User finalUser = new User(defaultUserHolder.DEFAULT_USER_USER_NAME,
                    passwordEncoderHolder.getInstance().encode(defaultUserHolder.DEFAULT_USER_PASSWORD),
                    defaultUserHolder.DEFAULT_USER_NAME,
                    defaultUserHolder.DEFAULT_USER_STREET,
                    defaultUserHolder.DEFAULT_USER_CITY,
                    defaultUserHolder.DEFAULT_USER_STATE,
                    defaultUserHolder.DEFAULT_USER_ZIP,
                    defaultUserHolder.DEFAULT_USER_PN);
            userRepository.save(finalUser);
            defaultUserHolder.setUser(finalUser);
        }
    }
}