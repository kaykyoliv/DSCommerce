package com.kayky.mapper;

import com.kayky.commons.CategoryUtils;
import com.kayky.commons.ProductUtils;
import com.kayky.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageImpl;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
    private final ProductMapperHelper helper = new MockProductMapperHelper();

    // Mock manual do helper
    private static class MockProductMapperHelper extends ProductMapperHelper {
        @Override
        public Set<Category> mapCategoryIdsToCategories(Set<Long> ids) {
            return ids.stream()
                    .map(id -> CategoryUtils.savedCategory())
                    .collect(Collectors.toSet());
        }
    }

    @Test
    @DisplayName("toProduct should map all fields from PostRequest")
    void toProduct_FromPostRequest_MapsCorrectly() {
        var request = ProductUtils.productPostRequest();
        var product = mapper.toProduct(request, helper);

        assertThat(product.getName()).isEqualTo(request.getName());
        assertThat(product.getDescription()).isEqualTo(request.getDescription());
        assertThat(product.getPrice()).isEqualTo(request.getPrice());
    }

    @Test
    @DisplayName("toProduct should ignore id and timestamps")
    void toProduct_ShouldIgnoreIdAndTimestamps() {
        var product = mapper.toProduct(ProductUtils.productPostRequest(), helper);
        assertThat(product.getId()).isNull();
        assertThat(product.getCreatedAt()).isNull();
        assertThat(product.getUpdatedAt()).isNull();
    }

    @Test
    @DisplayName("updateProductFromRequest should update mutable fields")
    void updateProductFromRequest_UpdatesCorrectly() {
        var request = ProductUtils.productPutRequest();
        var product = ProductUtils.savedProduct();

        mapper.updateProductFromRequest(request, product, helper);

        assertThat(product.getName()).isEqualTo(request.getName());
        assertThat(product.getDescription()).isEqualTo(request.getDescription());
        assertThat(product.getPrice()).isEqualTo(request.getPrice());
    }


    @Test
    @DisplayName("toProductPutResponse should map all fields including ID")
    void toProductPutResponse_MapsCorrectly() {
        var product = ProductUtils.savedProduct();
        var response = mapper.toProductPutResponse(product);

        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getDescription()).isEqualTo(product.getDescription());
        assertThat(response.getCategoryIds()).containsExactly(1L);
    }

    @Test
    @DisplayName("toProductGetResponse should map all fields correctly")
    void toProductGetResponse_MapsCorrectly() {
        var product = ProductUtils.savedProduct();
        var response = mapper.toProductGetResponse(product);

        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    @DisplayName("toProductPostResponse should map all fields including categoryIds")
    void toProductPostResponse_MapsCorrectly() {
        var product = ProductUtils.savedProduct();
        var response = mapper.toProductPostResponse(product);

        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getDescription()).isEqualTo(product.getDescription());
        assertThat(response.getCategoryIds()).containsExactly(1L);
    }
    @Test
    @DisplayName("toSummaryResponse should map essential fields")
    void toSummaryResponse_MapsCorrectly() {
        var product = ProductUtils.savedProduct();
        var response = mapper.toSummaryResponse(product);

        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getDescription()).isEqualTo(product.getDescription());
        assertThat(response.getCategoryIds()).containsExactly(1L);
    }

    @Test
    @DisplayName("toSummaryPage should convert all page elements")
    void toSummaryPage_ConvertsAllElements() {
        var productList = ProductUtils.newProductList();
        var page = new PageImpl<>(productList);

        var resultPage = mapper.toSummaryPage(page);

        assertThat(resultPage.getContent())
                .hasSize(productList.size())
                .allSatisfy(response -> {
                    assertThat(response.getId()).isNotNull();
                    assertThat(response.getName()).isNotBlank();
                });
    }

    @Test
    @DisplayName("mapCategoriesToIds should return null for null input")
    void mapCategoriesToIds_ReturnsNullForNullInput() {
        assertThat(mapper.mapCategoriesToIds(null)).isNull();
    }

    @Test
    @DisplayName("mapCategoriesToIds should return correct IDs")
    void mapCategoriesToIds_ReturnsCorrectIds() {
        var categories = Set.of(
                CategoryUtils.savedCategory()
        );

        assertThat(mapper.mapCategoriesToIds(categories))
                .containsExactlyInAnyOrder(1L);
    }
}