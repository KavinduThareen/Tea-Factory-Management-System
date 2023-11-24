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
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();
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
            else {
              //  new Alert(Alert.AlertType.CONFIRMATION, "Wrong password").show();
                animateError(confirmPasswordTxt);
            }
        }
    }

    public static void animateError(TextField textField) {
        // Get the original border or use Border.EMPTY as a fallback
        Border originalBorder = textField.getBorder() != null ? textField.getBorder() : Border.EMPTY;

        // Create a Timeline for the animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(textField.borderProperty(), originalBorder)),
                new KeyFrame(Duration.millis(500), new KeyValue(textField.borderProperty(), createRedBorder())),
                new KeyFrame(Duration.millis(900), new KeyValue(textField.borderProperty(), originalBorder))
        );

        // Add a translation animation for vibration effect
        double originalTranslateX = textField.getTranslateX();
        double vibrationDistance = 60.0; // Adjust the distance of the vibration
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), new KeyValue(textField.translateXProperty(), originalTranslateX)),
                new KeyFrame(Duration.millis(50), new KeyValue(textField.translateXProperty(), originalTranslateX + vibrationDistance)),
                new KeyFrame(Duration.millis(100), new KeyValue(textField.translateXProperty(), originalTranslateX - vibrationDistance)),
                new KeyFrame(Duration.millis(150), new KeyValue(textField.translateXProperty(), originalTranslateX + vibrationDistance)),
                new KeyFrame(Duration.millis(200), new KeyValue(textField.translateXProperty(), originalTranslateX))
        );

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(210), event -> textField.clear()));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new KeyValue(textField.borderProperty(), createRedBorder())));
        // Play the animation
        timeline.play();
    }

    private static Border createRedBorder() {
        // Create a red border
        return new Border(new BorderStroke(
                Color.RED, BorderStrokeStyle.SOLID,
                javafx.scene.layout.CornerRadii.EMPTY, javafx.scene.layout.BorderWidths.DEFAULT
        ));
    }


    private boolean validate() {

        String idText = useridTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[U][0-9]{3,}", idText);
        if (!isIDValidated) {
            animateError(useridTxt);
           // new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String nameText = usernameTxt.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            animateError(usernameTxt);
          //  new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }

        String cantacText = contacTxt.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            animateError(contacTxt);
         //   new Alert(Alert.AlertType.ERROR, "Invalid customer contac").show();
            return false;
        }

/*
        String addressText = passwordTxt.getText();
//        boolean isAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isAddressValidated = Pattern.matches("[0-5]{1,}", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

 */


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
