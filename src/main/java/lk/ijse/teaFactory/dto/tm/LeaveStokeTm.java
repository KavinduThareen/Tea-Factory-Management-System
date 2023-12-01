package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaveStokeTm {
    private String id;
    private String weigth;
    private java.sql.Date SDate;
    private java.sql.Date EDate;
    private JFXButton btnDelete;
  
}
