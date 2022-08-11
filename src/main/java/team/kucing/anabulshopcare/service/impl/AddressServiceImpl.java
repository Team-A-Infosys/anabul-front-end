package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;
import team.kucing.anabulshopcare.entity.subaddress.Kota;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;
import team.kucing.anabulshopcare.repository.AddressRepository;
import team.kucing.anabulshopcare.repository.subrepo.KecamatanRepository;
import team.kucing.anabulshopcare.repository.subrepo.KelurahanRepository;
import team.kucing.anabulshopcare.repository.subrepo.KotaRepository;
import team.kucing.anabulshopcare.repository.subrepo.ProvinsiRepository;
import team.kucing.anabulshopcare.service.AddressService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

    private ProvinsiRepository provinsiRepository;

    private KotaRepository kotaRepository;

    private KecamatanRepository kecamatanRepository;

    private KelurahanRepository kelurahanRepository;

    @Override
    public Address createNewAddress(Address address){
        return this.addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address, Long id){
        Address findAddress = this.getAddress(id);

        if (address.getProvinsi() != null){
            findAddress.setProvinsi(address.getProvinsi());
        }

        if (address.getKota() != null){
            findAddress.setKota(address.getKota());
        }

        if (address.getKecamatan() != null){
            findAddress.setKecamatan(address.getKecamatan());
        }

        if (address.getKelurahan() != null){
            findAddress.setKelurahan(address.getKelurahan());
        }

        return this.addressRepository.save(findAddress);
    }

    @Override
    public List<Address> getAllAddress(){
        return this.addressRepository.findAll();
    }

    @Override
    public List<Provinsi> getAllProvinsi(){
        return this.provinsiRepository.findAll();
    }

    @Override
    public List<Kota> getKota(String id) {
        return this.kotaRepository.findByIdStartingWith(id);
    }

    @Override
    public List<Kecamatan> getKecamatan(String id) {
        return this.kecamatanRepository.findByIdStartingWith(id);
    }

    @Override
    public List<Kelurahan> getKelurahan(String id) {
        return this.kelurahanRepository.findByIdStartingWith(id);
    }

    @Override
    public Address getAddress(Long id) {
        Optional<Address> optionalAddress = this.addressRepository.findById(id);

        if (optionalAddress.isPresent()){
            return optionalAddress.get();
        }
        throw new RuntimeException("Not Found");
    }
}