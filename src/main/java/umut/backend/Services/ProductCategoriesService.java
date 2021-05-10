package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.Mapper.ProductCategoriesMapper;
import umut.backend.Repository.ProductCategoriesRepository;
import umut.backend.Services.Interfaces.IProductCategoriesService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ProductCategoriesService implements IProductCategoriesService {

    private final ProductCategoriesRepository categoriesRepository;
    private final ProductCategoriesMapper mapper;

    @Nullable
    @Override
    public ProductCategoryDTO getProductCategoryByName(String categoryName) {
        var category = categoriesRepository.findByName(categoryName);
        return mapper.fromProductCategory(category);
    }

    @Nullable
    @Override
    public ProductCategoryDTO getProductCategoryBySubPath(String subPath) {
        var category = categoriesRepository.findBySubPath(subPath);
        return mapper.fromProductCategory(category);
    }

    @Nullable
    @Override
    public ProductCategoryDTO getProductCategoryByUrl(String url) {
        var category = categoriesRepository.findByUrl(url);
        return mapper.fromProductCategoryWithoutProducts(category);
    }

    @Nullable
    @Override
    public ProductCategoryDTO getProductCategoryById(UUID id) {
        var productCategory = categoriesRepository.findById(id);
        return productCategory.map(mapper::fromProductCategory).orElse(null);

    }

    @Override
    public ProductCategoryDTO addCategory(ProductCategoryDTO dto) {
        var productCategory = mapper.toProductCategory(dto);
        return mapper.fromProductCategory(categoriesRepository.save(productCategory));
    }

    @Override
    public List<ProductCategoryDTO> findAllProductCategories() {
        return categoriesRepository.findAll().stream().map(mapper::fromProductCategoryWithoutProducts).collect(Collectors.toList());
    }
}
