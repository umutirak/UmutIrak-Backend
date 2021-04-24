package umut.backend.Batch.PriceParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Services.ProductCategoriesService;
import umut.backend.Util.Parser.HtmlParserFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class PriceParserReader implements ItemReader<ProductDTO> {
    private final ProductCategoriesService productCategoriesService;

    private AtomicInteger productIndex;
    private final List<ProductDTO> customProductModelList = new ArrayList<>();


    @BeforeStep
    public void before() {
        log.info("Initializing Reader");
        List<ProductCategoryDTO> categories = productCategoriesService.findAllProductCategories();
        categories.forEach(i -> initialize(i.getUrl()));
        log.info("Read Complete");

        productIndex = new AtomicInteger(0);
    }

    @Override
    public ProductDTO read() {
        ProductDTO data = null;
        int currentIndex = productIndex.getAndIncrement();

        if (currentIndex < customProductModelList.size()) {
            data = customProductModelList.get(currentIndex);
        } else {
            productIndex.set(0);
        }

        return data;
    }

    public void initialize(String categoryUrl) {
        try {
            URI url = new URI(categoryUrl);
            var productList = HtmlParserFactory.getHtmlParser(url).parseProducts(url);
            customProductModelList.addAll(productList);
        } catch (URISyntaxException | ParseException | HttpException e) {
            log.error(e.getMessage());
        }

    }
}
