package umut.backend.Batch.PriceParser;

import lombok.Getter;
import lombok.Setter;
import umut.backend.Entities.Product;
import umut.backend.Entities.ProductPrice;

@Getter
@Setter
public class CustomProductModel {
    private Product product;
    private ProductPrice lastPrice;
    private ProductPrice currentPrice;
}
