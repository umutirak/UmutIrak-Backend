package umut.backend.Util;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class MailUtil {

//    @Value("${app.sendgrid.apikey")
//    private static String sendGridApiKey;

    @Value("${app.mailjet.apiKey}")
    private static String apiKey;
    @Value("${app.mailjet.apiSecretKey}")
    private static String apiSecretKey;

//    public static void sendMail(Mail mail) throws IOException {
//        log.info("MAIL SENDING IS CURRENTLY DISABLED");
////        SendGrid sg = new SendGrid(sendGridApiKey);
////        Request request = new Request();
////        request.setMethod(Method.POST);
////        request.setEndpoint("mail/send");
////        request.setBody(mail.build());
////        Response response = sg.api(request);
////        System.out.println(response.getStatusCode());
////        System.out.println(response.getBody());
////        System.out.println(response.getHeaders());
//    }

    public static void sendMail(TransactionalEmail mail) throws MailjetException {
        ClientOptions options = ClientOptions.builder()
                .apiKey(apiKey)
                .apiSecretKey(apiSecretKey)
                .build();

        MailjetClient client = new MailjetClient(options);

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(mail)
                .build();

        SendEmailsResponse response = request.sendWith(client);
        log.info(response.toString());

    }
}
