package team.kucing.anabulshopcare.dto.request;

import lombok.*;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;
import team.kucing.anabulshopcare.entity.subaddress.Kota;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    private Provinsi provinsi;

    private Kota kota;

    private Kecamatan kecamatan;

    private Kelurahan kelurahan;
}