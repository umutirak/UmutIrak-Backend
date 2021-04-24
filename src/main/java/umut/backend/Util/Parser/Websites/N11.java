package umut.backend.Util.Parser.Websites;

import org.springframework.stereotype.Component;
import umut.backend.Util.Parser.HtmlParserFactory;
import umut.backend.Util.Parser.WebsiteParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class N11 extends WebsiteParser {

    @Override
    protected String getProductListCssQuery() {
        return "section.group.listingGroup.resultListGroup.import-search-view";
    }

    @Override
    protected List<String> getProductPriceCssQueries() {
        return Arrays.asList("p.view-instant-price", "a.newPrice > ins");
    }

    @Override
    protected String getProductUrlCssQuery() {
        return "div.pro > a";
    }

    @Override
    protected String getProductImageCssQuery() {
        return "img.lazy";
    }

    @Override
    protected String getProductNameCssQuery() {
        return "h3.productName";
    }

    @Override
    protected String getProductsCssQuery() {
        return "li.column";
    }

    @Override
    protected List<String> getPageNumberQueries() {
        return Arrays.asList("\\?pg=", "&pg=");
    }

    @Override
    protected String getProductCategoryNameCssQuery() {
        return "div.resultText > h1";
    }

    @Override
    protected String getProductImageAttributeName() {
        return "data-original";
    }

    @Override
    protected HtmlParserFactory.Website getWebsite() {
        return HtmlParserFactory.Website.N11;
    }
}
