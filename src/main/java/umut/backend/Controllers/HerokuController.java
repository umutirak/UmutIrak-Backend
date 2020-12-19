package umut.backend.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/heroku")
@CrossOrigin
@Slf4j
public class HerokuController {

    @GetMapping
    public void heroku() throws SQLException {
        log.info("Heroku wake up.");
    }
}
