package umut.backend.Util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Slf4j
public class MailUtil {

    @Value("${app.sendgrid.apikey")
    private static String sendGridApiKey;

    public static void sendMail(Mail mail) throws IOException {
        log.info("MAIL SENDING IS CURRENTLY DISABLED");
//        SendGrid sg = new SendGrid(sendGridApiKey);
//        Request request = new Request();
//        request.setMethod(Method.POST);
//        request.setEndpoint("mail/send");
//        request.setBody(mail.build());
//        Response response = sg.api(request);
//        System.out.println(response.getStatusCode());
//        System.out.println(response.getBody());
//        System.out.println(response.getHeaders());
    }
}
