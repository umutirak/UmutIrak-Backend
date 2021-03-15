package umut.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Entities.Product;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {ProductPriceMapper.class, ProductCategoriesMapper.class})
public interface ProductMapper {

    @Named(value = "defaultProduct")
    Product toProduct(ProductDTO productDTO);

    @Named("defaultProduct")
    ProductDTO fromProduct(Product product);

    @Mapping(target = "productPrices", qualifiedByName = "fromProductPriceWithoutProduct")
    @Mapping(target = "productCategory", qualifiedByName = "productCategoryWithoutProducts")
    @Mapping(target = "latestPrice", source = "latestPrice")
    ProductDTO fromProduct(Product product, BigDecimal latestPrice);

    @Mapping(target = "productPrices", ignore = true)
    @Named(value = "productWithoutPrices")
    Product toProductWithoutPrices(ProductDTO dto);

}
