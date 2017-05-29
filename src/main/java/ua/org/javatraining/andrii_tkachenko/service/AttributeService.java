package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.repository.AttributeAssociationRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.AttributeRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by tkaczenko on 07.05.17.
 */
@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeAssociationRepository attributeAssociationRepository;

    @Autowired
    public AttributeService(AttributeRepository attributeRepository,
                            AttributeAssociationRepository attributeAssociationRepository) {
        this.attributeRepository = attributeRepository;
        this.attributeAssociationRepository = attributeAssociationRepository;
    }

    public List<Attribute> findAll() {
        List<Attribute> list = new ArrayList<>();
        attributeRepository.findAll().forEach(list::add);
        return list;
    }

    public List<Attribute> save(Collection<Attribute> attributes) {
        List<Attribute> list = new ArrayList<>();
        attributeRepository.save(attributes).forEach(list::add);
        return list;
    }

    public Attribute findById(String id) {
        return attributeRepository.findOne(id);
    }

    public void delete(Collection<Attribute> attributes) {
        attributeRepository.delete(attributes);
    }

    //// FIXME: 09.05.17 Change model name isn't id
    public void updateForeign(String oldName, String newName) {
        Attribute newAttribute = attributeRepository.findOne(oldName);
        newAttribute.setName(newName);
        attributeRepository.save(newAttribute);
//        Set<AttributeAssociation> allByAttributeName = attributeAssociationRepository.findAllByAttributeName(oldName);
//        allByAttributeName.parallelStream()
//                .forEach(attributeAssociation -> {
//                    attributeAssociation.setAttribute(newAttribute);
//                    attributeAssociationRepository.save(attributeAssociation);
//                });
    }
}