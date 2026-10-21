package co2123.streetfood.repository;

import co2123.streetfood.model.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Integer>
{
}
