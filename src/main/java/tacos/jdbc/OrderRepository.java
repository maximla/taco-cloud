package tacos.jdbc;

import tacos.order.Order;

public interface OrderRepository {
    Order save(Order order);
}
