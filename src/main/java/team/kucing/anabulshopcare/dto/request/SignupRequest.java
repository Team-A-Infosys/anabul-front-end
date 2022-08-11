package team.kucing.anabulshopcare.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private String confirmPassword;

    private AddressRequest address;
}