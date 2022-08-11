package team.kucing.anabulshopcare.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String bankName;

    private String bankAccount;

    private String accountName;
}