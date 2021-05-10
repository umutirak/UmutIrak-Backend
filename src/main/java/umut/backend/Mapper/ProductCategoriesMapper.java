package umut.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.Entities.ProductCategory;

@Mapper(componentModel = "spring")
public interface ProductCategoriesMapper extends BaseMapper<ProductCategoryDTO, ProductCategory> {
    @Mapping(target = "products", ignore = true)
    @Named(value = "productCategoryWithoutProducts")
    ProductCategoryDTO fromProductCategoryWithoutProducts(ProductCategory dto);
}
