package umut.backend.Services.Interfaces;

import umut.backend.DTOs.ProductPriceDTO;

import java.util.List;
import java.util.UUID;

public interface IProductPricesService {

    void addProductPrice(ProductPriceDTO priceDTO);

    void addProductPrices(List<ProductPriceDTO> dtoList);

    ProductPriceDTO getLatestProductPriceByProductId(UUID productId);
}
