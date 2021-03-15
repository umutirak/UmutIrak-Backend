package umut.backend.Util.Parser;

import org.apache.http.HttpException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.DTOs.ProductPriceDTO;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Services.Interfaces.IProductCategoriesService;
import umut.backend.Services.Interfaces.IWebsitesService;
import umut.backend.Util.Parser.Websites.Amazon;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


public abstract class WebsiteParser {

    public List<ProductDTO> parseProducts(URI categoryUrl) throws ParseException, HttpException, URISyntaxException {
        var productList = new ArrayList<ProductDTO>();
        int pageNumber = 1;
        var document = getDocument(categoryUrl.toString());
        var productCategory = getProductCategory(categoryUrl, document);

        while (true) {
            var currentUrl = getUrlWithPageNumber(categoryUrl, pageNumber);
            document = getDocument(currentUrl);
            if (document == null)
                break;

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
                if (product == null)
                    continue;
                product.setProductCategory(productCategory);
                productList.add(product);
            }
            pageNumber++;
        }

        return removeDuplicates(productList);
    }

    private List<ProductDTO> removeDuplicates(List<ProductDTO> productList) {
        var seenItems = new HashSet<String>();
        productList.removeIf(i -> !seenItems.add(i.getUrl()));
        return productList;
    }

    private Document getDocument(String currentUrl) throws HttpException {
        var connection = Jsoup.connect(currentUrl);
        if (this instanceof Amazon) {
            connection = connection.userAgent("User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        }

        Document document;
        try {
            document = connection.get();
        } catch (IOException e) {
            if (e instanceof HttpStatusException) {
                return null;
            }
            throw new HttpException("An Error Occurred");
        }

        return document;
    }

    private ProductCategoryDTO getProductCategory(URI categoryUrl, Document document) {
        var productCategoryName = document.select(getProductCategoryNameCssQuery()).last().text();

        var websiteDTO = new WebsiteDTO();
        websiteDTO.setUrl(categoryUrl.toString());

        var productCategory = new ProductCategoryDTO();
        productCategory.setName(productCategoryName);
        productCategory.setUrl(categoryUrl.toString());
        productCategory.setSubPath(productCategoryName.toLowerCase().replace(" ", "-"));
        productCategory.setWebsite(websiteDTO);

        return productCategory;
    }

    private String getUrlWithPageNumber(URI url, int pageNumber) {
        var pageQuery = getPageNumberQuery();
        var split = url.toString().split(pageQuery);
        pageQuery = pageQuery.replace("\\", "");
        return split[0] + pageQuery + pageNumber + split[1].substring(1);
    }

    private ProductDTO parseProductFromElement(Element element, String host) throws ParseException {
        var productPrice = parseProductPrice(element);
        if (productPrice == null)
            return null;

        var productName = element.select(getProductNameCssQuery()).text();
        var productImageUrl = element.select(getProductImageCssQuery()).attr(getProductImageAttributeName());
        var productUrl = parseProductUrl(element, host);

        var product = new ProductDTO();
        product.setName(productName);
        product.setUrl(productUrl);
        product.setImageUrl(productImageUrl);

        var productPriceDTO = new ProductPriceDTO();
        productPriceDTO.setProduct(product);
        productPriceDTO.setPrice(productPrice);
        product.setProductPrices(Collections.singletonList(productPriceDTO));

        return product;
    }


    private String parseProductUrl(Element element, String host) {
        var productUrl = element.select(getProductUrlCssQuery()).attr("href");
        if (productUrl.startsWith("http")) {
            return productUrl;
        }

        var indexOf = productUrl.indexOf("www.");
        if (indexOf != -1) {
            productUrl = productUrl.substring(indexOf + host.length());
        }
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
            var priceParsed = (BigDecimal) getDecimalFormatter().parse(price);
            if (productPrice != null && priceParsed.compareTo(productPrice) > 0)
                continue;

            productPrice = priceParsed;
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

    protected String getProductImageAttributeName() {
        return "src";
    }

    protected abstract String getProductListCssQuery();

    protected abstract List<String> getProductPriceCssQueries();

    protected abstract String getProductUrlCssQuery();

    protected abstract String getProductImageCssQuery();

    protected abstract String getProductNameCssQuery();

    protected abstract String getProductsCssQuery();

    protected abstract String getPageNumberQuery();

    protected abstract String getProductCategoryNameCssQuery();

    protected abstract HtmlParserFactory.Website getWebsite();
}
