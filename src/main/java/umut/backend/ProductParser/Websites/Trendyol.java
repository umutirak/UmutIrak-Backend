package umut.backend.ProductParser.Websites;

import org.springframework.stereotype.Component;
import umut.backend.ProductParser.HtmlParserFactory;
import umut.backend.ProductParser.WebsiteParser;

import java.util.Collections;
import java.util.List;

@Component
public class Trendyol extends WebsiteParser {

    @Override
    protected String getProductListCssQuery() {
        return "div.prdct-cntnr-wrppr";
    }

    @Override
    protected List<String> getProductPriceCssQueries() {
        return Collections.singletonList("div.prc-box-sllng");
    }

    @Override
    protected String getProductUrlCssQuery() {
        return "a[href]";
    }

    @Override
    protected String getProductImageCssQuery() {
        return "img.p-card-img";
    }

    @Override
    protected String getProductNameCssQuery() {
        return "span.prdct-desc-cntnr-name";
    }

    @Override
    protected String getProductsCssQuery() {
        return "div.p-card-wrppr";
    }

    @Override
    protected List<String> getPageNumberQueries() {
        return Collections.singletonList("&pi=");
    }

    @Override
    protected String getProductCategoryNameCssQuery() {
        return "div.dscrptn > h1";
    }

    @Override
    protected HtmlParserFactory.Website getWebsite() {
        return HtmlParserFactory.Website.TRENDYOL;
    }

}
