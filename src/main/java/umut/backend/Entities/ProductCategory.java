package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "PRODUCT_CATEGORIES")
public class ProductCategory extends BaseEntity {
    private String name;
    private String url;
    private LocalDateTime createDate;
    private String subPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "website_id", referencedColumnName = "id")
    private Website website;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "productCategory")
    private List<Product> products;

    @PrePersist
    public void prePersist() {
        if (createDate == null)
            this.createDate = LocalDateTime.now();
    }
}
