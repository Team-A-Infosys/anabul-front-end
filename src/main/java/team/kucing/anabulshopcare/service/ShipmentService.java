package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.ShipmentRequest;
import team.kucing.anabulshopcare.dto.response.ShipmentResponse;
import team.kucing.anabulshopcare.entity.Shipment;

import java.util.List;

public interface ShipmentService {

    List<Shipment> findAllShipping();

    ShipmentResponse createShipment(ShipmentRequest shipmentRequest );

}