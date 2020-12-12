package umut.backend.Config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {
    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .url(System.getenv("JPA_URL"))
                .username(System.getenv("JPA_USERNAME"))
                .password(System.getenv("JPA_PASSWORD"))
                .build();
    }
}
