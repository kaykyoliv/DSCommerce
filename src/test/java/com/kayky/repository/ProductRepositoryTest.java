package com.kayky.repository;


import com.kayky.commons.ProductUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(ProductUtils.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductUtils productUtils;
//
//    @Test
//    @DisplayName("find all returns a list with all products")
//    @Order(1)
//    @Sql(value = "/sql/product/init_three_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    void findAll_ShouldReturnListWithAllProducts_WhenSuccessful() {
//        var products = repository.findAll();
//
//        Assertions.assertThat(products).isNotEmpty().hasSize(3);
//    }
//
//    @Test
//    @DisplayName("find by id returns a product with given id")
//    @Order(2)
//    @Sql(value = "/sql/product/init_one_product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(value = "/sql/product/clean_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    void findById_ShouldReturnsProductById_WhenSuccessful() {
//        var id = 1L;
//        var product = repository.findById(id);
//
//        Assertions.assertThat(product).isPresent();
//    }
//
//    @Test
//    @DisplayName("save persists a product")
//    @Order(3)
//    void save_ShouldPersistProduct_WhenSuccessful() {
//        var productToSave = productUtils.productToSave();
//
//        var savedProduct = repository.save(productToSave);
//
//        Assertions.assertThat(savedProduct).isNotNull();
//        Assertions.assertThat(savedProduct.getName()).isEqualTo(productToSave.getName());
//    }
//
//    @Test
//    @DisplayName("update modifies an existing product")
//    @Sql(value = "/sql/product/init_one_product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Order(4)
//    void update_ShouldModifyProduct_WhenSuccessful() {
//        var productToUpdate = productUtils.productToUpdate();
//
//        var updatedProduct = repository.save(productToUpdate);
//
//        Assertions.assertThat(updatedProduct).isNotNull();
//        Assertions.assertThat(updatedProduct.getName()).isEqualTo(productToUpdate.getName());
//    }
//
//
//    @Test
//    @DisplayName("delete removes product by id")
//    @Order(5)
//    void deleteById_ShouldRemoveProduct_WhenSuccessful() {
//        var product = productUtils.productToSave();
//        var productToDelete = repository.save(product);
//
//        Assertions.assertThat(productToDelete.getId()).isNotNull();
//
//        repository.delete(productToDelete);
//        var deletedProduct = repository.findById(productToDelete.getId());
//
//        Assertions.assertThat(deletedProduct).isEmpty();
//    }

}