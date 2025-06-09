package com.kayky.mapper;

import com.kayky.domain.Category;
import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import org.mapstruct.*;

import java.util.List;
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
    void updateProductFromRequest(ProductPutRequest request, @MappingTarget Product product);

    List<ProductGetResponse> toProductGetResponseList(List<Product> products);

    ProductGetResponse toProductGetResponse(Product product);

    @Mapping(target = "categoryIds", expression = "java(mapCategoriesToIds(product.getCategories()))")
    ProductPostResponse toProductPostResponse(Product product);

    ProductPutResponse toProductPutResponse(Product product);

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
