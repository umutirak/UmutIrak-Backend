package umut.backend.Batch.Ps5Finder.Models;

public class AmazonGermany extends AmazonWebsite {
    @Override
    public String getTranslatedOutOfStockMessage() {
        return null;
    }

    @Override
    public AmazonRegion getAmazonRegion() {
        return AmazonRegion.GERMANY;
    }

    @Override
    public String getNonDigitalProductUrl() {
        return null;
    }

    @Override
    public String getDigitalProductUrl() {
        return null;
    }
}
