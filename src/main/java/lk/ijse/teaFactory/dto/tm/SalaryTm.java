package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryTm {
    private String id;
    private String empId;
    private String date;
    private String count;
    private JFXButton btnDelete;



    public SalaryTm(String id, String empId, String date, String count) {

        this.id = id;
        this.empId = empId;
        this.date = date;
        this.count = count;
    }
}
