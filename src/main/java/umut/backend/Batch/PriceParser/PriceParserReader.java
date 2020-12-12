package umut.backend.Batch.PriceParser;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import umut.backend.Entities.Product;
import umut.backend.Entities.ProductCategory;
import umut.backend.Entities.ProductPrice;
import umut.backend.Repository.ProductCategoriesRepository;
import umut.backend.Repository.ProductPricesRepository;
import umut.backend.Repository.ProductsRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class PriceParserReader implements ItemReader<CustomProductModel> {

    private final ProductsRepository productsRepository;
    private final ProductCategoriesRepository categoriesRepository;
    private final ProductPricesRepository pricesRepository;

    private AtomicInteger productIndex;
    private List<CustomProductModel> customProductModelList;


    @BeforeStep
    public void before() {
        System.out.println("Initializing Reader");
        List<ProductCategory> categories = categoriesRepository.findAll();
        for (ProductCategory category : categories) {
            initialize(category.getUrl());
        }
        System.out.println("Read Complete");

        productIndex = new AtomicInteger(0);
    }

    @Override
    public CustomProductModel read() {
        CustomProductModel data = null;
        int currentIndex = productIndex.getAndIncrement();

        if (currentIndex < customProductModelList.size()) {
            data = customProductModelList.get(currentIndex);
        } else {
            productIndex.set(0);
        }

        return data;
    }

    public void initialize(String categoryUrl) {
        List<CustomProductModel> productPrices = new ArrayList<>();
        int index = 1;
        while (true) {
            String finalUrl = categoryUrl + "?sayfa=" + index;
            System.out.println("Working on " + finalUrl);
            Document document;
            try {
                document = Jsoup.connect(finalUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (index != 1 && !document.baseUri().equals(finalUrl)) {
                break;
            }

            String ulClass = "product-list results-container do-flex ";
            String productCategoryName = document.select("span[itemprop]").last().text();

            ProductCategory productCategory = categoriesRepository.findByName(productCategoryName);
            if (productCategory == null) {
                ProductCategory category = new ProductCategory();
                category.setName(productCategoryName);
                category.setUrl(categoryUrl);
                productCategory = categoriesRepository.save(category);
            }
            UUID categoryId = productCategory.getId();

            Elements elementList = document.getElementsByClass(ulClass)
                    .first()
                    .children();
            productPrices.addAll(findProductPricesByHtmlElements(elementList, categoryId));
            index++;
        }
        System.out.println(categoryUrl + " Complete");
        customProductModelList = Collections.unmodifiableList(productPrices);
    }

    private List<CustomProductModel> findProductPricesByHtmlElements(Elements elementList, UUID categoryId) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("tr-TR"));
        List<CustomProductModel> customProductList = new ArrayList<>();

        for (Element element : elementList) {
            if (!"li".equals(element.tag().getName())) {
                continue;
            }
            String productUrl = "https://www.hepsiburada.com" + element.select("a[href]").attr("href");

            Elements productInfo = element.select("a").first().children();

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
                Element priceValueElement = productAttributes.getElementsByClass("price-value").first();
                if (priceValueElement == null) continue;
                priceText = priceValueElement.text();
            }

            Number parse;
            try {
                parse = currencyInstance.parse(priceText);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return customProductList;
            }
            BigDecimal currentPrice = new BigDecimal(parse.toString());

            Product existingProduct = productsRepository.findByUrl(productUrl);
            if (existingProduct == null) {
                String imageUrl = productInfo.first()
                        .getElementsByClass("carousel-lazy-item")
                        .select("img[data-src]")
                        .first()
                        .attr("data-src");


                String productName = productAttributes.getElementsByClass("product-title title").first().attr("title");

                Product product = new Product();
                product.setImageUrl(imageUrl);
                product.setName(productName);
                product.setUrl(productUrl);
                product.setCategoryId(categoryId);
                existingProduct = productsRepository.save(product);
            }

            ProductPrice latestPrice = pricesRepository.findFirstByProductIdOrderByCreateDateDesc(existingProduct.getId());
            if (latestPrice != null && currentPrice.compareTo(latestPrice.getPrice()) == 0) {
                continue;
            }

            ProductPrice productPriceData = new ProductPrice();
            productPriceData.setProductId(existingProduct.getId());
            productPriceData.setPrice(currentPrice);

            CustomProductModel productModel = new CustomProductModel();
            productModel.setProduct(existingProduct);
            productModel.setCurrentPrice(productPriceData);
            productModel.setLastPrice(latestPrice);
            customProductList.add(productModel);
        }

        return customProductList;
    }


}
