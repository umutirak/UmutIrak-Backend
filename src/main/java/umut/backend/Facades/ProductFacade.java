package umut.backend.Facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.DTOs.WebsiteDTO;
import umut.backend.Facades.Interfaces.IProductFacade;
import umut.backend.Services.Interfaces.IProductCategoriesService;
import umut.backend.Services.Interfaces.IProductPricesService;
import umut.backend.Services.Interfaces.IProductService;
import umut.backend.Services.Interfaces.IWebsitesService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductFacade implements IProductFacade {
    private final IProductService productService;
    private final IProductCategoriesService productCategoriesService;
    private final IProductPricesService productPricesService;
    private final IWebsitesService websitesService;

    @Transactional
    @Override
    public void createProduct(ProductDTO productDTO) throws URISyntaxException {
        savePrices(getProductInfo(productDTO));
    }

    @Override
    @Transactional
    public void createProducts(List<ProductDTO> productList) throws URISyntaxException {
        if (productList.isEmpty())
            return;

        var productDTO = productList.get(0);
        var websiteDTO = getProductWebsite(productDTO);
        var productCategoryDTO = getProductCategory(productDTO, websiteDTO);

        productList.forEach(product -> savePrices(getProductInfo(product, productCategoryDTO)));
    }

    private void savePrices(ProductDTO productDTO) {
        if (productDTO == null)
            return;

        productDTO.setProductPrices(productDTO.getProductPrices());
        productDTO.getProductPrices().forEach(i -> i.setProduct(productDTO));
        productPricesService.addProductPrices(productDTO.getProductPrices());
    }

    private ProductDTO getProductInfo(ProductDTO productDTO) throws URISyntaxException {
        var websiteDTO = getProductWebsite(productDTO);
        var productCategoryDTO = getProductCategory(productDTO, websiteDTO);
        return getProductInfo(productDTO, productCategoryDTO);
    }

    private ProductDTO getProductInfo(ProductDTO productDTO, ProductCategoryDTO productCategoryDTO) {
        var product = productService.findProductByUrl(productDTO.getUrl());
        if (product != null) {
            if (productDTO.getProductPrices().get(0).getPrice().compareTo(product.getLatestPrice()) == 0) {
                return null;
            }
        }
        if (product == null) {
            productDTO.setProductCategory(productCategoryDTO);
            product = productService.addProduct(productDTO);
            product.setProductPrices(new ArrayList<>());
        }
        product.setProductPrices(productDTO.getProductPrices());
        return product;
    }

    private ProductCategoryDTO getProductCategory(ProductDTO productDTO, WebsiteDTO websiteDTO) throws URISyntaxException {
        var productCategoryUrl = productDTO.getProductCategory().getUrl();
        var productCategory = productCategoriesService.getProductCategoryByUrl(productCategoryUrl);
        if (productCategory == null) {
            var productCategoryDTO = productDTO.getProductCategory();
            productCategoryDTO.setWebsite(websiteDTO);
            productCategory = productCategoriesService.addCategory(productCategoryDTO);
        }
        return productCategory;
    }

    private WebsiteDTO getProductWebsite(ProductDTO productDTO) throws URISyntaxException {
        var websiteUrl = productDTO.getProductCategory().getWebsite().getUrl();
        var website = websitesService.getWebsiteByUrl(websiteUrl);
        if (website == null) {
            website = websitesService.addWebsite(websiteUrl);
        }
        return website;
    }
}
