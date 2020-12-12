package umut.backend.Services;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.ProductDTO;
import umut.backend.DTOs.ProductPriceDTO;
import umut.backend.Entities.Product;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.ProductsRepository;
import umut.backend.Services.Interfaces.IProductService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductsRepository productsRepository;
    private final ProductCategoriesService productCategoriesService;
    private final ProductPricesService productPricesService;
    private final AutoMapper mapper;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productsRepository.findAll();
        return products.stream().map(mapper::toProductDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addProductsByCategoryUrl(String categoryUrl) {
//        AbstractMap.SimpleEntry<String, Document> categoryEntry = ParseUtil.getProductCategoryNameFromUrlWithDocument(categoryUrl);
//        String ulClass = "product-list results-container do-flex ";
//
//        UUID categoryId = productCategoriesService.getProductCategoryIdByName(categoryEntry.getKey());
//
//        Elements elementList = document.getElementsByClass(ulClass)
//                .first()
//                .children();
//        List<ProductPriceDTO> productPrices = findProductPricesByHtmlElements(elementList, categoryId);
//        productPricesService.addProductPrices(productPrices);
    }

    @Override
    public ProductDTO findProductAllPriceDataById(UUID productId) {
        Optional<Product> optionalProduct = productsRepository.findById(productId);
        if (!optionalProduct.isPresent()) return null;
        Product product = optionalProduct.get();

        return mapper.toProductDTO(product);
    }

    @Override
    public List<ProductDTO> findProductsByCategoryId(UUID categoryId, Integer page) {
        List<Product> products;
        if (page == null) {
            products = productsRepository.findByCategoryId(categoryId);
        } else {
            products = productsRepository.findByCategoryId(categoryId, PageRequest.of(page, 10));
        }

        return products.stream()
                .map(product -> mapper.toProductDTO(product, productPricesService.getLatestProductPriceByProductId(product
                        .getId())))
                .collect(Collectors.toList());
    }

    private List<ProductPriceDTO> findProductPricesByHtmlElements(Elements elementList, UUID categoryId) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("tr-TR"));
        List<ProductPriceDTO> productPrices = new ArrayList<>();

        for (Element element : elementList) {
            if (!"li".equals(element.tag().getName())) {
                continue;
            }
            String productUrl = "https://www.hepsiburada.com" + element.select("a[href]")
                    .attr("href");

            Elements productInfo = element.select("a")
                    .first()
                    .children();

            Element productAttributes = productInfo.get(1);
            if (productInfo.size() > 2)
                productAttributes = productInfo.get(2);

            Elements priceElement = productAttributes
                    .getElementsByClass("price product-price");

            String priceText;
            if (!priceElement.isEmpty()) {
                priceText = priceElement.first()
                        .text();
            } else {
                priceText = productAttributes
                        .getElementsByClass("price-value")
                        .first()
                        .text();
            }

            Number parse;
            try {
                parse = currencyInstance.parse(priceText);
            } catch (ParseException e) {
                e.printStackTrace();
                return productPrices;
            }
            BigDecimal currentPrice = new BigDecimal(parse.toString());

            Product existingProduct = productsRepository.findByUrl(productUrl);
            if (existingProduct == null) {
                String imageUrl = productInfo.first()
                        .getElementsByClass("carousel-lazy-item")
                        .select("img[data-src]")
                        .first()
                        .attr("data-src");


                String productName = productAttributes
                        .getElementsByClass("product-title title")
                        .first()
                        .attr("title");

                Product product = new Product();
                product.setImageUrl(imageUrl);
                product.setName(productName);
                product.setUrl(productUrl);
                product.setCategoryId(categoryId);
                existingProduct = productsRepository.save(product);
            }

            BigDecimal latestPrice = productPricesService.getLatestProductPriceByProductId(existingProduct.getId());
            if (latestPrice != null && currentPrice.compareTo(latestPrice) == 0) {
                continue;
            }

            ProductPriceDTO productPriceData = new ProductPriceDTO();
            productPriceData.setProductId(existingProduct.getId());
            productPriceData.setPrice(currentPrice);
            productPrices.add(productPriceData);
        }

        return productPrices;
    }
}
