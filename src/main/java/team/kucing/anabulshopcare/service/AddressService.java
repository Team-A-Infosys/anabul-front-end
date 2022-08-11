package team.kucing.anabulshopcare.service;

import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;
import team.kucing.anabulshopcare.entity.subaddress.Kota;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

import java.util.List;

public interface AddressService {
    Address createNewAddress(Address address);

    Address updateAddress(Address address, Long id);

    List<Address> getAllAddress();

    List<Provinsi> getAllProvinsi();

    List<Kota> getKota(String id);

    List<Kecamatan> getKecamatan(String id);

    List<Kelurahan> getKelurahan(String id);

    Address getAddress(Long id);
}