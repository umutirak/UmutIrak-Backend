package umut.backend.Services;

import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.Entities.ProductCategory;
import umut.backend.Mapper.ProductCategoriesMapper;
import umut.backend.Repository.ProductCategoriesRepository;
import umut.backend.Services.Interfaces.IProductCategoriesService;

import java.io.IOException;
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

        var newCategory = new ProductCategory();
        newCategory.setName(categoryName);
        newCategory.setUrl(categoryUrl);
        newCategory.setSubPath(categoryName.toLowerCase().replace(" ", "-"));
        categoriesRepository.save(newCategory);
    }

    @Override
    public List<ProductCategoryDTO> findAllProductCategories() {
        return categoriesRepository.findAll().stream().map(mapper::fromProductCategoryWithoutProducts).collect(Collectors.toList());
    }
}
