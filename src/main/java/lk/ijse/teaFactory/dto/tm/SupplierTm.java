package lk.ijse.teaFactory.dto.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierTm {
    private String id;
    private String name;
    private String address;
    private String contac;
    private JFXButton btnDelete;

    {
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);
    }

    public SupplierTm(String id, String name, String address, String contac){

        this.id = id;
        this.name = name;
        this.address = address;
        this.contac = contac;
    }

}
