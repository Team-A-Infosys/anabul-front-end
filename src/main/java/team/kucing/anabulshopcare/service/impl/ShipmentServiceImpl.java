package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.ShipmentRequest;
import team.kucing.anabulshopcare.dto.response.ShipmentResponse;
import team.kucing.anabulshopcare.entity.Shipment;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.ShipmentRepository;
import team.kucing.anabulshopcare.repository.subrepo.ProvinsiRepository;
import team.kucing.anabulshopcare.service.ShipmentService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    private ShipmentRepository shipmentRepo;

    private final ProvinsiRepository provRepo;

    @Override
    public List<Shipment> findAllShipping() {
        log.info("success get All shipments");
        return this.shipmentRepo.findAll();
    }

    @Override
    public ShipmentResponse createShipment(ShipmentRequest shipmentRequest) {
       Optional<Provinsi> optionalProvinsi = this.provRepo.findById(shipmentRequest.getAddressId());
        if (optionalProvinsi.isEmpty()){
            throw new ResourceNotFoundException("Provinsi is not found");
        }
        Provinsi getProvinsi = optionalProvinsi.get();
        Shipment shipment = new Shipment();
        shipment.setProvinsi(getProvinsi);
        shipment.setPrice(shipmentRequest.getPrice());
        shipment.setCompany("JN-CAT");
        this.shipmentRepo.save(shipment);
        return shipment.convertToResponse();
    }
}