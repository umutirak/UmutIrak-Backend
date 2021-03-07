package umut.backend.Repository;

import umut.backend.Entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategory, UUID> {
    ProductCategory findByName(String name);

    ProductCategory findBySubPath(String subPath);

    ProductCategory findByUrl(String url);
}
