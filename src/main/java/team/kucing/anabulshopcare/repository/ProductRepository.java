package team.kucing.anabulshopcare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findByIsPublishedAndNameContainingIgnoreCase(boolean status, String name, Pageable pageable);

    Page<Product> findByUserAppAndNameContainingIgnoreCase(UserApp userApp, String name, Pageable pageable);

    Page<Product> findByLocationIgnoreCase(String location, Pageable pageable);

    Page<Product> findByPriceBetween(double startPrice, double endPrice, Pageable pageable);

    Page<Product> findByIsPublished(boolean status, Pageable pageable);

    Page<Product> findByUserApp(Pageable pageable, UserApp userApp);
}