package lk.ijse.teaFactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class LeavesStokeDto {
    private String id;
    private String weigth;
    private String  sDate;
    private String eDate;
    private String isCompleted;
}
