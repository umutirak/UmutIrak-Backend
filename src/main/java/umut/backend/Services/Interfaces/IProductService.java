package umut.backend.Services.Interfaces;

import umut.backend.DTOs.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    List<ProductDTO> findAll();

    ProductDTO findProductAllPriceDataById(UUID productId);

    List<ProductDTO> findProductsByCategoryId(UUID categoryId, Integer page);

}
