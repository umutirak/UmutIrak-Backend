package umut.backend.Batch.Ps5Finder.Models;

public class AmazonItaly extends AmazonWebsite {
    @Override
    public String getTranslatedOutOfStockMessage() {
        return "Non disponibile.";
    }

    @Override
    public AmazonRegion getAmazonRegion() {
        return AmazonRegion.ITALY;
    }

    @Override
    public String getNonDigitalProductUrl() {
        return "https://www.amazon.it/Sony-PlayStation-5-Digital-Edition/dp/B08KKJ37F7/ref=sr_1_2?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=96G6LXCSCK5Y&dchild=1&keywords=playstation%2B5&qid=1605980475&rnid=1640607031&s=videogames&sprefix=plays%2Caps%2C234&sr=1-2&th=1";
    }

    @Override
    public String getDigitalProductUrl() {
        return "https://www.amazon.it/Sony-PlayStation-5-Digital-Edition/dp/B08KJF2D25/ref=sr_1_2?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=96G6LXCSCK5Y&dchild=1&keywords=playstation+5&qid=1605980475&rnid=1640607031&s=videogames&sprefix=plays%2Caps%2C234&sr=1-2";
    }
}
