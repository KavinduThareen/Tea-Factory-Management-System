package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.RegisterDto;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class RegisterPagecontroller {

    @FXML
    private TextField confirmPasswordTxt;

    @FXML
    private TextField contacTxt;

    @FXML
    private JFXButton registerBackBtn;

    @FXML
    private JFXButton createAccountBtn;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField useridTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private AnchorPane registerroot;



    @FXML
    void createAccountBtnOnAction(ActionEvent event) {

        String userid = useridTxt.getText();
        String username = usernameTxt.getText();
        String contac = contacTxt.getText();
        String password = passwordTxt.getText();


            var dto = new RegisterDto(userid, username, contac, password);

            var model = new RegisterModel();

            try {
                boolean isSaved = model.registerUser(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "You are registerd!").show();
                    clearFields();
                    registerroot.getChildren().clear();
                    registerroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/login_page.fxml"))));

                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    void clearFields() {
        usernameTxt.setText("");
        useridTxt.setText("");
        contacTxt.setText("");
        passwordTxt.setText("");
        confirmPasswordTxt.setText("");
    }

    @FXML
    void registerBackBtnOnAction(ActionEvent event) throws IOException {
        registerroot.getChildren().clear();
        registerroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/login_page.fxml"))));


    }

}
