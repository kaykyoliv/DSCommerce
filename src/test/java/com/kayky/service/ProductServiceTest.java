package com.kayky.service;

import com.kayky.commons.ProductUtils;
import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.mapper.ProductMapper;
import com.kayky.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Spy
    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    private final ProductUtils productUtils = new ProductUtils();

    private List<Product> productList;

    @BeforeEach
    void init(){
        productList = productUtils.newProductList();
    }

    @Test
    @DisplayName("find all returns a list with all products")
    void findAll_ShouldReturnListWithAllProducts_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(productList);
        var products = service.findAll();

        Assertions.assertThat(products).hasSize(3);
        Assertions.assertThat(products.getFirst().getName())
                .isEqualTo(productList.getFirst().getName());
    }


    @Test
    @DisplayName("find by id returns a product with given id")
    void findById_ShouldReturnProductById_WhenSuccessful() {
        var expectedProduct = productList.getFirst();

        BDDMockito.when(repository.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));

        var product = service.findByIdOrThrowNotFound(expectedProduct.getId());

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(expectedProduct);
    }

    @Test
    @DisplayName("findById throws ResponseStatusException when product does not exists")
    void findById_ShouldThrowsResponseStatusException_WhenProductIdDoesNotExists() {
        var expectedProduct = productList.getFirst();

        BDDMockito.when(repository.findById(expectedProduct.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findByIdOrThrowNotFound(expectedProduct.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save persists a product")
    void save_ShouldPersistProduct_WhenSuccessful() {
        var productPostRequest = productUtils.productPostRequest();
        var productToSave = mapper.toProduct(productPostRequest);
        var savedProduct = productUtils.savedProduct();

        BDDMockito.when(repository.save(productToSave)).thenReturn(savedProduct);

        var product = service.save(productPostRequest);
        var expectedResponse = mapper.toProductPostResponse(savedProduct);

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }
}