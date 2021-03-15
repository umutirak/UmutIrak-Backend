package umut.backend.Entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import umut.backend.Enums.UserRole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ROLES")
@Data
public class Role {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private UserRole name;
}
