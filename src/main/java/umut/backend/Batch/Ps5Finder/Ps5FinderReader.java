package umut.backend.Batch.Ps5Finder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import umut.backend.Batch.Ps5Finder.Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Ps5FinderReader implements ItemReader<ProductAvailability> {
    private AtomicInteger index;

    static List<AmazonWebsite> websites = new ArrayList<>();

    static {
        websites.add(new AmazonItaly());
        websites.add(new AmazonFrance());
        websites.add(new AmazonSpain());
    }

    @BeforeStep
    public void before() {
        log.info("Before Ps5Finder Reader");
        index = new AtomicInteger(0);
    }

    @Override
    public ProductAvailability read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        ProductAvailability data = null;
        int currentIndex = index.getAndIncrement();
        log.info("Reading ps5 reader index of " + currentIndex);
        if (currentIndex < websites.size()) {
            AmazonWebsite currentWebsite = websites.get(currentIndex);
            log.info("Currently working on " + currentWebsite.getAmazonRegion());
            data = currentWebsite.getProductAvailability();
        } else {
            index.set(0);
        }

        return data;
    }
}
