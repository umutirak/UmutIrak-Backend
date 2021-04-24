package umut.backend.Batch.PriceParser;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import umut.backend.DTOs.ProductDTO;
import umut.backend.Facades.ProductFacade;

import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@Component
public class PriceParserWriter implements ItemWriter<ProductDTO> {
    private final ProductFacade productFacade;

    @Override
    public void write(List<? extends ProductDTO> list) throws URISyntaxException {
        System.out.println("Writing");
        productFacade.createProducts((List<ProductDTO>) list);
    }


}
