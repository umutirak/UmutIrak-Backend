package umut.backend.Services.Interfaces;

import umut.backend.DTOs.ProductPriceDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IProductPricesService {
    void addProductPrices(List<ProductPriceDTO> dtoList);

    BigDecimal getLatestProductPriceByProductId(UUID productId);
}
