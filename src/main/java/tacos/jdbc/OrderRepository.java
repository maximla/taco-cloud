package tacos.jdbc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.order.Order;

@Repository
public interface OrderRepository
        extends CrudRepository<Order, Long> {
}
