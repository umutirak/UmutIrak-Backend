package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String url;
    private String imageUrl;
    private BigDecimal latestPrice;
    private List<ProductPriceDTO> productPrices;
}
