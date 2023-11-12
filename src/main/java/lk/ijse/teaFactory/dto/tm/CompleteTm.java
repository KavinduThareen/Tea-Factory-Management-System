package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.IdentityHashMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompleteTm {
    private String Id;
    private String catogary;
    private String weigth;
    private String date;
    private JFXButton btnDelete;

    {
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);
    }

    public CompleteTm(String Id, String catogary,String weigth,String date) {
        this.Id = Id;
        this.catogary = catogary;
        this.weigth= weigth;
        this.date = date;

    }



}
