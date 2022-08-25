package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.ShipmentRequest;
import team.kucing.anabulshopcare.dto.response.ShipmentResponse;
import team.kucing.anabulshopcare.entity.Shipment;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

import java.util.List;

public interface ShipmentService {

    List<Shipment> findAllShipping();

    Shipment findShipmentPriceByProvinsi(Provinsi provinsi);

    ShipmentResponse createShipment(ShipmentRequest shipmentRequest );

}