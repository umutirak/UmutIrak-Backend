package umut.backend.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/heroku")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class HerokuController {

    private final DataSource ds;

    @GetMapping
    public void heroku() throws SQLException {
        Connection connection = ds.getConnection();
        System.out.println(connection.getMetaData().getConnection());
        System.out.println(connection.getMetaData().getUserName());
        System.out.println(connection.getMetaData().getURL());
        log.info("Heroku wake up.");
    }
}
