package umut.backend.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductPriceDTO extends BaseDTO {
    private UUID id;
    private ProductDTO product;
    private BigDecimal price;
}
