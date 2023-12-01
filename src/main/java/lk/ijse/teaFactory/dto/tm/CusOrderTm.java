package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter

public class CusOrderTm {
    private String id;
    private String cId;
    private String catagary;
    private String weigth;
    private String date;
    private String descreption;
    private double payment;
    private JFXButton btnDelete;

}
