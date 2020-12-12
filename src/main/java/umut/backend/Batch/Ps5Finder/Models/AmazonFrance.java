package umut.backend.Batch.Ps5Finder.Models;

public class AmazonFrance extends AmazonWebsite {
    @Override
    public String getTranslatedOutOfStockMessage() {
        return "Actuellement indisponible.";
    }

    @Override
    public AmazonRegion getAmazonRegion() {
        return AmazonRegion.FRANCE;
    }

    @Override
    public String getNonDigitalProductUrl() {
        return "https://www.amazon.fr/PlayStation-%C3%89dition-Standard-DualSense-Couleur/dp/B08H93ZRK9/ref=sr_1_1?__mk_fr_FR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=OQUP53H2AHWC&dchild=1&keywords=playstation%2B5&qid=1605979754&sprefix=playst%2Caps%2C210&sr=8-1&th=1";
    }

    @Override
    public String getDigitalProductUrl() {
        return "https://www.amazon.fr/PlayStation-%C3%89dition-Standard-DualSense-Couleur/dp/B08H98GVK8/ref=sr_1_1?__mk_fr_FR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=OQUP53H2AHWC&dchild=1&keywords=playstation%2B5&qid=1605979754&sprefix=playst%2Caps%2C210&sr=8-1&th=1";
    }
}
