package tacos.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;

@Slf4j
@Service
public class TacoCloudClient {
//    public Ingredient getIngredientById(String ingredientId) {
//        return restTemplate().getForObject("http://localhost:8080/ingredients/{id}",
//                Ingredient.class, ingredientId);
//    }
//
//    public Ingredient getIngredientById(String ingredientId) {
//        Map<String, String> urlVariables = new HashMap<>();
//        urlVariables.put("id", ingredientId);
//        return restTemplate().getForObject("http://localhost:8080/ingredients/{id}",
//                Ingredient.class, urlVariables);
//    }

    public Ingredient getIngredientById(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(urlVariables);
        return restTemplate().getForObject(url, Ingredient.class);
    }

    public Ingredient getIngredientEntityById(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate().getForEntity("http://localhost:8080/ingredients/{id}",
                        Ingredient.class, ingredientId);
        log.info("Fetched time: {}",
                responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        restTemplate().put("http://localhost:8080/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate().delete("http://localhost:8080/ingredients/{id}",
                ingredient.getId());
    }

//    public Ingredient createIngredient(Ingredient ingredient) {
//        return restTemplate().postForObject("http://localhost:8080/ingredients",
//                ingredient, Ingredient.class);
//    }

//    public java.net.URI createIngredient(Ingredient ingredient) {
//        return restTemplate().postForLocation("http://localhost:8080/ingredients",
//                ingredient);
//    }

    public Ingredient createIngredient(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate().postForEntity("http://localhost:8080/ingredients",
                        ingredient,
                        Ingredient.class);
        log.info("New resource created at {}",
                responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
