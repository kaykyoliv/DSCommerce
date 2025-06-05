package com.kayky.mapper;

import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "createdAt", ignore = true)
    void updateProductFromRequest(ProductPutRequest request, @MappingTarget Product product);

    List<ProductGetResponse> toProductGetResponseList(List<Product> products);

    ProductGetResponse toProductGetResponse(Product product);

    Product toProduct(ProductPostRequest request);

    Product toProduct(ProductPutRequest request);

    ProductPostResponse toProductPostResponse(Product product);

    ProductPutResponse toProductPutResponse(Product product);
}
