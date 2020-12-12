package umut.backend.Batch.Ps5Finder.Models;

public class AmazonSpain extends AmazonWebsite {
    @Override
    public String getTranslatedOutOfStockMessage() {
        return "No disponible.";
    }

    @Override
    public AmazonRegion getAmazonRegion() {
        return AmazonRegion.SPAIN;
    }

    @Override
    public String getNonDigitalProductUrl() {
        return "https://www.amazon.es/PlayStation-5-Mando-inal%C3%A1mbrico-DualSense/dp/B08KKJ37F7/ref=sr_1_2?__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=1LOUJIE8KG8YN&dchild=1&keywords=playstation%2B5&qid=1605980417&sprefix=playst%2Caps%2C206&sr=8-2&th=1";
    }

    @Override
    public String getDigitalProductUrl() {
        return "https://www.amazon.es/PlayStation-5-Mando-inal%C3%A1mbrico-DualSense/dp/B08KJF2D25/ref=sr_1_2?__mk_es_ES=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=1LOUJIE8KG8YN&dchild=1&keywords=playstation%2B5&qid=1605980417&sprefix=playst%2Caps%2C206&sr=8-2&th=1";
    }
}
