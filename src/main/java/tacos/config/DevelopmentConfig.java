package tacos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import tacos.jdbc.IngredientRepository;
import tacos.jdbc.UserRepository;

@Profile({"!prod", "!qa"})
@Configuration
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo, PasswordEncoder encoder) {

        /**
         * alter table Taco_Ingredients add foreign key (taco) references Taco(id)
         * https://github.com/habuma/spring-in-action-5-samples/blob/master/ch06/tacos/src/main/java/tacos/DevelopmentConfig.java
         */
        return null;
    }
}
