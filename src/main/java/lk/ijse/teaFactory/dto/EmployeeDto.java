package lk.ijse.teaFactory.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class EmployeeDto {
    private String uId;
    private String employeeId;
    private String empGender;
    private String empbd;
    private String employeeName;
    private String empAddress;
    private String empContac;
    private String isCompleted;
}
