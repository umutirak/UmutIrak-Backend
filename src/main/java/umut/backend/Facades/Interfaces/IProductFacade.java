package umut.backend.Facades.Interfaces;

import umut.backend.DTOs.ProductDTO;

import java.net.URISyntaxException;
import java.util.List;

public interface IProductFacade {
    void createProduct(ProductDTO productDTO) throws URISyntaxException;

    void createProducts(List<ProductDTO> productList) throws URISyntaxException;
}
