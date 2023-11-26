package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class VerifiedPageController {

    @FXML
    private TextField confimepwTxt;

    @FXML
    private TextField newpwTxt;

    @FXML
    private TextField uidTxt;


    @FXML
    void pwSaveOnAction(ActionEvent event) {
        String id = uidTxt.getText();
        String pw = newpwTxt.getText();

        try {
            boolean isLogin = RegisterModel.updatepw(id, pw);
            if (isLogin) {
             //   loginroot.getChildren().clear();
              //  loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml"))));

                System.out.println("ok bro");

                return;
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Username Or Passowrd").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }


}
