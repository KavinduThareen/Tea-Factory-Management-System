package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaveStokeTm {


    private String id;
    private String weigth;
    private String SDate;
    private String EDate;
    private JFXButton btnDelete;


    {
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);
    }

    public LeaveStokeTm(String id,String weigth,String SDate,String EDate){
        this.id = id;
        this.weigth=weigth;
        this.SDate =SDate;
        this.EDate= EDate;
    }
}
