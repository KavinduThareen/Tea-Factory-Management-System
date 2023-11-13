package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeTm {
   private String employeeId;
   private String employeeName;
   private String empGender;
   private String empbd;
   private String uId;
   private String empContac;
   private String empAddress;
   private JFXButton btnDelete;

   {
      btnDelete = new JFXButton("Delete");

      // Set button styles
      btnDelete.setCursor(javafx.scene.Cursor.HAND);
      btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

      btnDelete.setPrefWidth(100);
      btnDelete.setPrefHeight(30);
   }

   public EmployeeTm(String employeeId, String employeeName, String empGender, String empbd, String uId, String empContac, String empAddress) {

      this.employeeId = employeeId;
      this.employeeName = employeeName;
      this.empGender = empGender;
      this.empbd = empbd;
      this.uId = uId;
      this.empContac = empContac;
      this.empAddress = empAddress;
   }
}
