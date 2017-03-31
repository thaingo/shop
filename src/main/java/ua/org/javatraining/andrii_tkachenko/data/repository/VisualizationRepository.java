package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.javatraining.andrii_tkachenko.data.model.Visualization;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface VisualizationRepository extends CrudRepository<Visualization, Integer> {
}
