package umut.backend.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/heroku")
@CrossOrigin
@Slf4j
public class HerokuController {

    @GetMapping
    public void heroku() {
        log.info(new Date().toString());
        log.info("Heroku wake up.");
    }
}
