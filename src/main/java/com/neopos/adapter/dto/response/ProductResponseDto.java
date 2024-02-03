package com.neopos.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"id", "name", "price", "description", "links"})
public class ProductResponseDto extends ResponseWithLinks {
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
}
