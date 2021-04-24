package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umut.backend.Facades.Interfaces.IProductFacade;
import umut.backend.Requests.RequestAddWebsite;

import java.net.URISyntaxException;
import java.text.ParseException;

@AllArgsConstructor
@RequestMapping("/website")
@CrossOrigin
@RestController
public class WebsiteController {

    private final IProductFacade productFacade;

    // TODO: 24-Apr-21
    @PostMapping("/add")
    public ResponseEntity<Void> addWebsite(@RequestBody RequestAddWebsite request) throws ParseException, HttpException {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
