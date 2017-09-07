package io.github.tkaczenko.data.repository;

import io.github.tkaczenko.data.model.Visualization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface VisualizationRepository extends CrudRepository<Visualization, Integer> {
    Set<Visualization> findAllByProductSkuAndType(String sku, int type);

    Set<Visualization> findAllByProductSku(String sku);
}
