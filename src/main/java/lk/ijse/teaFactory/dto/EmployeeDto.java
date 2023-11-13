package lk.ijse.teaFactory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeDto {
    private String employeeId;
    private String employeeName;
    private String empGender;
    private String empbd;
    private String uId;
    private String empContac;
    private String empAddress;
    private String isCompleted;
}
