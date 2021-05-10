package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.ProductPriceDTO;
import umut.backend.Entities.ProductPrice;
import umut.backend.Mapper.ProductPriceMapper;
import umut.backend.Repository.ProductPricesRepository;
import umut.backend.Services.Interfaces.IProductPricesService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ProductPricesService implements IProductPricesService {
    private final ProductPriceMapper mapper;
    private final ProductPricesRepository pricesRepository;

    @Override
    public void addProductPrice(ProductPriceDTO priceDTO) {
        pricesRepository.save(mapper.toEntity(priceDTO));
    }

    @Override
    public void addProductPrices(List<ProductPriceDTO> dtoList) {
        List<ProductPrice> priceList = dtoList.stream().map(mapper::toProductPriceWithoutProduct).collect(Collectors.toList());
        pricesRepository.saveAll(priceList);
    }

    @Override
    public ProductPriceDTO getLatestProductPriceByProductId(UUID productId) {
        ProductPrice productPrice = pricesRepository.findFirstByProductIdOrderByCreateDateDesc(productId);
        return mapper.toDTO(productPrice);
    }
}
