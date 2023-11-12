package lk.ijse.teaFactory.dto.tm;


import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {
    private String cusid;
    private String empid;
    private String cusname;
    private String cusAddress;
    private String cusCantac;
    private JFXButton btnDelete;

    {
        btnDelete = new JFXButton("Delete");

        // Set button styles
        btnDelete.setCursor(javafx.scene.Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);
    }

    public CustomerTm(String cusid,String empid,String cusname,String cusAddress,String cusCantac){
        this.cusid=cusid;
        this.empid=empid;
        this.cusname=cusname;
        this.cusAddress=cusAddress;
        this.cusCantac=cusCantac;
    }

}
