package team.kucing.anabulshopcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.Checkout;
import team.kucing.anabulshopcare.entity.UserApp;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, UUID> {
    List<Checkout> findByUserAppAndIsPaid(UserApp userApp, boolean isPaid);
}