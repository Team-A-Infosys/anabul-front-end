package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;
import team.kucing.anabulshopcare.entity.subaddress.Kota;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;
import team.kucing.anabulshopcare.service.AddressService;

import java.util.List;

@RestController
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;

    @GetMapping("/provinsi.json")
    public List<Provinsi> getProvinsi() {
       return this.addressService.getAllProvinsi();
    }

    @GetMapping("/kota/{id}.json")
    public List<Kota> getKota(@PathVariable(value = "id")String id) {
        return this.addressService.getKota(id);
    }

    @GetMapping("/kecamatan/{id}.json")
    public List<Kecamatan> getKecamatan(@PathVariable(value = "id")String id) {
        return this.addressService.getKecamatan(id);
    }

    @GetMapping("/kelurahan/{id}.json")
    public List<Kelurahan> getKelurahan(@PathVariable(value = "id")String id) {
        return this.addressService.getKelurahan(id);
    }
}