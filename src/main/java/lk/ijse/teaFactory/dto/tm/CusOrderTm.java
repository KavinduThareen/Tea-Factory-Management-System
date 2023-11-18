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
    private JFXButton btnDelete;

   /* {
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnDelete.setCursor(Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);


    }*/

/*
    public CusOrderTm(String id, String cId, String catagary, String weigth, String date, String descreption,JFXButton btnDelete) {

        this.id = id;
        this.cId = cId;
        this.catagary = catagary;
        this.weigth = weigth;
        this.date = date;
        this.descreption = descreption;
        this.btnDelete = btnDelete;
    }

 */

}
