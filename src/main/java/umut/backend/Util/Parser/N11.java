package umut.backend.Util.Parser;

import java.util.Collections;
import java.util.List;

public class N11 extends WebsiteParser {
    @Override
    KeyValuePair getProductListAttributeAndValue() {
        return new KeyValuePair("class", "group listingGroup resultListGroup import-search-view");
    }

    @Override
    List<KeyValuePair> getProductNameAttributeAndValue() {
        return Collections.singletonList(new KeyValuePair("class", "productName bold"));
    }

    @Override
    KeyValuePair getProductHrefAttributeAndValue() {
        return new KeyValuePair("class", "plink");
    }

    @Override
    List<KeyValuePair> getPossibleProductPriceAttributeAndValues() {
        return Collections.singletonList(new KeyValuePair("class", "newPrice"));
    }

    @Override
    KeyValuePair getProductFilterAttributeAndValue() {
        return new KeyValuePair("class", "column");
    }

    @Override
    String getPageNumberQuery() {
        return "\\?pg=";
    }
}
