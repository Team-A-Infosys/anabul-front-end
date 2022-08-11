package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import team.kucing.anabulshopcare.dto.response.BuyerResponse;
import team.kucing.anabulshopcare.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserAppService {

    UserResponse signUpSeller(SignupRequest user, MultipartFile file);

    UserResponse signUpBuyer(SignupRequest user, MultipartFile file);

    void deactivateAccount(UUID id);

    List<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUser(UUID id);

    UserResponse updateUser(UpdateUserRequest user, MultipartFile file, UUID id);

    UserResponse updatePasswordUser(PasswordRequest passwordRequest, UUID id);
}