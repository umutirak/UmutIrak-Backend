package umut.backend.Services.Interfaces;

import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    ProductDTO addProduct(ProductDTO productDTO);

    List<ProductDTO> findAll();

    ProductDTO findProductAllPriceDataById(UUID productId);

    List<ProductDTO> findProductsByCategory(ProductCategoryDTO categoryDTO, Integer page);

    ProductDTO findProductByUrl(String url);

}
