package umut.backend.Batch.PriceParser;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import umut.backend.Entities.Product;
import umut.backend.Entities.ProductPrice;
import umut.backend.Util.MailUtil;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class PriceParserWriterListener implements ItemWriteListener<CustomProductModel> {


    private enum HtmlTableColumns {
        PRODUCT_NAME("Product Name"),
        PRODUCT_CATEGORY("Product Category"),
        PRODUCT_URL("Product Link"),
        PRODUCT_LAST_PRICE("Old Price"),
        PRODUCT_CURRENT_PRICE("Current Price");

        String columnText;

        HtmlTableColumns(String columnText) {
            this.columnText = columnText;
        }

        public String getColumnText() {
            return columnText;
        }
    }

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final BigDecimal PERCENTAGE_AMOUNT = new BigDecimal(85);

    private static final String HTML_TABLE_DATA_TAG = "<td style=\"border: 1px solid black;\">";
    private static final String HTML_TABLE_DATA_TAG_CLOSER = "</td>";

    @Override
    public void beforeWrite(List<? extends CustomProductModel> list) {

    }

    @Override
    public void afterWrite(List<? extends CustomProductModel> list) {
        StringBuilder mailBuilder = new StringBuilder();
        for (CustomProductModel item : list) {
            ProductPrice lastPrice = item.getLastPrice();
            ProductPrice currentPrice = item.getCurrentPrice();
            if (lastPrice == null) {
                continue;
            }

            BigDecimal priceToCompare = percentage(lastPrice.getPrice());
            if (priceToCompare.compareTo(currentPrice.getPrice()) > 0) {
                String tableData = createTableDataFromProduct(item.getProduct(), currentPrice.getPrice(), lastPrice
                        .getPrice());
                mailBuilder.append(tableData);
            }
        }
        if (mailBuilder.length() == 0) {
            return;
        }
        String mailContent = createTableHeader() + mailBuilder.toString() + "</table>";
        sendMail(mailContent);

        System.out.println("Writing Complete");
    }

    @Override
    public void onWriteError(Exception e, List<? extends CustomProductModel> list) {

    }

    private void sendMail(String mailContent) {
        Email from = new Email("umt955@gmail.com");
        String subject = "Price Drops !";
        Content content = new Content("text/html", mailContent);
        Email to = new Email("dujiool@gmail.com");
        Mail mail = new Mail(from, subject, to, content);

        try {
            MailUtil.sendMail(mail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createTableHeader() {
        String initial = "<table style=\"border: 1px solid black;margin-left: auto;margin-right: auto\"><tr>";
        StringBuilder headerBuilder = new StringBuilder(initial);
        for (HtmlTableColumns columns : HtmlTableColumns.values()) {
            headerBuilder.append("<th style=\"border: 1px solid black;\">")
                    .append(columns.getColumnText())
                    .append("</th>");
        }
        headerBuilder.append("</tr>");

        return headerBuilder.toString();
    }

    private String createTableDataFromProduct(Product product, BigDecimal currentPrice, BigDecimal lastPrice) {
        String productName = createTableRowFromField(product.getName());
        String productCategory = createTableRowFromField(product.getProductCategory().getName());
        String productUrl = createTableRowFromField(product.getUrl());
        String productLastPrice = createTableRowFromField(lastPrice);
        String productCurrentPrice = createTableRowFromField(currentPrice);

        return "<tr>" + productName + productCategory + productUrl + productLastPrice + productCurrentPrice + "</tr>";
    }

    private String createTableRowFromField(Object data) {
        return HTML_TABLE_DATA_TAG + data + HTML_TABLE_DATA_TAG_CLOSER;
    }

    private static BigDecimal percentage(BigDecimal base) {
        return base.multiply(PERCENTAGE_AMOUNT).divide(ONE_HUNDRED, BigDecimal.ROUND_HALF_UP);
    }
}
