package umut.backend.Services.Interfaces;

import com.mailjet.client.transactional.TransactionalEmail;

public interface IMailService {

    void sendMail(TransactionalEmail mail);
}
