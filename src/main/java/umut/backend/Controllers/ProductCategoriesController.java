package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umut.backend.DTOs.ProductCategoryDTO;
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
}
