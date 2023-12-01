package lk.ijse.teaFactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class LeavesStokeDto {
    private String id;
    private String weigth;
    private java.sql.Date sDate;
    private java.sql.Date eDate;

}
