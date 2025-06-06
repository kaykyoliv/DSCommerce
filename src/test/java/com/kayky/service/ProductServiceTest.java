package com.kayky.service;

import com.kayky.commons.ProductUtils;
import com.kayky.domain.Product;
import com.kayky.mapper.ProductMapper;
import com.kayky.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
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

        var expectedResponse = productUtils.productGetResponseList();

        BDDMockito.when(mapper.toProductGetResponseList(productList)).thenReturn(expectedResponse);

        var actualResponse = service.findAll();

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }


    @Test
    @DisplayName("find by id returns a product with given id")
    void findById_ShouldReturnProductById_WhenSuccessful() {
        var expectedProduct = productUtils.savedProduct();
        var expectedResponse = productUtils.productGetResponse();

        BDDMockito.when(repository.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));
        BDDMockito.when(mapper.toProductGetResponse(expectedProduct)).thenReturn(expectedResponse);

        var product = service.findByIdOrThrowNotFound(expectedProduct.getId());

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
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
        var savedProduct = productUtils.savedProduct();
        var expectedResponse = productUtils.productPostResponse();

        BDDMockito.when(repository.save(any(Product.class))).thenReturn(savedProduct);
        BDDMockito.when(mapper.toProduct(productPostRequest)).thenReturn(savedProduct);
        BDDMockito.when(mapper.toProductPostResponse(savedProduct)).thenReturn(expectedResponse);

        var actualProduct = service.save(productPostRequest);

        Assertions.assertThat(actualProduct)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("update modifies an existing product")
    void update_ShouldModifyProduct_WhenSuccessful() {
        var savedProduct = productUtils.savedProduct();
        var productId = savedProduct.getId();
        var productPutRequest = productUtils.productPutRequest();
        var expectedResponse = productUtils.productPutResponse();

        BDDMockito.when(repository.findById(productId)).thenReturn(Optional.of(savedProduct));
        BDDMockito.when(repository.save(any(Product.class))).thenReturn(savedProduct);
        BDDMockito.when(mapper.toProductPutResponse(savedProduct)).thenReturn(expectedResponse);

        var actualProduct = service.update(productId, productPutRequest);

        Assertions.assertThat(actualProduct)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(expectedResponse);
    }
}