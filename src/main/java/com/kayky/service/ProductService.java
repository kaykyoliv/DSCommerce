package com.kayky.service;

import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import com.kayky.dto.response.ProductSummaryResponse;
import com.kayky.mapper.ProductMapper;
import com.kayky.mapper.ProductMapperHelper;
import com.kayky.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final ProductMapperHelper productMapperHelper;


    @Transactional(readOnly = true)
    public Page<ProductSummaryResponse> findAll(Pageable pageable) {
        var allProducts = repository.findAll(pageable);
        return mapper.toSummaryPage((allProducts));
    }

    @Transactional(readOnly = true)
    public ProductGetResponse findByIdOrThrowNotFound(Long id) {
        var product = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        return mapper.toProductGetResponse(product);
    }

    @Transactional
    public ProductPostResponse save(ProductPostRequest postRequest) {
        var productToSave = mapper.toProduct(postRequest, productMapperHelper);
        var productSaved = repository.save(productToSave);
        return mapper.toProductPostResponse(productSaved);
    }

    @Transactional
    public ProductPutResponse update(Long id, ProductPutRequest productRequest) {
        var productToUpdate = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        mapper.updateProductFromRequest(productRequest, productToUpdate, productMapperHelper);
        var updatedProduct = repository.save(productToUpdate);
        repository.flush();

        return mapper.toProductPutResponse(updatedProduct);
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
