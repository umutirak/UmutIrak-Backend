package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Requests.RequestAddProductsByCategoryUrl;
import umut.backend.Services.Interfaces.IProductCategoriesService;
import umut.backend.Services.Interfaces.IProductService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/product")
@CrossOrigin
@RestController
public class ProductController {

    private final IProductService productService;
    private final IProductCategoriesService productCategoriesService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/createByUrl")
    public ResponseEntity<List<ProductDTO>> addProductsByCategoryUrl(@RequestBody RequestAddProductsByCategoryUrl request) {
        productService.addProductsByCategoryUrl(request.getCategoryUrl());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> findProductAllPriceDataById(@PathVariable String productId) {
        ProductDTO productDTO = productService.findProductAllPriceDataById(UUID.fromString(productId));
        if (productDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @GetMapping("/subPath/{subPathName}/{page}")
    public ResponseEntity<List<ProductDTO>> findProductsBySubPath(@PathVariable String subPathName, @PathVariable String page) {
        ProductCategoryDTO productCategory = productCategoriesService.getProductCategoryBySubPath(subPathName);
        if (productCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProductDTO> products = productService.findProductsByCategoryId(productCategory.getId(), Integer.valueOf(page));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
