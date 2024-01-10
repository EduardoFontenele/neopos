package com.neopos.adapters.mapper;

import com.neopos.adapters.dto.request.ProductPostRequestDto;
import com.neopos.adapters.dto.response.ProductGetDto;
import com.neopos.adapters.entity.ProductEntity;
import com.neopos.application.core.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract ProductEntity toEntity(Product product);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract Product toDomain(ProductPostRequestDto dto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract Product toDomain(ProductEntity dto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    public abstract ProductGetDto toGetDto(Product product);

    public List<ProductGetDto> toGetDtoList(List<Product> products) {
        return products.stream().map(INSTANCE::toGetDto).toList();
    }
}
