package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Facades.Interfaces.IProductFacade;
import umut.backend.Requests.RequestAddProductsByCategoryUrl;
import umut.backend.Services.Interfaces.IProductCategoriesService;
import umut.backend.Util.Parser.HtmlParserFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/productCategories")
@CrossOrigin
@RestController
public class ProductCategoriesController {
    private final IProductCategoriesService productCategoriesService;
    private final IProductFacade productFacade;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategoryDTO>> findAllProductCategories() {
        List<ProductCategoryDTO> productCategories = productCategoriesService.findAllProductCategories();
        return new ResponseEntity<>(productCategories, HttpStatus.OK);
    }

    @PostMapping("/createByUrl")
    public ResponseEntity<List<ProductDTO>> addProductCategoryByCategoryUrl(@RequestBody RequestAddProductsByCategoryUrl request) throws URISyntaxException, ParseException, HttpException {
        var uri = new URI(request.getCategoryUrl());
        var website = HtmlParserFactory.Website.hostOf(uri.getHost());
        var productList = HtmlParserFactory.getHtmlParser(website).parseProducts(uri);
        productFacade.createProducts(productList);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
