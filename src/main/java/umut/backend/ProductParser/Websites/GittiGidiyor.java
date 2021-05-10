package umut.backend.ProductParser.Websites;

import org.springframework.stereotype.Component;
import umut.backend.ProductParser.HtmlParserFactory;
import umut.backend.ProductParser.WebsiteParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GittiGidiyor extends WebsiteParser {
    @Override
    protected String getProductListCssQuery() {
        return "ul.catalog-view.clearfix.products-container";
    }

    @Override
    protected List<String> getProductPriceCssQueries() {
        return Arrays.asList("p.fiyat.price-txt.robotobold.price", "p.fiyat.robotobold.price-txt");
    }

    @Override
    protected String getProductUrlCssQuery() {
        return "a.product-link";
    }

    @Override
    protected String getProductImageCssQuery() {
        return "img.img-cont";
    }

    @Override
    protected String getProductNameCssQuery() {
        return "h3.product-title > span";
    }

    @Override
    protected String getProductsCssQuery() {
        return "li.gg-uw-6.gg-w-8.gg-d-8.gg-t-8.gg-m-24.gg-mw-12.catalog-seem-cell.srp-item-list";
    }

    @Override
    protected List<String> getPageNumberQueries() {
        return Collections.singletonList("\\?sf=");
    }

    @Override
    protected String getProductCategoryNameCssQuery() {
        return "li.last-child > span";
    }

    @Override
    protected HtmlParserFactory.Website getWebsite() {
        return HtmlParserFactory.Website.GITTIGIDIYOR;
    }
}
