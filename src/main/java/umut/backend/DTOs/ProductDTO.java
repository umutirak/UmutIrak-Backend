package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO extends BaseDTO {
    private UUID id;
    private String name;
    private String url;
    private String imageUrl;
    private BigDecimal latestPrice;
    private List<ProductPriceDTO> productPrices;
    private ProductCategoryDTO productCategory;
}
