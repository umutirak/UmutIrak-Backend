package umut.backend.Entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_PRICES")
public class ProductPrice {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "product_id")
    private UUID productId;
    private BigDecimal price;
    private Date createDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Product product;

    @PrePersist
    public void prePersist() {
        if (createDate == null)
            this.createDate = new Date();
    }
}
