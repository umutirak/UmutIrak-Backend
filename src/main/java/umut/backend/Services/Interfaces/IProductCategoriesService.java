package umut.backend.Services.Interfaces;

import umut.backend.DTOs.ProductCategoryDTO;

import java.util.List;
import java.util.UUID;

public interface IProductCategoriesService {
    ProductCategoryDTO getProductCategoryByName(String categoryName);

    ProductCategoryDTO getProductCategoryBySubPath(String subPath);

    ProductCategoryDTO getProductCategoryByUrl(String url);

    ProductCategoryDTO getProductCategoryById(UUID id);

    ProductCategoryDTO addCategory(ProductCategoryDTO dto);

    List<ProductCategoryDTO> findAllProductCategories();
}
