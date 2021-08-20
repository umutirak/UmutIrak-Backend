package umut.backend.Config;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailjetConfig {

    @Value("${app.mailjet.apikey")
    private String mailjetApiKey;

    @Value("${app.mailjet.apiSecretKey")
    private String mailjetApiSecretKey;

    @Bean
    public MailjetClient mailjetClient() {
        ClientOptions options = ClientOptions.builder()
                .apiKey(mailjetApiKey)
                .apiSecretKey(mailjetApiSecretKey)
                .build();

        return new MailjetClient(options);
    }
}
