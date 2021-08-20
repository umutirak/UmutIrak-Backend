package umut.backend.Services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.IMailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService implements IMailService {

    private final MailjetClient mailjetClient;

    @Override
    public void sendMail(TransactionalEmail mail) {
        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(mail)
                .build();

        SendEmailsResponse response;
        try {
            response = request.sendWith(mailjetClient);
        } catch (MailjetException e) {
            log.error(e.getMessage());
            return;
        }

        log.info(response.toString());
    }


}
