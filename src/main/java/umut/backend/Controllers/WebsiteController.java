package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umut.backend.Facades.Interfaces.IProductFacade;
import umut.backend.Requests.RequestAddWebsite;
import umut.backend.Util.Parser.HtmlParserFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

@AllArgsConstructor
@RequestMapping("/website")
@CrossOrigin
@RestController
public class WebsiteController {

    private final IProductFacade productFacade;

    @PostMapping("/add")
    public ResponseEntity<Void> addWebsite(@RequestBody RequestAddWebsite request) throws URISyntaxException, ParseException, HttpException {
        var uri = new URI("https://www.hepsiburada.com/playstation-5-c-80757005?sayfa=1");
        var website = HtmlParserFactory.Website.hostOf(uri.getHost());
        var productList = HtmlParserFactory.getHtmlParser(website).parseProducts(uri);
        productFacade.createProducts(productList);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
