package umut.backend.Batch.Ps5Finder;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.TrackOpens;
import com.mailjet.client.transactional.TransactionalEmail;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import umut.backend.Batch.Ps5Finder.Models.ProductAvailability;
import umut.backend.Util.MailUtil;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Ps5FinderWriter implements ItemWriter<ProductAvailability> {

    @Override
    public void write(List<? extends ProductAvailability> list) {
        List<AbstractMap.SimpleEntry<String, String>> availabilityList = list.stream()
                .filter(q -> q.isDigitalAvailable() || q.isNonDigitalAvailable())
                .flatMap(q -> {
                    AbstractMap.SimpleEntry<String, String> nonDigital = null;
                    AbstractMap.SimpleEntry<String, String> digital = null;
                    if (q.isNonDigitalAvailable()) {
                        nonDigital = new AbstractMap.SimpleEntry<>(q.getRegion().name(), "Non Digital PS5");
                    }
                    if (q.isDigitalAvailable()) {
                        digital = new AbstractMap.SimpleEntry<>(q.getRegion().name(), "Digital PS5");
                    }
                    return Stream.of(nonDigital, digital);
                })
                .collect(Collectors.toList());

        if (!availabilityList.isEmpty()) {
            sendMail(availabilityList);
        }
    }

    private static void sendMail(List<AbstractMap.SimpleEntry<String, String>> productAvailableRegions) {

        String availability = productAvailableRegions.stream()
                .map(q -> q.getKey() + " has " + q.getValue())
                .reduce(System.lineSeparator(), (partial, element) -> partial + System.lineSeparator() + element);

        TransactionalEmail mail = TransactionalEmail
                .builder()
                .to(new SendContact("dujiool@gmail.com"))
                .from(new SendContact("umt955@gmail.com"))
                .htmlPart("PS5 Available Regions " + availability)
                .subject("Price Drops !")
                .trackOpens(TrackOpens.ENABLED)
                .build();


        try {
            MailUtil.sendMail(mail);
        } catch (MailjetException e) {
            e.printStackTrace();
        }

    }
}
