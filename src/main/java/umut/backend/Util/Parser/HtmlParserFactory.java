package umut.backend.Util.Parser;

public class HtmlParserFactory {

    enum Website {
        HEPSIBURADA("www.hepsiburada.com"),
        AMAZON("www.amazon.com.tr"),
        N11("www.n11.com"),
        TRENDYOL("www.trendyol.com");

        final String host;

        Website(String host) {
            this.host = host;
        }

        static Website hostOf(String host) {
            for (Website website : Website.values()) {
                if (website.host.equals(host))
                    return website;
            }
            throw new IllegalArgumentException("Website Not Found !");
        }
    }

    public static WebsiteParser getHtmlParser(Website website) {
        return switch (website) {
            case HEPSIBURADA -> new HepsiBurada();
            case AMAZON -> new Amazon();
            case N11 -> new N11();
            case TRENDYOL -> new Trendyol();
        };
    }
}
