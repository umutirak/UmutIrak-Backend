package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Entities.Product;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.ProductsRepository;
import umut.backend.Services.Interfaces.IProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductsRepository productsRepository;
    private final ProductPricesService productPricesService;
    private final AutoMapper mapper;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(mapper::toProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findProductAllPriceDataById(UUID productId) {
        Optional<Product> optionalProduct = productsRepository.findById(productId);
        if (optionalProduct.isEmpty()) return null;
        Product product = optionalProduct.get();

        return mapper.toProductDTO(product);
    }

    @Override
    public List<ProductDTO> findProductsByCategoryId(UUID categoryId, Integer page) {
        List<Product> products = page == null ? productsRepository.findByCategoryId(categoryId) : productsRepository.findByCategoryId(categoryId, PageRequest.of(page, 10));

        return products.stream()
                       .map(product -> mapper.toProductDTO(product, productPricesService.getLatestProductPriceByProductId(product.getId())))
                       .collect(Collectors.toList());
    }

}
