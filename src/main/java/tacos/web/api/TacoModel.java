package tacos.web.api;

import java.util.Date;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.Getter;
import tacos.Ingredient;

@Data
@Relation(value="taco",collectionRelation = "tacos")
public class TacoModel extends RepresentationModel<TacoModel> {
    @Getter
    private final String name;
    @Getter
    private final Date createdAt;
    @Getter
    private final CollectionModel<Ingredient> ingredients;
}

