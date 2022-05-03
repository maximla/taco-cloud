package tacos.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import tacos.User;

@Component
@ConfigurationProperties(prefix = "security.user.default")
@Data
public final class DefaultUserHolder {
    private String userName;
    private String password;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    private User user;
}
