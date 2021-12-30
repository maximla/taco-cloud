package tacos.jdbc;

import tacos.product.Taco;

public interface TacoRepository {
    Taco save(Taco design);
}
