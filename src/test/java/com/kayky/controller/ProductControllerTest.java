package com.kayky.controller;

import com.kayky.commons.FileUtils;
import com.kayky.commons.ProductUtils;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductController.class)
@Import({FileUtils.class, ProductUtils.class})
@MockitoBean(types = JpaMetamodelMappingContext.class)
class ProductControllerTest {

    private final static String URL = "/v1/products";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProductUtils productUtils;

    @BeforeEach
    void init() {
    }

    @Test
    @DisplayName("GET /v1/products - Should return list of products with status 200")
    void findAll_ShouldReturnListWithAllProducts_WhenSuccessful() throws Exception {
        BDDMockito.when(productService.findAll()).thenReturn(productUtils.productGetResponseList());

        var expectedJsonResponse = fileUtils.readResourceFile("/product/get/all-products-200.json");

        mockMvc.perform(get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse));

        BDDMockito.verify(productService, BDDMockito.times(1)).findAll();
    }

    @Test
    @DisplayName("GET /v1/products/{id} - Should return a product with given id with status 200")
    void findById_ShouldReturnProduct_WhenIdExists() throws Exception {
        var expectedResponse = productUtils.productGetResponse();
        var expectedResponseId = expectedResponse.getId();

        BDDMockito.when(productService.findByIdOrThrowNotFound(expectedResponseId)).thenReturn(expectedResponse);

        var expectedJsonResponse = fileUtils.readResourceFile("/product/get/product-by-id-200.json");

        mockMvc.perform(get(URL + "/{id}", expectedResponseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse));

        BDDMockito.verify(productService).findByIdOrThrowNotFound(expectedResponseId);
    }

    @Test
    @DisplayName("GET /v1/products/{id} - Should return 404 when product ID doesn't exist")
    void findById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        var nonExistingId = 99L;

        BDDMockito.when(productService.findByIdOrThrowNotFound(nonExistingId)).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        mockMvc.perform(get(URL + "/{id}", nonExistingId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /v1/products should create and return new product with status 201")
    void save_ShouldPersistProduct_WhenSuccessful() throws Exception {
        var jsonRequest = fileUtils.readResourceFile("/product/post/request-product-200.json");
        var expectedJsonResponse = fileUtils.readResourceFile("/product/post/response-product-201.json");
        var expectedResponse = productUtils.productPostResponse();

        BDDMockito.when(productService.save(any(ProductPostRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(post(URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse))
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/v1/products/1"));


        BDDMockito.verify(productService).save(any(ProductPostRequest.class));
    }


    @Test
    @DisplayName("PUT /v1/products/{id} - Should update and return updated product with status 200")
    void update_ShouldModifyProduct_WhenSuccessful() throws Exception {
        var jsonRequest = fileUtils.readResourceFile("/product/put/request-product-200.json");
        var expectedJsonResponse = fileUtils.readResourceFile("/product/put/response-product-200.json");
        var expectedResponse = productUtils.productPutResponse();
        var existingId = expectedResponse.getId();

        BDDMockito.when(productService.update(eq(existingId), any(ProductPutRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(put(URL + "/{id}", existingId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse));

        BDDMockito.verify(productService).update(eq(existingId), any(ProductPutRequest.class));
    }

    @Test
    @DisplayName("PUT /v1/products/{id} - Should return 404 when product ID doesn't exist")
    void update_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        var jsonRequest = fileUtils.readResourceFile("/product/put/request-product-200.json");
        var nonExistingId = 99L;

        BDDMockito.when(productService.update(eq(nonExistingId), any(ProductPutRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        mockMvc.perform(put(URL + "/{id}", nonExistingId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        BDDMockito.verify(productService).update(eq(nonExistingId), any(ProductPutRequest.class));
    }

    @Test
    @DisplayName("DELETE /v1/products/{id} - Should successfully remove product when ID exists")
    void delete_ShouldRemoveProduct_WhenSuccessful() throws Exception {
        var savedProduct = productUtils.savedProduct();
        var productId = savedProduct.getId();

        BDDMockito.doNothing().when(productService).delete(productId);

        mockMvc.perform(delete(URL + "/{id}", productId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        BDDMockito.verify(productService).delete(productId);
        //BDDMockito.verifyNoMoreInteractions(productService);
    }

    @Test
    @DisplayName("DELETE /v1/products/{id} - Should return 404 when product ID doesn't exist")
    void delete_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        var nonExistingId = 99L;

        BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"))
                        .when(productService)
                        .delete(nonExistingId);

        mockMvc.perform(delete(URL + "/{id}", nonExistingId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}