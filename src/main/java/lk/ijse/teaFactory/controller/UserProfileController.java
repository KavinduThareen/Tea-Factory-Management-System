package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.RegisterDto;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.RegisterModel;
import lk.ijse.teaFactory.model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class UserProfileController {

    @FXML
    private TextField contacTxt;

    @FXML
    private PasswordField cpwTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private PasswordField pwTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField usernameTxt;

    @FXML
    void addnewaccOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void deleteaccOnAction(ActionEvent event) {
        String id = idTxt.getText();
        deleteItem(id);
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = RegisterModel.delete(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }


    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String name =  usernameTxt.getText();
        String contac = contacTxt.getText();
        String password = pwTxt.getText();
        String comfimepw = cpwTxt.getText();


        var dto = new RegisterDto(id,name,contac,password);

        var model = new RegisterModel();
        try {
            boolean isUpdated = model.updateuser(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                //clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
