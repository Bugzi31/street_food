package co2123.streetfood.repository;

import org.springframework.data.repository.CrudRepository;
import co2123.streetfood.model.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}
