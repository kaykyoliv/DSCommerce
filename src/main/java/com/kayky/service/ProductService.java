package com.kayky.service;

import com.kayky.domain.Product;
import com.kayky.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> findAll(){
        return repository.findAll();
    }


}
