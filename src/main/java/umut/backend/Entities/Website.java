package umut.backend.Entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "WEBSITES")
@Data
public class Website {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    private String url;
    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductCategory> productCategories;

    @PrePersist
    public void prePersist() {
        if (createDate == null)
            this.createDate = LocalDateTime.now();
    }
}
