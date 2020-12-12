package umut.backend.Repository;

import umut.backend.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);

    AppUser findByUsernameOrEmail(String username, String email);
}
