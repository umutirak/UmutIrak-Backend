package umut.backend.Util.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class WebsiteParser {
    List<Product> parse(URI categoryUrl) throws IOException, ParseException {
        var productList = new ArrayList<Product>();
        int pageNumber = 1;
        while (true) {
            var currentUrl = getUrlWithPageNumber(categoryUrl, pageNumber);
            var connection = Jsoup.connect(currentUrl);
            if (this instanceof Amazon) {
                connection = connection.userAgent("User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
            }

            var document = connection.get();
            if (!document.baseUri().equals(currentUrl) && pageNumber != 1)
                break;

            var productListKeyValue = getProductListAttributeAndValue();
            var productListElements = document.getElementsByAttributeValue(productListKeyValue.getKey(), productListKeyValue.getValue());
            if (productListElements.isEmpty())
                break;

            var productKeyValue = getProductFilterAttributeAndValue();
            var productElements = productListElements.first().getElementsByAttributeValue(productKeyValue.getKey(), productKeyValue.getValue());
            if (productElements.isEmpty())
                break;

            for (Element element : productElements) {
                var product = parseProductFromElement(element, categoryUrl.getHost());
                productList.add(product);
            }

            pageNumber++;
        }

        return productList;
    }

    private String getUrlWithPageNumber(URI url, int pageNumber) {
        var pageQuery = getPageNumberQuery();
        var split = url.toString().split(pageQuery);
        pageQuery = pageQuery.replace("\\", "");
        return split[0] + pageQuery + pageNumber + split[1].substring(1);
    }

    private Product parseProductFromElement(Element element, String host) throws ParseException {
        var productUrl = parseProductUrl(element, host);
        var productName = parseProductName(element);
        var productPrice = parseProductPrice(element);

        var product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductUrl(productUrl);

        return product;
    }

    private String parseProductUrl(Element element, String host) {
        var hrefKeyValue = getProductHrefAttributeAndValue();
        var productUrlElement = element.getElementsByAttributeValue(hrefKeyValue.getKey(), hrefKeyValue.getValue()).first();
        String productUrl = productUrlElement.attr("href");
        if (host.equals(HtmlParserFactory.Website.TRENDYOL.host))
            productUrl = productUrlElement.getElementsByTag("a").attr("href");

        if (!productUrl.startsWith("http"))
            productUrl = "https://" + host + productUrl;
        return productUrl;
    }

    private String parseProductName(Element element) {
        var possibleNameKeyValues = getProductNameAttributeAndValue();
        String productName = null;
        for (KeyValuePair nameKeyValue : possibleNameKeyValues) {
            productName = element.getElementsByAttributeValue(nameKeyValue.getKey(), nameKeyValue.getValue()).text();
            if (!productName.isEmpty())
                break;
        }

        return productName;
    }

    private BigDecimal parseProductPrice(Element element) throws ParseException {
        var possiblePriceKeyValues = getPossibleProductPriceAttributeAndValues();
        BigDecimal productPrice = null;
        for (KeyValuePair priceKeyValue : possiblePriceKeyValues) {
            var keyValuePrice = element.getElementsByAttributeValue(priceKeyValue.getKey(), priceKeyValue.getValue()).text();
            if (keyValuePrice.isEmpty())
                continue;

            keyValuePrice = keyValuePrice.split(" ")[0];
            var price = (BigDecimal) getDecimalFormatter().parse(keyValuePrice);
            if (productPrice == null || productPrice.compareTo(price) > 0)
                productPrice = price;

        }

        return productPrice;
    }

    private static DecimalFormat getDecimalFormatter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        String pattern = "#,##0.0#";
        DecimalFormat df = new DecimalFormat(pattern, symbols);
        df.setParseBigDecimal(true);

        return df;
    }


    abstract KeyValuePair getProductListAttributeAndValue();

    abstract List<KeyValuePair> getProductNameAttributeAndValue();

    abstract KeyValuePair getProductHrefAttributeAndValue();

    abstract KeyValuePair getProductFilterAttributeAndValue();

    abstract List<KeyValuePair> getPossibleProductPriceAttributeAndValues();

    abstract String getPageNumberQuery();
}
