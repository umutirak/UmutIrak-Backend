package umut.backend.Repository;

import umut.backend.Entities.Role;
import umut.backend.Enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Role, UUID> {
    Role findByName(UserRole name);
}
