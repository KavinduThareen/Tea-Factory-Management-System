package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter

public class EmployeeTm {
   private String uId;
   private String employeeId;
   private String empGender;
   private String empbd;
   private String employeeName;
   private String empAddress;
   private String empContac;
   private JFXButton btnDelete;



   public EmployeeTm(String uId, String employeeId, String empGender, String empbd, String employeeName, String empAddress, String empContac) {
      this.uId = uId;
      this.employeeId = employeeId;
      this.empGender = empGender;
      this.empbd = empbd;
      this.employeeName = employeeName;
      this.empAddress = empAddress;
      this.empContac = empContac;
   }
}
