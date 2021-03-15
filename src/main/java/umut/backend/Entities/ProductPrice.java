package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_PRICES")
public class ProductPrice {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private BigDecimal price;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @PrePersist
    public void prePersist() {
        if (createDate == null)
            this.createDate = LocalDateTime.now();
    }
}
