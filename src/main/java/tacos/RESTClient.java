package tacos;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RESTClient {
    private static RestTemplate rest = new RestTemplate();
    private static String INGREDIENT_BY_ID_TEMPLATE = "http://localhost:8080/data-api/ingredients/{id}";
    private static String INGREDIENT_CREATING_URL = "http://localhost:8080/data-api/ingredients";

    public static void main(String[] args){
//        String id = "FLTO";
//        Ingredient ingredient = getIngredientById(id);
//        System.out.println(ingredient);
//        updateIngredient(new Ingredient(id, "UPDATED NAME", Ingredient.Type.PROTEIN));
//        ingredient = getIngredientById(id);
//        System.out.println(ingredient);

        String id = "N_ID";
        URI created = createIngredientLocation(new Ingredient(id, "CREATED", Ingredient.Type.PROTEIN));
        System.out.println(created);
    }

    //                              OBJECT GETTERS

    public static Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject(INGREDIENT_BY_ID_TEMPLATE,
                Ingredient.class, ingredientId);
    }

    public Ingredient getIngredientByIdMap(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        return rest.getForObject(INGREDIENT_BY_ID_TEMPLATE,
                Ingredient.class, urlVariables);
    }

    public Ingredient getIngredientByIdURI(String ingredientId) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);
        URI url = UriComponentsBuilder
                .fromHttpUrl(INGREDIENT_BY_ID_TEMPLATE)
                .build(urlVariables);
        return rest.getForObject(url, Ingredient.class);
    }

    //                              ENTITY RESULT GETTERS

    public static ResponseEntity<Ingredient> getIngredientByIdER(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity =
                rest.getForEntity(INGREDIENT_BY_ID_TEMPLATE,
                        Ingredient.class, ingredientId);
        log.info("Fetched time: {}",
                responseEntity.getHeaders().getDate());
        return responseEntity;
    }

    //                              OBJECT UPDATING

    public static void updateIngredient(Ingredient ingredient) {
        rest.put(INGREDIENT_BY_ID_TEMPLATE,
                ingredient, ingredient.getId());
    }

    //                              OBJECT DELETING

    public static void deleteIngredient(String id) {
        rest.delete(INGREDIENT_BY_ID_TEMPLATE, id);
    }

    //                              OBJECT CREATING

    public static Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject(INGREDIENT_CREATING_URL,
                ingredient, Ingredient.class);
    }

    public static URI createIngredientLocation(Ingredient ingredient) {
        return rest.postForLocation(INGREDIENT_CREATING_URL, ingredient);
    }

    public static ResponseEntity<Ingredient> createIngredientEntity(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                rest.postForEntity(INGREDIENT_CREATING_URL,
                        ingredient,
                        Ingredient.class);
        log.info("New resource created at {}",
                responseEntity.getHeaders().getLocation());
        return responseEntity;
    }
}
