package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_PRICES")
public class ProductPrice extends BaseEntity {
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
