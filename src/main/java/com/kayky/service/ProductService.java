package com.kayky.service;

import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import com.kayky.mapper.ProductMapper;
import com.kayky.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    public List<ProductGetResponse> findAll() {
        var allProducts = repository.findAll();
        return mapper.toProductGetResponseList(allProducts);
    }

    public ProductGetResponse findByIdOrThrowNotFound(Long id) {
        var product = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        return mapper.toProductGetResponse(product);
    }

    @Transactional
    public ProductPostResponse save(ProductPostRequest productRequest) {
        var productEntity = mapper.toProduct(productRequest);
        productEntity = repository.save(productEntity);
        return mapper.toProductPostResponse(productEntity);
    }

    @Transactional
    public ProductPutResponse update(Long id, ProductPutRequest productRequest) {
        var productToUpdate = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        mapper.updateProductFromRequest(productRequest, productToUpdate);
        repository.save(productToUpdate);

        return mapper.toProductPutResponse(productToUpdate);
    }

    public void assertIfProductExists(Long id) {
        findByIdOrThrowNotFound(id);
    }


}
