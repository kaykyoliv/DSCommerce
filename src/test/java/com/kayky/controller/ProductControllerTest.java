package com.kayky.controller;

import com.kayky.commons.FileUtils;
import com.kayky.commons.ProductUtils;
import com.kayky.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @DisplayName("GET /v1/products should return list of products with status 200")
    void findAll_ShouldReturnListWithAllProducts_WhenSuccessful() throws Exception {
        BDDMockito.when(productService.findAll()).thenReturn(productUtils.productGetResponseList());
        var response = fileUtils.readResourceFile("/product/get/all-products-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

        BDDMockito.verify(productService, BDDMockito.times(1)).findAll();
    }

}