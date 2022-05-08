package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tacos.data.IngredientRepository;
import tacos.data.UserRepository;
import tacos.security.PasswordEncoderHolder;

@Profile({"home", "!work"})
@Configuration
public class DevelopmentConfig {
@Bean
    public CommandLineRunner dataLoader(IngredientRepository repo,
                                        UserRepository userRepo,
                                        PasswordEncoderHolder passwordEncoderHolder) {
        /**
         * Initialization of some beans directly for specific application config profile from the application.yml.
         */
        return null;
    }
}
