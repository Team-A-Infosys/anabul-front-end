package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.PaymentResponse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE payment SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Payment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String bankName;

    private String bankAccount;

    private String accountName;

    private boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public PaymentResponse convertToResponse(){
        return PaymentResponse.builder()
                .bankName(this.bankName)
                .bankAccount(this.bankAccount)
                .accountName(this.accountName).build();
    }
}