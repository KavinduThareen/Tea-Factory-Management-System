package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CusOrderTm {
    private String id;
    private String cId;
    private String catagary;
    private String weigth;
    private String date;
    private String descreption;
    private JFXButton btnDelete;

    {
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);
    }


    public CusOrderTm(String id, String cId, String catagary, String weigth, String date, String descreption) {

        this.id = id;
        this.cId = cId;
        this.catagary = catagary;
        this.weigth = weigth;
        this.date = date;
        this.descreption = descreption;
    }
}
