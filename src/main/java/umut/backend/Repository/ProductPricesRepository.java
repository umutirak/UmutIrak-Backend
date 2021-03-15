package umut.backend.Repository;

import org.springframework.data.jpa.repository.Query;
import umut.backend.Entities.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductPricesRepository extends JpaRepository<ProductPrice, UUID> {
    ProductPrice findFirstByProductIdOrderByCreateDateDesc(UUID productId);

    @Query(value = "SELECT pp from product_prices pp where pp.product_id = ?1 order by pp.create_date desc limit 1", nativeQuery = true)
    ProductPrice findProductLatestPriceById(UUID productId);
}
