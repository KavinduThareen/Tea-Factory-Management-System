package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.loginDto;
import lk.ijse.teaFactory.dto.registerDto;
import lk.ijse.teaFactory.model.loginModel;
import lk.ijse.teaFactory.model.registerModel;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginPageController{

    @FXML
    private JFXButton loginbtn;

    @FXML
    private AnchorPane loginroot;

    @FXML
    private TextField passwordTxt;

    @FXML
    private JFXButton registerbtn;

    @FXML
    private TextField usernameTxt;


    @FXML
    void loginbtnOnAction(ActionEvent event) {

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        var model = new loginModel();
        try {
            loginDto dto = model.finduserName();

            if( dto.getUsername().equals(username) && dto.getPassword().equals(password)) {

               // new Alert(Alert.AlertType.INFORMATION, "ok!").show();
                loginroot.getChildren().clear();
                loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/dashboard.fxml"))));


            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void registerbtnOnAction(ActionEvent event) throws IOException {
        loginroot.getChildren().clear();
        loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/register_page.fxml"))));

    }

}
