package umut.backend.Batch.Ps5Finder.Models;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        availability.setDigitalAvailable(getAvailability(true));
        availability.setNonDigitalAvailable(getAvailability(false));
        return availability;
    }

    private boolean getAvailability(boolean isDigitalAvailability) {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(isDigitalAvailability ? getDigitalProductUrl() : getNonDigitalProductUrl())
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            log.info("Response code is " + response.code());
            if (response.code() == 200 && response.body() != null) {
                String body = response.body().string();
                String digitalAvailability = Jsoup.parse(body)
                        .getElementById("availability")
                        .tagName("span")
                        .children()
                        .first()
                        .text();

                if (!getTranslatedOutOfStockMessage().equals(digitalAvailability)) {
                    return true;
                } else {
                    log.info((isDigitalAvailability ? "" : "Non ") + "Digital PS5 not available in " + getAmazonRegion()
                            .name());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
