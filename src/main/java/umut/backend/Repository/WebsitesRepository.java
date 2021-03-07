package umut.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umut.backend.Entities.Website;

import java.util.UUID;

@Repository
public interface WebsitesRepository extends JpaRepository<Website, UUID> {
    Website findByUrl(String url);
}
