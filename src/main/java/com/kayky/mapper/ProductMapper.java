package com.kayky.mapper;

import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    List<ProductGetResponse> toProductGetResponseList(List<Product> products);

    ProductGetResponse toProductGetResponse(Product product);

    Product toProduct(ProductPostRequest request);

    ProductPostResponse toProductPostResponse(Product product);
}
