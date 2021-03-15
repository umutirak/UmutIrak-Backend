package umut.backend.Mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.Entities.ProductCategory;

@Mapper(componentModel = "spring")
public interface ProductCategoriesMapper {
    @Named(value = "defaultProductCategories")
    ProductCategoryDTO fromProductCategory(ProductCategory productCategory);

    @IterableMapping(qualifiedByName = "defaultProduct")
    ProductCategory toProductCategory(ProductCategoryDTO dto);

    @Mapping(target = "products", ignore = true)
    @Named(value = "productCategoryWithoutProducts")
    ProductCategoryDTO fromProductCategoryWithoutProducts(ProductCategory dto);
}
