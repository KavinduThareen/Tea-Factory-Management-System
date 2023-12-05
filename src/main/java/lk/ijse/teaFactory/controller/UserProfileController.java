package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.ErrorAnimation;
import lk.ijse.teaFactory.dto.NotificationAnimation;
import lk.ijse.teaFactory.dto.RegisterDto;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;

public class UserProfileController{

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
    private Label lblname;

    @FXML
    private TextField usernameTxt;

    private ErrorAnimation errorAnimation = new ErrorAnimation();
    NotificationAnimation notifi = new NotificationAnimation();

    @FXML
    void addnewaccOnAction(ActionEvent event) throws IOException, SQLException {

    }




    @FXML
    void deleteaccOnAction(ActionEvent event) {
        if (idTxt == null) {
            new Alert(Alert.AlertType.CONFIRMATION, " enter user id").show();
        }
        else {
            String id = idTxt.getText();
            deleteItem(id);
        }
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = RegisterModel.delete(id);
            if(isDeleted)
              notifi.showNotification("Delete");
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String name = usernameTxt.getText();
        String contac = contacTxt.getText();
        String password = pwTxt.getText();
        String comfimepw = cpwTxt.getText();

        var dto = new RegisterDto(id, name, contac, password);
        boolean isValidated = validate();

        if (isValidated) {
            if (password.equals(comfimepw)) {
                var model = new RegisterModel();
                try {
                    boolean isUpdated = model.updateuser(dto);
                    System.out.println(isUpdated);
                    if (isUpdated) {
                        notifi.showNotification("Update");
                        //clearFields();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Wrong password").show();
            }
        }
    }

    private boolean validate() {

        String nameText = usernameTxt.getText();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid username").show();
            errorAnimation.animateError(usernameTxt);
            return false;
        }
        String cantacText = contacTxt.getText();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid contac").show();
            errorAnimation.animateError(contacTxt);
            return false;
        }

        String passwordText = pwTxt.getText();
        boolean isPwValidated = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", passwordText);
        if (!isPwValidated) {
            errorAnimation.animateError(pwTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid pw ").show();
            return false;
        }
        return true;
    }

}
