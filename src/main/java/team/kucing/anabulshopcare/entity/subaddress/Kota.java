package team.kucing.anabulshopcare.entity.subaddress;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Kota {

    @Id
    @Column(length = 10, name = "id")
    private String id;

    @Column(length = 32, name = "nama")
    private String nama;
}