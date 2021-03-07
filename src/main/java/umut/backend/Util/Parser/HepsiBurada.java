package umut.backend.Util.Parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HepsiBurada extends WebsiteParser {

    @Override
    public KeyValuePair getProductListAttributeAndValue() {
        return new KeyValuePair("class", "product-list results-container do-flex ");
    }

    @Override
    public List<KeyValuePair> getProductNameAttributeAndValue() {
        return Collections.singletonList(new KeyValuePair("class", "hb-pl-cn"));
    }

    @Override
    public KeyValuePair getProductHrefAttributeAndValue() {
        return new KeyValuePair("data-bind", "click: clickHandler.bind($data)");
    }

    @Override
    public List<KeyValuePair> getPossibleProductPriceAttributeAndValues() {
        return Arrays.asList(new KeyValuePair("class", "price-value"), new KeyValuePair("class", "price product-price"));
    }

    @Override
    KeyValuePair getProductFilterAttributeAndValue() {
        return new KeyValuePair("class", "search-item col lg-1 md-1 sm-1  custom-hover not-fashion-flex");
    }

    @Override
    String getPageNumberQuery() {
        return "\\?sayfa=";
    }
}
