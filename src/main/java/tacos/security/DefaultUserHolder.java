package tacos.security;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import tacos.User;

@Component
public final class DefaultUserHolder {
    public final String DEFAULT_USER_USER_NAME = "user";
    public final String DEFAULT_USER_PASSWORD = "123";
    public final String DEFAULT_USER_NAME = "name";
    public final String DEFAULT_USER_STREET = "street";
    public final String DEFAULT_USER_CITY = "city";
    public final String DEFAULT_USER_STATE = "state";
    public final String DEFAULT_USER_ZIP = "123123";
    public final String DEFAULT_USER_PN = "123123123123";
    @Getter
    @Setter
    private User user;
}
