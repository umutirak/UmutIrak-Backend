package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Requests.RequestAddProductsByCategoryUrl;
import umut.backend.Services.Interfaces.IProductCategoriesService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/productCategories")
@CrossOrigin
@RestController
public class ProductCategoriesController {
    private final IProductCategoriesService productCategoriesService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategoryDTO>> findAllProductCategories() {
        List<ProductCategoryDTO> productCategories = productCategoriesService.findAllProductCategories();
        return new ResponseEntity<>(productCategories, HttpStatus.OK);
    }

    @PostMapping("/createByUrl")
    public ResponseEntity<List<ProductDTO>> addProductCategoryByCategoryUrl(@RequestBody RequestAddProductsByCategoryUrl request) {
        productCategoriesService.addCategoryByCategoryUrl(request.getCategoryUrl());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
