package org.example.mapper;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "category", source = "categoryId")
    ProductDTO productToProductDTO(Product product);

    @Mapping(target = "categoryId", source = "category")
    Product productDTOToProduct(ProductDTO productDTO);
}