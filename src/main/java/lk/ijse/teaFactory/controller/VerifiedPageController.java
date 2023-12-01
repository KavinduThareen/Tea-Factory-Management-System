package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class VerifiedPageController {

    @FXML
    private TextField confimepwTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField newpwTxt;

    @FXML
    private TextField uidTxt;

    @FXML
    void pwSaveOnAction(ActionEvent event) {
        String id = uidTxt.getText();
        String pw = newpwTxt.getText();
        String cfpw = confimepwTxt.getText();

        try {
            if (pw.equals(cfpw)) {
                boolean isLogin = RegisterModel.updatepw(id, pw);
                if (isLogin) {
                    root.getChildren().clear();
                    root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login_page.fxml"))));
                    return;
                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Username Or Passowrd").show();
                }
            }
            else {
                new Alert(Alert.AlertType.WARNING, "Invalid password").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
