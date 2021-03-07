package umut.backend.Util.Parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Trendyol extends WebsiteParser {

    @Override
    public KeyValuePair getProductListAttributeAndValue() {
        return new KeyValuePair("class", "prdct-cntnr-wrppr");
    }

    @Override
    public List<KeyValuePair> getProductNameAttributeAndValue() {
        return Arrays.asList(new KeyValuePair("class", "prdct-desc-cntnr-name hasRatings"), new KeyValuePair("class", "prdct-desc-cntnr-name"));
    }

    @Override
    public KeyValuePair getProductHrefAttributeAndValue() {
        return new KeyValuePair("class", "p-card-chldrn-cntnr");
    }

    @Override
    public List<KeyValuePair> getPossibleProductPriceAttributeAndValues() {
        return Collections.singletonList(new KeyValuePair("class", "prc-box-sllng"));
    }

    @Override
    KeyValuePair getProductFilterAttributeAndValue() {
        return new KeyValuePair("class", "p-card-wrppr");
    }

    @Override
    String getPageNumberQuery() {
        return "&pi=";
    }
}
