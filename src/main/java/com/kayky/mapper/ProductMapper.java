package com.kayky.mapper;

import com.kayky.domain.Category;
import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import com.kayky.dto.response.ProductSummaryResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "categories", source = "categoryIds")
    Product toProduct(ProductPostRequest request, @Context ProductMapperHelper helper);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "categories", source = "categoryIds")
    void updateProductFromRequest(ProductPutRequest request, @MappingTarget Product product, @Context ProductMapperHelper helper);

    ProductGetResponse toProductGetResponse(Product product);

    @Mapping(target = "categoryIds", expression = "java(mapCategoriesToIds(product.getCategories()))")
    ProductPostResponse toProductPostResponse(Product product);

    @Mapping(target = "categoryIds", expression = "java(mapCategoriesToIds(product.getCategories()))")
    ProductPutResponse toProductPutResponse(Product product);

    @Mapping(target = "categoryIds", expression = "java(mapCategoriesToIds(product.getCategories()))")
    ProductSummaryResponse toSummaryResponse(Product product);

    default Page<ProductSummaryResponse> toSummaryPage(Page<Product> page) {
        return page.map(this::toSummaryResponse);
    }

    default Set<Category> mapCategoryIds(Set<Long> ids, @Context ProductMapperHelper helper) {
        return helper.mapCategoryIdsToCategories(ids);
    }

    default Set<Long> mapCategoriesToIds(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

}
