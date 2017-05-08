package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;
import ua.org.javatraining.andrii_tkachenko.data.repository.AttributeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkaczenko on 07.05.17.
 */
@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;

    @Autowired
    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public List<Attribute> findAll() {
        List<Attribute> list = new ArrayList<>();
        for (Attribute attribute : attributeRepository.findAll()) {
            list.add(attribute);
        }
        return list;
    }
}
