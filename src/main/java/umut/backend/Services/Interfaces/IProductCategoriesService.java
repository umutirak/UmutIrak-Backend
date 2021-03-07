package umut.backend.Services.Interfaces;

import umut.backend.DTOs.ProductCategoryDTO;

import java.util.List;
import java.util.UUID;

public interface IProductCategoriesService {
    ProductCategoryDTO getProductCategoryByName(String categoryName);

    ProductCategoryDTO getProductCategoryBySubPath(String subPath);

    ProductCategoryDTO getProductCategoryByUrl(String url);

    UUID getProductCategoryIdByName(String categoryName);

    UUID addCategory(ProductCategoryDTO dto);

    void addCategoryByCategoryUrl(String categoryUrl);

    List<ProductCategoryDTO> findAllProductCategories();
}
