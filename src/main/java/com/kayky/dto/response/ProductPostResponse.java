package com.kayky.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ProductPostResponse {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;

    private LocalDateTime createdAt;
}
