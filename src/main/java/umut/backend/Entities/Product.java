package umut.backend.Entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    private String url;
    private String imageUrl;
    @Column(name = "category_id")
    private UUID categoryId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private ProductCategory productCategory;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    @OrderBy("createDate desc")
    private List<ProductPrice> productPrices;
}
