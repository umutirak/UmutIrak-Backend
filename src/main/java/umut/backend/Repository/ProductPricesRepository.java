package umut.backend.Repository;

import umut.backend.Entities.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductPricesRepository extends JpaRepository<ProductPrice, UUID> {
    ProductPrice findFirstByProductIdOrderByCreateDateDesc(UUID productId);
}
