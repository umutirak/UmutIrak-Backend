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
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(getDigitalProductUrl()).build();
            Response response = httpClient.newCall(request).execute();
            if (response.body() != null){
                String digitalAvailability = Jsoup.parse(response.body().string())
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
            }

            Request nonDigitalRequest = new Request.Builder().url(getNonDigitalProductUrl()).build();
            Response nonDigitalResponse = httpClient.newCall(nonDigitalRequest).execute();
            if (nonDigitalResponse.body() != null){
                String nonDigitalAvailability = Jsoup.parse(nonDigitalResponse.body().string())
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return availability;
    }
}
