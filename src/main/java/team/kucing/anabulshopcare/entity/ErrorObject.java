package team.kucing.anabulshopcare.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErrorObject {

    private List<String> errorMessage;

    private Integer statusCode;

    private List<String> payload;

    private Date timestamp;
}