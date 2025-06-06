package com.kayky.service;

import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import com.kayky.mapper.ProductMapper;
import com.kayky.repository.ProductRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductGetResponse> findAll() {
        var allProducts = repository.findAll();
        return mapper.toProductGetResponseList(allProducts);
    }

    @Transactional(readOnly = true)
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
        repository.flush();

        return mapper.toProductPutResponse(productToUpdate);
    }

    @Transactional
    public void delete(Long id) {
        assertIfProductExists(id);
        repository.deleteById(id);
    }

    public void assertIfProductExists(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
    }
}
