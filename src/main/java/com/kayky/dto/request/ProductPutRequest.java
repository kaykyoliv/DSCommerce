package com.kayky.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductPutRequest {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;
}
