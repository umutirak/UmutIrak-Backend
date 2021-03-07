package umut.backend.Util.Parser;

import java.util.Collections;
import java.util.List;

public class Amazon extends WebsiteParser {

    @Override
    public KeyValuePair getProductListAttributeAndValue() {
        return new KeyValuePair("class", "s-main-slot s-result-list s-search-results sg-row");
    }

    @Override
    public List<KeyValuePair> getProductNameAttributeAndValue() {
        return Collections.singletonList(new KeyValuePair("class", "a-size-base-plus a-color-base a-text-normal"));
    }

    @Override
    public KeyValuePair getProductHrefAttributeAndValue() {
        return new KeyValuePair("class", "a-link-normal a-text-normal");
    }

    @Override
    public List<KeyValuePair> getPossibleProductPriceAttributeAndValues() {
        return Collections.singletonList(new KeyValuePair("class", "a-offscreen"));
    }

    @Override
    KeyValuePair getProductFilterAttributeAndValue() {
        return new KeyValuePair("data-component-type", "s-search-result");
    }

    @Override
    String getPageNumberQuery() {
        return "&page=";
    }
}
