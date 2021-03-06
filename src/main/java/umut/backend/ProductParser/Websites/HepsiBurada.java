package umut.backend.ProductParser.Websites;

import org.springframework.stereotype.Component;
import umut.backend.ProductParser.HtmlParserFactory;
import umut.backend.ProductParser.WebsiteParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class HepsiBurada extends WebsiteParser {
    @Override
    protected String getProductListCssQuery() {
        return "ul.product-list.results-container.do-flex";
    }

    @Override
    protected List<String> getProductPriceCssQueries() {
        return Arrays.asList("div.price-value", "span.price.product-price");
    }

    @Override
    protected String getProductUrlCssQuery() {
        return "a[href]";
    }

    @Override
    protected String getProductImageCssQuery() {
        return "img[data-src]";
    }

    @Override
    protected String getProductNameCssQuery() {
        return "p.hb-pl-cn";
    }

    @Override
    protected String getProductsCssQuery() {
        return "li.search-item.col.lg-1.md-1.sm-1.custom-hover.not-fashion-flex";
    }

    @Override
    protected List<String> getPageNumberQueries() {
        return Collections.singletonList("\\?sayfa=");
    }

    @Override
    protected String getProductCategoryNameCssQuery() {
        return "span[itemprop='name']";
    }

    @Override
    protected String getProductImageAttributeName() {
        return "data-src";
    }

    @Override
    protected HtmlParserFactory.Website getWebsite() {
        return HtmlParserFactory.Website.HEPSIBURADA;
    }
}
