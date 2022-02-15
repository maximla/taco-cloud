package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import tacos.converter.IngredientByIdConverter;
import tacos.jdbc.IngredientRepository;

@SpringBootApplication
public class TacoCloudApplication implements WebMvcConfigurer {
    @Autowired
    private IngredientRepository ingredientRepository;

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new IngredientByIdConverter(ingredientRepository));
    }
}
