package team.kucing.anabulshopcare.dto.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShipmentResponse {

    private double price;

    private String address;

    private String company;

}
