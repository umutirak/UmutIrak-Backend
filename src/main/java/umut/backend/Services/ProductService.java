package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.DTOs.ProductPriceDTO;
import umut.backend.Entities.Product;
import umut.backend.Mapper.ProductMapper;
import umut.backend.Repository.ProductsRepository;
import umut.backend.Services.Interfaces.IProductPricesService;
import umut.backend.Services.Interfaces.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductsRepository productsRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        var product = productsRepository.save(mapper.toProductWithoutPrices(productDTO));
        return mapper.fromProduct(product);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(mapper::fromProduct).collect(Collectors.toList());
    }

    @Nullable
    @Override
    public ProductDTO findProductAllPriceDataById(UUID productId) {
        Optional<Product> optionalProduct = productsRepository.findById(productId);
        return optionalProduct.map(mapper::fromProduct).orElse(null);
    }

    @Override
    public List<ProductDTO> findProductsByCategory(ProductCategoryDTO categoryDTO, Integer page) {
//        var productCategory = mapper.toProductCategory(categoryDTO);
//        List<Product> products = page == null ? productsRepository.findByProductCategory(productCategory) : productsRepository.findByProductCategory(productCategory, PageRequest
//                .of(page, 10));
//
//        return products.stream()
//                       .map(product -> mapper.toProductDTO(product, productPricesService.getLatestProductPriceByProductId(product.getId())))
//                       .collect(Collectors.toList());
        return null;
    }

    @Nullable
    @Override
    public ProductDTO findProductByUrl(String url) {
        var product = productsRepository.findByUrl(url);
        if (product == null)
            return null;

        return mapper.fromProduct(product, product.getProductPrices().get(0).getPrice());
    }

}
