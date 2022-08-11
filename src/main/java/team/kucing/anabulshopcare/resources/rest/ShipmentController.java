package team.kucing.anabulshopcare.resources.rest;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.ShipmentRequest;
import team.kucing.anabulshopcare.dto.response.ShipmentResponse;
import team.kucing.anabulshopcare.entity.Shipment;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.service.impl.ShipmentServiceImpl;

import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "10. Shipment Controller")
public class ShipmentController {

    private final ShipmentServiceImpl shipmentService;

    @GetMapping("/shipments")
    public ResponseEntity<Object> getAllShipment() {
        List<Shipment> response = this.shipmentService.findAllShipping();
        return ResponseHandler.generateResponse("Success get All Shipments", HttpStatus.OK, response);
    }

    @PostMapping("/shipments/add")
    public ResponseEntity<Object> addShipment(@RequestBody ShipmentRequest shipmentRequest) {
       ShipmentResponse response = this.shipmentService.createShipment(shipmentRequest);
        log.info("New Shipment Added");
        return ResponseHandler.generateResponse("success create new shipment", HttpStatus.OK, response);
    }
}