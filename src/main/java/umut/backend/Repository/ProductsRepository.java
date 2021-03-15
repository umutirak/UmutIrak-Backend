package umut.backend.Repository;

import org.springframework.data.domain.Pageable;
import umut.backend.DTOs.ProductCategoryDTO;
import umut.backend.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umut.backend.Entities.ProductCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
    Product findByUrl(String url);

    List<Product> findByProductCategory(ProductCategory category);

    List<Product> findByProductCategory(ProductCategory category, Pageable pageable);
}
