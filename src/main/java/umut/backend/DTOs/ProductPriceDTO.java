package umut.backend.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ProductPriceDTO {
    private UUID productId;
    private BigDecimal price;
    private Date createDate;
}
