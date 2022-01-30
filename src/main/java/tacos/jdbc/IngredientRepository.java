package tacos.jdbc;

import org.springframework.data.repository.CrudRepository;

import tacos.product.Ingredient;

public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
}
