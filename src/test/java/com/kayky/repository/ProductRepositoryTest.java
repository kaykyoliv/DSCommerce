package com.kayky.repository;


import com.kayky.commons.ProductUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.kayky"})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductUtils productUtils;

    @Test
    @DisplayName("find all returns a list with all products")
    @Order(1)
    @Sql(value = "/sql/product/init_three_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) void findAll_ShouldReturnListWithAllProducts_WhenSuccessful() {
        var products = repository.findAll();
        Assertions.assertThat(products).isNotEmpty().hasSize(3);
    }

    @Test
    @DisplayName("find by id returns a product with given id")
    @Order(2)
    @Sql(value = "/sql/product/init_one_product.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/product/clean_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_ShouldReturnsProductById_WhenSuccessful() {
        var id = 1L;
        var product = repository.findById(id);

        Assertions.assertThat(product).isPresent();
    }

    @Test
    @DisplayName("save persists a product")
    @Order(3)
    void save_ShouldPersistProduct_WhenSuccessful() {
        var product = productUtils.createNewProduct();

        var savedProduct = repository.save(product);

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getName()).isEqualTo(product.getName());
    }


}