package team.kucing.anabulshopcare.resources.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;
import team.kucing.anabulshopcare.entity.subaddress.Kota;
import team.kucing.anabulshopcare.service.impl.AddressServiceImpl;

import java.util.List;

@Controller
@AllArgsConstructor
public class CreateAddress {

    private AddressServiceImpl addressService;

    @GetMapping(value = {"/address/add"})
    public String createAddress(Model model){
        Address address = new Address();
        model.addAttribute("provinsi", this.addressService.getAllProvinsi());
        model.addAttribute("address", address);
        return "address/add-address";
    }

    @PostMapping("/address/add")
    public String saveAddress(@ModelAttribute("address") Address address){
        Address newAddress = this.addressService.createNewAddress(address);

        return "redirect:/address/add/kota/"+newAddress.getId();
    }

    @GetMapping("/address/add/kota/{id}")
    public String createKota(@PathVariable("id") Long id, Model model){
        Address address = this.addressService.getAddress(id);
        List<Kota> kota = this.addressService.getKota(address.getProvinsi().getId());

        model.addAttribute("address", address);
        model.addAttribute("kota", kota);

        return "address/kota-index";
    }

    @PostMapping("/update/kota/address/{id}")
    public String updateAddress(@PathVariable("id") Long id, @ModelAttribute("address") Address address){
        this.addressService.updateAddress(address, id);
        Address getAddress = this.addressService.getAddress(id);
        return "redirect:/address/add/kecamatan/"+getAddress.getId();
    }

    @GetMapping("/address/add/kecamatan/{id}")
    public String createKecamatan(@PathVariable("id") Long id, Model model){
        Address address = this.addressService.getAddress(id);
        List<Kecamatan> kecamatan = this.addressService.getKecamatan(address.getKota().getId());

        model.addAttribute("address", address);
        model.addAttribute("kecamatan", kecamatan);

        return "address/kecamatan-index";
    }

    @PostMapping("/update/kecamatan/address/{id}")
    public String updateKecamatan(@PathVariable("id") Long id, @ModelAttribute("address") Address address){
        this.addressService.updateAddress(address, id);
        Address getAddress = this.addressService.getAddress(id);
        return "redirect:/address/add/kelurahan/"+getAddress.getId();
    }

    @GetMapping("/address/add/kelurahan/{id}")
    public String createKelurahan(@PathVariable("id") Long id, Model model){
        Address address = this.addressService.getAddress(id);
        List<Kelurahan> kelurahan = this.addressService.getKelurahan(address.getKecamatan().getId());

        model.addAttribute("address", address);
        model.addAttribute("kelurahan", kelurahan);

        return "address/kelurahan-index";
    }

    @PostMapping("/update/kelurahan/address/{id}")
    public String updateKelurahan(@PathVariable("id") Long id, @ModelAttribute("address") Address address){
        this.addressService.updateAddress(address, id);
        Address getAddress = this.addressService.getAddress(id);
        return "redirect:/show-address/"+getAddress.getId();
    }



    @GetMapping("/show-address/{id}")
    public String showAddress(@PathVariable("id") Long id, Model model){
        Address address = this.addressService.getAddress(id);
        model.addAttribute("address", address);
        return "address/show-address";
    }

}