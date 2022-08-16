package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.dto.request.AddressRequest;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.subaddress.*;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.*;
import team.kucing.anabulshopcare.repository.subrepo.*;
import team.kucing.anabulshopcare.service.UserAppService;
import team.kucing.anabulshopcare.service.uploadimg.UserAvatarService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserAppServiceImpl implements UserAppService{

    private PasswordEncoder passwordEncoder;
    private UserAppRepository userRepo;

    private AddressRepository addressRepository;

    private ProvinsiRepository provinsiRepository;

    private KotaRepository kotaRepository;

    private KecamatanRepository kecamatanRepository;

    private KelurahanRepository kelurahanRepository;

    private RoleRepository roleRepo;

    private final UserAvatarService fileStorageService;



    @Override
    public UserResponse signUpSeller(SignupRequest newUser, MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images-ava/" + fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_SELLER");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public UserResponse signUpBuyer(SignupRequest newUser, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images-ava/" + fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_BUYER");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public void deactivateAccount(UUID id) {
        UserApp userApp = this.findById(id);
        userRepo.delete(userApp);
        log.info("Deactivate account process has been successful");
    }
    @Override
    public List<UserResponse> getAllUsers(Pageable pageable){
        Page<UserApp> userApp = this.userRepo.findAll(pageable);
        List<UserResponse> response = userApp.stream().map(UserApp::convertToResponse).toList();
        log.info("Retrieve all data user " + response);
        return response;
    }

    @Override
    public UserResponse getUser(UUID id){
        Optional<UserApp> userApp = this.userRepo.findById(id);
        if (userApp.isEmpty()){
            throw new ResourceNotFoundException("User is not found");
        }

        UserApp getUserApp = userApp.get();

        log.info("Success get user " + getUserApp.getEmail());
        return getUserApp.convertToResponse();
    }

    @Override
    public UserResponse getUserByEmail(String email){
       UserApp userApp = this.userRepo.findByEmail(email);
        if (userApp == null){
            throw new ResourceNotFoundException("User is not found");
        }

        log.info("Success get user " + userApp.getEmail());
        return userApp.convertToResponse();
    }

    private UserResponse saveUser(SignupRequest request, String fileDownloadUri, Role getRole) {
        UserApp newUser = new UserApp();
        newUser.setImageUrl(fileDownloadUri);
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setRoles(Collections.singleton(getRole));

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())){
            throw new BadRequestException("Password is not match, try again");
        }
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (this.userRepo.existsByEmail(request.getEmail())){
            log.error("Email already registered, try login. Error: " + HttpStatus.BAD_REQUEST);
            throw new BadRequestException("Email already registered, try login");
        }
        newUser.setEmail(request.getEmail());

        if (this.userRepo.existsByPhoneNumber(request.getPhoneNumber())){
            log.error("Phone number already registered, try login. Error: " + HttpStatus.BAD_REQUEST);
            throw new BadRequestException("Phone number already registered");
        }
        newUser.setPhoneNumber(request.getPhoneNumber());
        this.userRepo.save(newUser);

//        Provinsi provinsi = this.provinsiRepository.findByNameIgnoreCase(request.getProvinsi());
//        Kota kota = this.kotaRepository.findByNameIgnoreCase(request.getKota());
//        Kelurahan kelurahan = this.kelurahanRepository.findByNameIgnoreCase(request.getKelurahan());
//        Kecamatan kecamatan = this.kecamatanRepository.findByNameIgnoreCase(request.getKecamatan());

            Address newAddress = new Address();
            newAddress.setProvinsi(request.getAddress().getProvinsi());
            newAddress.setKota(request.getAddress().getKota());
            newAddress.setKecamatan(request.getAddress().getKecamatan());
            newAddress.setKelurahan(request.getAddress().getKelurahan());
            Address saveAddress = this.addressRepository.save(newAddress);

            newUser.setAddress(saveAddress);
            UserApp savedUser = this.userRepo.save(newUser);

            UserApp searchUser = this.userRepo.findByEmail(request.getEmail());
            searchUser.getAddress().setUserApp(savedUser);
            this.userRepo.save(searchUser);


        UserResponse response = newUser.convertToResponse();
        log.info("Success create user: " + response.toString());
        return response;
    }

    private UserApp findById(UUID id) {
        Optional<UserApp> optionalUserApp = userRepo.findById(id);

        if(optionalUserApp.isEmpty()){
            throw new ResourceNotFoundException("User with ID "+id+" Is Not Found");
        }
        return optionalUserApp.get();
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest user, MultipartFile file, UUID id) {
        UserApp userUpdate = this.findById(id);
        if (user.getFirstName() != null) {
            userUpdate.setFirstName(user.getFirstName());
            this.userRepo.save(userUpdate);
            log.info("Success to Update First Name of User with ID : " + userUpdate.getId()+ " into " + user.getFirstName());
        }
        if (user.getLastName()!= null) {
            userUpdate.setLastName(user.getLastName());
            this.userRepo.save(userUpdate);
            log.info("Success to Update Last Name of User with ID : " + userUpdate.getId()+ " into " + user.getLastName());
        }
        if (user.getEmail()!= null) {
            if (this.userRepo.existsByEmail(user.getEmail())) {
                log.error("Cannot use this Email " + user.getEmail() + " try another Email");
                throw new BadRequestException("Cannot use this Email " + user.getEmail()+ " try another Email");
            }
            userUpdate.setEmail(user.getEmail());
            this.userRepo.save(userUpdate);
            log.info("Success to Update Email of User with ID : " + userUpdate.getId()+ " into " + user.getEmail());
        }
        if (user.getAddress() != null){
            this.updateAddressUser(user.getAddress(), id);
        }
        if (user.getPhoneNumber()!= null) {
            if (this.userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
                log.error("Cannot use this Phone Number " + user.getPhoneNumber() + " try another Phone Number");
                throw new BadRequestException("Cannot use this Phone Number " + user.getPhoneNumber()+ " try another Phone Number");
            }
            userUpdate.setPhoneNumber(user.getPhoneNumber());
            this.userRepo.save(userUpdate);
            log.info("Success to Update Phone Number of User with ID : " + userUpdate.getId()+ " into " + user.getPhoneNumber());
        }
        if (!(file.isEmpty())){
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            userUpdate.setImageUrl(fileDownloadUri);
            this.userRepo.save(userUpdate);
            log.info("Success to Update Image of User with ID : " + userUpdate.getId() + " into " + fileDownloadUri);
        }

        UserResponse response = userUpdate.convertToResponse();
        log.info("Success update User " + response.toString());
        return response;
    }

    @Override
    public UserResponse updatePasswordUser(PasswordRequest passwordRequest, UUID id){
        UserApp userApp = this.findById(id);

        if (!Objects.equals(passwordRequest.getPassword(), passwordRequest.getConfirmPassword())){
            throw new BadRequestException("Password is not match, try again");
        }

        userApp.setPassword(passwordRequest.getPassword());
        this.userRepo.save(userApp);

        log.info("Success change the password of user: " + userApp.convertToResponse());
        return userApp.convertToResponse();
    }

    private void updateAddressUser(AddressRequest addressRequest, UUID id){
        UserApp updateAddressUser = this.findById(id);
        Optional<Provinsi> findProvinsi = this.provinsiRepository.findById(addressRequest.getProvinsi().getId());
        if (findProvinsi.isEmpty()){
            throw new ResourceNotFoundException("Provinsi is not found");
        }
        Provinsi getProvinsi = findProvinsi.get();
        updateAddressUser.getAddress().setProvinsi(getProvinsi);

        Optional<Kota> findKota = this.kotaRepository.findById(addressRequest.getKota().getId());
        if (findKota.isEmpty()){
            throw new ResourceNotFoundException("Kota is not found");
        }
        Kota getKota = findKota.get();
        updateAddressUser.getAddress().setKota(getKota);

        Optional<Kecamatan> findKecamatan = this.kecamatanRepository.findById(addressRequest.getKecamatan().getId());
        if (findKecamatan.isEmpty()){
            throw new ResourceNotFoundException("Kecamatan is not found");
        }
        Kecamatan getKecamatan = findKecamatan.get();
        updateAddressUser.getAddress().setKecamatan(getKecamatan);

        Optional<Kelurahan> findKelurahan = this.kelurahanRepository.findById(addressRequest.getKelurahan().getId());
        if (findKelurahan.isEmpty()){
            throw new ResourceNotFoundException("Kelurahan is not found");
        }
        Kelurahan getKelurahan = findKelurahan.get();
        updateAddressUser.getAddress().setKelurahan(getKelurahan);

        this.userRepo.save(updateAddressUser);
    }
}