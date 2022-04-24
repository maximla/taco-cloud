package tacos.security;

import org.springframework.stereotype.Component;

import tacos.User;

@Component
public final class DefaultUserSupplier {
    public static String DEFAULT_USER_NAME = "user";
    public static String DEFAULT_USER_PASSWORD = "123";
    public User get(){
        return new User(DEFAULT_USER_NAME,
                DEFAULT_USER_PASSWORD,
                "name",
                "street",
                "city",
                "state",
                "123123",
                "123123123123");
    }
}
