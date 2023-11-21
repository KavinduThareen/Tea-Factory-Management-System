package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Cursor;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class SalaryTm {
    private String id;
    private String empId;
    private String date;
    private String count;
    private JFXButton btnDelete;


}
