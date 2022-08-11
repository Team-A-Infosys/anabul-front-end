package team.kucing.anabulshopcare.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import team.kucing.anabulshopcare.dto.response.ShipmentResponse;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id", nullable = false)
    private Long shipmentId;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Provinsi provinsi;

    @Column(name = "price")
    private double price;

    @Column(name = "company")
    private String company;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;


    public ShipmentResponse convertToResponse(){
        return ShipmentResponse.builder()
                .address(this.provinsi.getNama())
                .price(this.price)
                .company(this.company).build();


    }

}

