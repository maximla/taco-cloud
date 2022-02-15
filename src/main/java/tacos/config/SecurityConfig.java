package tacos.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import tacos.jdbc.UserRepository;
import tacos.service.UserRepositoryUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * EXAMPLE
     */
//    public static final String DEF_USERS_BY_USERNAME_QUERY =
//            "select username,password,enabled " +
//                    "from users " +
//                    "where username = ?";
//    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
//            "select username,authority " +
//                    "from authorities " +
//                    "where username = ?";
//    public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
//            "select g.id, g.group_name, ga.authority " +
//                    "from groups g, group_members gm, group_authorities ga " +
//                    "where gm.username = ? " +
//                    "and g.id = ga.group_id " +
//                    "and g.id = gm.group_id";
//    @Autowired
//    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * Authorisation restrictions (permissions)
         */
        http
                .authorizeRequests()
                .antMatchers("/design", "/orders").fullyAuthenticated()
//                .antMatchers("/orders").hasRole("user_role")
                .antMatchers("/", "/**").permitAll()
                .and()
                .csrf()
                .disable()//Disabling of CSRF protection

                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
//                .usernameParameter("user")
//                .passwordParameter("pwd")
                .defaultSuccessUrl("/design", true)
                .failureUrl("/login")
                .permitAll()
                .and()
                .csrf()
                .disable() //Disabling of CSRF protection

                .logout()
                .logoutSuccessUrl("/")

                .and()
                .csrf()
                .disable() //Disabling of CSRF protection
        ;


        /**
         * Spring Expression Language (SpEL) example of a complex  restriction
         */
//        .antMatchers("/design", "/orders")
//                .access("hasRole('ROLE_USER') && " +
//                        "T(java.util.Calendar).getInstance().get("+
//                        "T(java.util.Calendar).DAY_OF_WEEK) == " +
//                        "T(java.util.Calendar).TUESDAY")
//                .antMatchers(“/”, "/**").access("permitAll")

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(getUserDetails())
                .passwordEncoder(encoder());


        /**
         * EXAMPLE
         */
        /**
         * LDAP-backed user store
         */
//        auth
//                .ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("passcode");
//        auth
//                .ldapAuthentication()
//                .contextSource()
//                .url("ldap://tacocloud.com:389/dc=tacocloud,dc=com"); //url of LDAP server

//        auth
//                .ldapAuthentication()
//                .contextSource()
//                .root("dc=tacocloud,dc=com"); // spring embedded LDAP server

        /**
         * JDBC-based user store
         */
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        auth
//                .jdbcAuthentication().usersByUsernameQuery(
//                "select username, password, enabled from Users " +
//                        "where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities " +
//                                "where username=?")
//                .passwordEncoder(encoder);

        /**
         * in-memory user store
         */
//        auth
//                .inMemoryAuthentication()
//                .withUser("buzz")
//                .password("infinity")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("woody")
//                .password("bullseye")
//                .authorities("ROLE_USER");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new SCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetails(){
        return new UserRepositoryUserDetailsService(getApplicationContext().getBean(UserRepository.class),
                getApplicationContext().getBean(PasswordEncoder.class));
    }
}