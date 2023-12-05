package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.css.Size;
import javafx.css.SizeUnits;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.teaFactory.dto.ErrorAnimation;
import lk.ijse.teaFactory.dto.NotificationAnimation;
import lk.ijse.teaFactory.dto.RegisterDto;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;
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

    ErrorAnimation errora = new ErrorAnimation();
    NotificationAnimation notifi = new NotificationAnimation();

    @FXML
    void createAccountBtnOnAction(ActionEvent event) {

        String userid = useridTxt.getText();
        String username = usernameTxt.getText();
        String contac = contacTxt.getText();
        String password = passwordTxt.getText();

        String conPw = confirmPasswordTxt.getText();

            var dto = new RegisterDto(userid, username, contac, password);

            var model = new RegisterModel();
        boolean isValidated = validate();

        if (isValidated) {
            if (password.equals(conPw)) {
                try {
                    boolean isSaved = model.registerUser(dto);
                    if (isSaved) {
                        notifi.showNotification("You are registerd!");

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
            else {
                errora.animateError(confirmPasswordTxt);
            }
        }
    }

    private boolean validate() {

        String idText = useridTxt.getText();
        boolean isIDValidated = Pattern.matches("[U][0-9]{3,}", idText);
        if (!isIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID!").show();
            errora.animateError(useridTxt);
            return false;
        }

        String nameText = usernameTxt.getText();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid  name").show();
            errora.animateError(usernameTxt);
            return false;
        }

        String cantacText = contacTxt.getText();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid  contac").show();
            errora.animateError(contacTxt);
            return false;
        }

        String addressText = passwordTxt.getText();
        boolean isAddressValidated = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid password at least 8 characters").show();
            return false;
        }
        return true;
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

    private void generateNextCusOrderId() {
        try {
            String orderId = RegisterModel.generateNextUserId();
            useridTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
       generateNextCusOrderId();
    }

}
