package tacos.jdbc;

import org.springframework.data.repository.CrudRepository;

import tacos.order.Order;

public interface OrderRepository
        extends CrudRepository<Order, Long> {
}
