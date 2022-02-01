package tacos.jdbc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.product.Taco;

@Repository
public interface TacoRepository
        extends CrudRepository<Taco, Long> {
}
