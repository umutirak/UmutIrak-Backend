package umut.backend.Repository;

import org.springframework.data.domain.Pageable;
import umut.backend.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
    Product findByUrl(String url);

    List<Product> findByCategoryId(UUID categoryId);

    List<Product> findByCategoryId(UUID categoryId, Pageable pageable);
}
