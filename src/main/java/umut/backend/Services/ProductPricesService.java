package umut.backend.Services;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.ProductPriceDTO;
import umut.backend.Entities.ProductPrice;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.ProductPricesRepository;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.IProductPricesService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductPricesService implements IProductPricesService {
    private final AutoMapper mapper;
    private final ProductPricesRepository pricesRepository;

    @Override
    public void addProductPrices(List<ProductPriceDTO> dtoList) {
        List<ProductPrice> priceList = dtoList.stream().map(mapper::toProductPrice).collect(Collectors.toList());
        pricesRepository.saveAll(priceList);
    }

    @Override
    public BigDecimal getLatestProductPriceByProductId(UUID productId) {
        ProductPrice productPrice = pricesRepository.findFirstByProductIdOrderByCreateDateDesc(productId);
        if (productPrice == null) return null;
        return productPrice.getPrice();
    }
}
