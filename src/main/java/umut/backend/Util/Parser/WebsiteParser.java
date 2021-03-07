package umut.backend.Util.Parser;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import umut.backend.Entities.Product;
import umut.backend.Util.Parser.Websites.Amazon;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class WebsiteParser {
    public List<Product> parse(URI categoryUrl) throws ParseException, HttpException {
        var productList = new ArrayList<Product>();
        int pageNumber = 1;
        while (true) {
            var currentUrl = getUrlWithPageNumber(categoryUrl, pageNumber);
            var connection = Jsoup.connect(currentUrl);
            if (this instanceof Amazon) {
                connection = connection.userAgent("User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
            }

            Document document;
            try {
                document = connection.get();
            } catch (IOException e) {
                if (e instanceof HttpStatusException) {
                    break;
                }
                throw new HttpException("An Error Occurred");
            }
            if (!document.baseUri().equals(currentUrl) && pageNumber != 1)
                break;

            var productsGroupElements = document.select(getProductListCssQuery());
            if (productsGroupElements.isEmpty())
                break;

            var productsElements = productsGroupElements.select(getProductsCssQuery());
            if (productsElements.isEmpty())
                break;

            for (Element productElement : productsElements) {
                var product = parseProductFromElement(productElement, categoryUrl.getHost());
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
        var productName = element.select(getProductNameCssQuery()).text();
        var productPrice = parseProductPrice(element);
        var productImageUrl = element.select(getProductImageCssQuery()).attr(getProductImageAttributeName());
        var productUrl = parseProductUrl(element, host);

        var product = new Product();
//        product.setProductName(productName);
//        product.setProductPrice(productPrice);
//        product.setProductUrl(productUrl);
//        product.setProductImageUrl(productImageUrl);

        return product;
    }

    protected String getProductImageAttributeName() {
        return "src";
    }

    private String parseProductUrl(Element element, String host) {
        var productUrl = element.select(getProductUrlCssQuery()).attr("href");
        if (!productUrl.startsWith("http"))
            productUrl = "https://" + host + productUrl;
        return productUrl;
    }

    private BigDecimal parseProductPrice(Element element) throws ParseException {
        BigDecimal productPrice = null;
        var cssQueries = getProductPriceCssQueries();
        for (String cssQuery : cssQueries) {
            var price = element.select(cssQuery).text();
            if (price.isEmpty())
                continue;

            price = price.split(" ")[0];
            productPrice = (BigDecimal) getDecimalFormatter().parse(price);
            break;
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

    protected abstract String getProductListCssQuery();

    protected abstract List<String> getProductPriceCssQueries();

    protected abstract String getProductUrlCssQuery();

    protected abstract String getProductImageCssQuery();

    protected abstract String getProductNameCssQuery();

    protected abstract String getProductsCssQuery();

    protected abstract String getPageNumberQuery();

    protected abstract HtmlParserFactory.Website getWebsite();
}
