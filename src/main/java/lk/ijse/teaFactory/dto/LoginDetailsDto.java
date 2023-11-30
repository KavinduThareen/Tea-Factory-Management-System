package lk.ijse.teaFactory.dto;

import lombok.*;

import java.sql.Date;

@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Data
public class LoginDetailsDto {
    private String id;
    private String inTime;
    private Date date;
}
