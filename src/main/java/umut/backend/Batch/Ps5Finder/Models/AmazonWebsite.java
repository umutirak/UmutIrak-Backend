package umut.backend.Batch.Ps5Finder.Models;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;

@Slf4j
public abstract class AmazonWebsite {
    public abstract String getTranslatedOutOfStockMessage();

    public abstract AmazonRegion getAmazonRegion();

    public abstract String getNonDigitalProductUrl();

    public abstract String getDigitalProductUrl();

    public ProductAvailability getProductAvailability() {
        log.info("Getting product availablity on " + getAmazonRegion());
        ProductAvailability availability = new ProductAvailability();
        availability.setRegion(getAmazonRegion());
        try {
            String digitalAvailability = Jsoup.connect(getDigitalProductUrl())
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get()
                    .getElementById("availability")
                    .tagName("span")
                    .children()
                    .first()
                    .text();

            if (!getTranslatedOutOfStockMessage().equals(digitalAvailability)) {
                availability.setDigitalAvailable(true);
            } else {
                System.out.println("Digital PS5 not available in " + getAmazonRegion().name());
            }

            String nonDigitalAvailability = Jsoup.connect(getNonDigitalProductUrl()).get()
                    .getElementById("availability")
                    .tagName("span")
                    .children()
                    .first()
                    .text();
            if (!getTranslatedOutOfStockMessage().equals(nonDigitalAvailability)) {
                availability.setNonDigitalAvailable(true);
            } else {
                System.out.println("Non Digital PS5 not available in " + getAmazonRegion().name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return availability;
    }
}
