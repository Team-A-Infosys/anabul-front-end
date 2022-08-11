package team.kucing.anabulshopcare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.Shipment;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

   List<Shipment> findAll();

   Shipment findByProvinsi(Provinsi provinsi);


}