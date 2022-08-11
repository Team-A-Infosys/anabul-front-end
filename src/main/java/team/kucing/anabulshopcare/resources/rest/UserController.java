package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.service.impl.UserAppServiceImpl;

import javax.validation.Valid;

import java.io.File;
import java.util.List;
import java.util.UUID;


@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "01. User Controller")
@OpenAPIDefinition(info = @Info(title = "Anabul Shop & Care Documentation",
        description = "API Documentation of e-Commerce Anabul Shop & Care", version = "v1", license = @License(name ="Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
public class UserController {
    private final UserAppServiceImpl userAppService;
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(@ParameterObject Pageable pageable){
        List<UserResponse> response = this.userAppService.getAllUsers(pageable);
        return ResponseHandler.generateResponse("Success Retrieve All Users", HttpStatus.OK, response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") UUID id){
        UserResponse response = this.userAppService.getUser(id);
        return ResponseHandler.generateResponse("Success get user", HttpStatus.OK, response);
    }

    @PostMapping(value = "/signup/seller", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupSeller(@RequestPart MultipartFile file, @RequestPart SignupRequest userApp){
        UserResponse response = this.userAppService.signUpSeller(userApp, file);
        return ResponseHandler.generateResponse("Success Create User", HttpStatus.CREATED, response);
    }

    @PostMapping(value = "/signup/buyer", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupBuyer(@RequestPart MultipartFile file, @RequestPart SignupRequest userApp){
        UserResponse response = this.userAppService.signUpBuyer(userApp, file);
        return ResponseHandler.generateResponse("Success Create User", HttpStatus.CREATED, response);
    }

    @PutMapping(value = "/user/{id}/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid UpdateUserRequest user) {
        UserResponse response = this.userAppService.updateUser(user, file, id);
        return ResponseHandler.generateResponse("Success Update The User", HttpStatus.OK, response);
    }

    @PutMapping("/user/{id}/changePassword")
    public ResponseEntity<Object> changePassword(@PathVariable(value = "id") UUID id, @RequestBody PasswordRequest passwordRequest){
        UserResponse response = this.userAppService.updatePasswordUser(passwordRequest, id);
        return ResponseHandler.generateResponse("Success change the password", HttpStatus.OK, response);
    }

    @DeleteMapping("/user/{id}/deactivate")
    public ResponseEntity<Object> deactivateAccount(@PathVariable UUID id) {
        this.userAppService.deactivateAccount(id);
        return ResponseHandler.generateResponse("Success deactivate account", HttpStatus.OK, null);
    }
}