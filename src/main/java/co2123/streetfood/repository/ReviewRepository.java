package co2123.streetfood.repository;

import org.springframework.data.repository.CrudRepository;
import co2123.streetfood.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer>
{

}
