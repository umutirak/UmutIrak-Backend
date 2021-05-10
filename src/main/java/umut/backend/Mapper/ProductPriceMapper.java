package umut.backend.Mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import umut.backend.DTOs.ProductPriceDTO;
import umut.backend.Entities.ProductPrice;

@Mapper(componentModel = "spring")
public interface ProductPriceMapper extends BaseMapper<ProductPriceDTO, ProductPrice> {
    @IterableMapping(qualifiedByName = "defaultToEntity")
    @Mapping(target = "product.productPrices", ignore = true)
    ProductPrice toProductPriceWithoutProduct(ProductPriceDTO productPriceDTO);

    @Mapping(target = "product", ignore = true)
    @Named(value = "fromProductPriceWithoutProduct")
    ProductPriceDTO fromProductPriceWithoutProduct(ProductPrice productPrice);
}
