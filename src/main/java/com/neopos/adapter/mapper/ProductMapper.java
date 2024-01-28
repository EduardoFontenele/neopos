package com.neopos.adapter.mapper;

import com.neopos.adapter.dto.request.ProductRequestDto;
import com.neopos.adapter.dto.response.ProductResponseDto;
import com.neopos.adapter.entity.ProductEntity;
import com.neopos.application.core.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract ProductEntity toEntity(Product product);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract Product toDomain(ProductRequestDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract Product toDomain(ProductEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract ProductResponseDto toGetDto(Product product);

    public List<ProductResponseDto> toGetDtoList(List<Product> products) {
        return products.stream().map(INSTANCE::toGetDto).toList();
    }
}
