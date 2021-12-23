package tacos.product;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Taco {
    private List<Ingredient> ingredients = new ArrayList<>();
    public void addIngredient(@NonNull Ingredient ingredient){
        ingredients.add(ingredient);
    }
}
