package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CompleteTm {
    private String Id;
    private String catogary;
    private String weigth;
    private java.sql.Date date;
    private JFXButton btnDelete;



}
