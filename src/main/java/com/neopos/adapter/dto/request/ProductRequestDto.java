package com.neopos.adapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
}
