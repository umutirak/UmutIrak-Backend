package umut.backend.ProductParser.Websites;

import org.springframework.stereotype.Component;
import umut.backend.ProductParser.HtmlParserFactory;
import umut.backend.ProductParser.WebsiteParser;

import java.util.Collections;
import java.util.List;

@Component
public class Amazon extends WebsiteParser {
    @Override
    protected String getProductListCssQuery() {
        return "div.s-main-slot.s-result-list.s-search-results.sg-row";
    }

    @Override
    protected List<String> getProductPriceCssQueries() {
        return Collections.singletonList("span.a-offscreen");
    }

    @Override
    protected String getProductUrlCssQuery() {
        return "a.a-link-normal";
    }

    @Override
    protected String getProductImageCssQuery() {
        return "img.s-image";
    }

    @Override
    protected String getProductNameCssQuery() {
        return "span.a-size-base-plus";
    }

    @Override
    protected String getProductsCssQuery() {
        return "[data-component-type='s-search-result']";
    }

    @Override
    protected List<String> getPageNumberQueries() {
        return Collections.singletonList("&page=");
    }

    @Override
    protected String getProductCategoryNameCssQuery() {
        return "li.a-spacing-micro.s-navigation-indent-1";
    }

    @Override
    protected HtmlParserFactory.Website getWebsite() {
        return HtmlParserFactory.Website.AMAZON;
    }
}
