package tacos.web.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import lombok.NonNull;
import tacos.Taco;

public class TacoModelAssembler extends RepresentationModelAssemblerSupport<Taco, TacoModel> {
    public TacoModelAssembler() {
        super(TacoApiController.class, TacoModel.class);
    }

    @Override
    public TacoModel toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity);
    }

    public List<TacoModel> toModels(@NonNull List<Taco> entities) {
        return entities.stream().map(taco -> toModel(taco)).collect(Collectors.toList());
    }
}
