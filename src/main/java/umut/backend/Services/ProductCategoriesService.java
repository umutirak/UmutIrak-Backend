package umut.backend.Services;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.Entities.ProductCategory;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.ProductCategoriesRepository;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.IProductCategoriesService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductCategoriesService implements IProductCategoriesService {

    private final ProductCategoriesRepository categoriesRepository;
    private final AutoMapper mapper;

    @Override
    public ProductCategoryDTO getProductCategoryByName(String categoryName) {
        ProductCategory category = categoriesRepository.findByName(categoryName);
        if (category == null) return null;
        return mapper.toProductCategoryDTO(category);
    }

    @Override
    public ProductCategoryDTO getProductCategoryBySubPath(String subPath) {
        ProductCategory category = categoriesRepository.findBySubPath(subPath);
        if (category == null) return null;
        return mapper.toProductCategoryDTO(category);
    }

    @Override
    public UUID getProductCategoryIdByName(String categoryName) {
        ProductCategory category = categoriesRepository.findByName(categoryName);
        if (category == null) return null;
        return category.getId();
    }

    @Override
    public UUID addCategory(ProductCategoryDTO dto) {
        ProductCategory savedCategory = categoriesRepository.save(mapper.toProductCategory(dto));
        return savedCategory.getId();
    }

    @Override
    public void addCategoryByCategoryUrl(String categoryUrl) {
        Document document;
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(categoryUrl).build();
            Response response = httpClient.newCall(request).execute();
            document = Jsoup.parse(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String categoryName = document.select("span[itemprop]").last().text();
        ProductCategoryDTO category = getProductCategoryByName(categoryName);
        if (category != null)
            throw new IllegalArgumentException("Category Already Exists");

        ProductCategory newCategory = new ProductCategory();
        newCategory.setName(categoryName);
        newCategory.setUrl(categoryUrl);
        newCategory.setSubPath(categoryName.toLowerCase().replace(" ", "-"));
        categoriesRepository.save(newCategory);
    }

    @Override
    public List<ProductCategoryDTO> findAllProductCategories() {
        return categoriesRepository.findAll().stream().map(mapper::toProductCategoryDTO).collect(Collectors.toList());
    }
}
