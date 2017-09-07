package io.github.tkaczenko.data.repository;

import io.github.tkaczenko.data.model.attribute.AttributeAssociation;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by tkaczenko on 31.03.17.
 */
public interface AttributeAssociationRepository extends CrudRepository<AttributeAssociation, Integer> {
    Set<AttributeAssociation> findAllByAttributeName(String name);

    Set<AttributeAssociation> findAllByProductSku(String sku);
}
