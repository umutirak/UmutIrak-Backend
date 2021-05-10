package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "WEBSITES")
@Getter
@Setter
public class Website extends BaseEntity {
    private String name;
    private String url;
    private LocalDateTime createDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ProductCategory.class)
    private List<ProductCategory> productCategories;

    @PrePersist
    public void prePersist() {
        if (createDate == null)
            this.createDate = LocalDateTime.now();
    }
}
