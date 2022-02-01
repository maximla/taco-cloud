package tacos.jdbc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.product.Ingredient;

@Repository
public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
}
