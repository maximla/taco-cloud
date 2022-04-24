package tacos.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderHolder {
    private final PasswordEncoder INSTANCE = new BCryptPasswordEncoder();

    public PasswordEncoder getInstance(){
        return INSTANCE;
    }
}
