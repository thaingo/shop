package io.github.tkaczenko.data.model.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by tkaczenko on 27.02.17.
 */
@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(updatable = false, insertable = false)
    private String url;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "parentcategory_category_fkey"))
    @JsonManagedReference
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Category> subCategories;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryAssociation> products;

    public Category() {

    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        return o instanceof Category && this.getId() == ((Category) o).getId();
    }

    @Deprecated
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Set<CategoryAssociation> getProducts() {
        return products;
    }

    public void setProducts(Set<CategoryAssociation> products) {
        this.products = products;
    }
}
