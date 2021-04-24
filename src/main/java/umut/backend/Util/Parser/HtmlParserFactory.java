package umut.backend.Util.Parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HtmlParserFactory {
    private static final Map<Website, WebsiteParser> serviceCache = new HashMap<>();

    @Autowired
    public void initServiceCache(List<WebsiteParser> services) {
        services.forEach(service -> serviceCache.put(service.getWebsite(), service));
    }

    public enum Website {
        HEPSIBURADA("www.hepsiburada.com"),
        AMAZON("www.amazon.com.tr"),
        N11("www.n11.com"),
        TRENDYOL("www.trendyol.com"),
        GITTIGIDIYOR("www.gittigidiyor.com");

        final String host;

        Website(String host) {
            this.host = host;
        }

        public static Website hostOf(String host) {
            return Arrays.stream(Website.values())
                         .filter(website -> website.host.equals(host))
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("Website Not Found !"));
        }
    }

    public static WebsiteParser getHtmlParser(URI website) {
        return serviceCache.get(Website.hostOf(website.getHost()));
    }
}
