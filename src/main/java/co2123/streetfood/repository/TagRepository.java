package co2123.streetfood.repository;

import org.springframework.data.repository.CrudRepository;
import co2123.streetfood.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {
}

