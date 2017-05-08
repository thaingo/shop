package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.dao.ProductDAO;
import ua.org.javatraining.andrii_tkachenko.data.dao.VisualizationDAO;
import ua.org.javatraining.andrii_tkachenko.data.dao.attribute.AttributeAssociationDAO;
import ua.org.javatraining.andrii_tkachenko.data.dao.attribute.AttributeDAO;
import ua.org.javatraining.andrii_tkachenko.data.dao.category.CategoryAssociationDAO;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;

import java.util.HashSet;

/**
 * Created by tkaczenko on 03.05.17.
 */
//// TODO: 04.05.17 Need to refactor
@Service
public class AdminProductService {
    private final ProductDAO productDAO;
    private final CategoryAssociationDAO categoryAssociationDAO;
    private final AttributeAssociationDAO attributeAssociationDAO;
    private final VisualizationDAO visualizationDAO;

    @Autowired
    public AdminProductService(ProductDAO productDAO, CategoryAssociationDAO categoryAssociationDAO,
                               AttributeAssociationDAO attributeAssociationDAO, VisualizationDAO visualizationDAO) {
        this.productDAO = productDAO;
        this.categoryAssociationDAO = categoryAssociationDAO;
        this.attributeAssociationDAO = attributeAssociationDAO;
        this.visualizationDAO = visualizationDAO;
    }

    public Product getFullProduct(String sku) {
        Product product = productDAO.get(sku);
        product.setCategories(new HashSet<>(categoryAssociationDAO.getAllByProduct(sku)));
        product.setAttributes(new HashSet<>(attributeAssociationDAO.getAllByProduct(sku)));
        product.setVisualizations(new HashSet<>(visualizationDAO.getAllByProduct(sku)));
        return product;
    }

    public void create(Product product) {
        productDAO.create(product);
        product.getCategories().parallelStream()
                .forEach(categoryAssociationDAO::create);
        product.getAttributes().parallelStream()
                .forEach(attributeAssociationDAO::create);
    }

    public void update(Product product) {
        productDAO.update(product);
        categoryAssociationDAO.deleteByProduct(product.getSku());
        product.getCategories().parallelStream()
                .forEach(categoryAssociationDAO::create);
        attributeAssociationDAO.deleteByProduct(product.getSku());
        product.getAttributes().parallelStream()
                .forEach(attributeAssociationDAO::create);
    }
}
