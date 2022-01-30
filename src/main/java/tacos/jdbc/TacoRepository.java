package tacos.jdbc;

import org.springframework.data.repository.CrudRepository;

import tacos.product.Taco;

public interface TacoRepository
        extends CrudRepository<Taco, Long> {
}
