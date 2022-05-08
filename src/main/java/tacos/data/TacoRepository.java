package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import lombok.NonNull;
import tacos.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    List<Taco> findAll(@NonNull Pageable pageable);
}