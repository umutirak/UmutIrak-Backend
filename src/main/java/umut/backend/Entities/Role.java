package umut.backend.Entities;

import lombok.Getter;
import lombok.Setter;
import umut.backend.Enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private UserRole name;
}
