package umut.backend.Batch.PriceParser;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import umut.backend.Repository.ProductPricesRepository;

import java.util.List;

@AllArgsConstructor
@Component
public class PriceParserWriter implements ItemWriter<CustomProductModel> {
    private final ProductPricesRepository pricesRepository;

    @Override
    public void write(List<? extends CustomProductModel> list) {
        System.out.println("Writing");
        for (CustomProductModel item : list) {
            pricesRepository.save(item.getCurrentPrice());
        }
    }


}
