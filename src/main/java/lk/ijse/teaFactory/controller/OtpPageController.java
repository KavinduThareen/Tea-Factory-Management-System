package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.model.OtpModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class OtpPageController implements Initializable {

    @FXML
    private TextField otpTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField otpField1Txt;

    @FXML
    private TextField otpField2Txt;

    @FXML
    private TextField otpField3Txt;

    @FXML
    private TextField otpField4Txt;

    FogetpwController fogetpwController = new FogetpwController();

    /*
    public void save(final OtpDto dto) {
        this.otp = dto.getOtp();
        System.out.println("Saved OTP value: " + otp);
    }

     */
    int otp=0;

    @FXML
    void textOnAction(ActionEvent event) throws SQLException, IOException {


       // int otp=0;

        var model = new OtpModel();
        otp = model.load();


        boolean a = verifyOto(otp);
        if (a){
            delete(otp);
            root.getChildren().clear();
            root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/verifiedPage.fxml"))));
        }
        else {
            new Alert(Alert.AlertType.CONFIRMATION, "Otp is wrong").show();
        }

        /*
        try {

            int enteredOtp = Integer.parseInt(otpTxt.getText());
            checkEnteredOtp(enteredOtp);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for OTP").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */

    }




    @FXML
    void okOnAction(ActionEvent event) throws SQLException, IOException {




       /*

        try {

            int enteredOtp = Integer.parseInt(otpTxt.getText());
            checkEnteredOtp(enteredOtp);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for OTP").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        */


    }

    public void checkEnteredOtp(int enteredOtp) throws SQLException, IOException {

        /*

        int otp=0;

        var model = new OtpModel();
         otp = model.load();
        System.out.println("pakaya" + otp);

        if (otp == enteredOtp) {
            verifyOto(otp);
            root.getChildren().clear();
            root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/verifiedPage.fxml"))));
            delete(otp);
            System.out.println("ok");
            System.out.println("Entered OTP: " + enteredOtp);
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid OTP").show();
            System.out.println("wrong");
        }

         */

    }

    private void delete(int otp) {
        try {
            boolean isDeleted = OtpModel.delete(otp);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    @FXML
    void tOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void resentOtpOnAction(ActionEvent event) throws SQLException {

        delete(otp);
        fogetpwController.generateNewOtp();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        otpField1Txt.requestFocus();
        otpField1Txt.addEventFilter(KeyEvent.KEY_TYPED, numericOnlyFilter);
        otpField2Txt.addEventFilter(KeyEvent.KEY_TYPED, numericOnlyFilter);
        otpField3Txt.addEventFilter(KeyEvent.KEY_TYPED, numericOnlyFilter);
        otpField4Txt.addEventFilter(KeyEvent.KEY_TYPED, numericOnlyFilter);

        otpField1Txt.setOnKeyReleased(event -> {
            String input = otpField1Txt.getText().trim();
            if (input.length() == 1) {
                otpField2Txt.requestFocus();
            } else if (input.length() == 0 && event.getCode() == KeyCode.BACK_SPACE) {
                otpField1Txt.clear();
                otpField1Txt.requestFocus();
            }
        });

        otpField2Txt.setOnKeyReleased(event -> {
            String input = otpField2Txt.getText().trim();
            if (input.length() == 1) {
                otpField3Txt.requestFocus();
            } else if (input.length() == 0 && event.getCode() == KeyCode.BACK_SPACE) {
                otpField1Txt.requestFocus();
            }
        });

        otpField3Txt.setOnKeyReleased(event -> {
            String input = otpField3Txt.getText().trim();
            if (input.length() == 1) {
                otpField4Txt.requestFocus();
            } else if (input.length() == 0 && event.getCode() == KeyCode.BACK_SPACE) {
                otpField2Txt.requestFocus();
            }
        });

        otpField4Txt.setOnKeyReleased(event -> {
            String input = otpField4Txt.getText().trim();
            if (input.length() == 0 && event.getCode() == KeyCode.BACK_SPACE) {
                otpField3Txt.requestFocus();
            }
        });
    }

    private final EventHandler<KeyEvent> numericOnlyFilter = event -> {
        char inputChar = event.getCharacter().charAt(0);

        if (!Character.isDigit(inputChar) && inputChar != '\b') {
            event.consume();
        }
    };


    public boolean verifyOto(int otp){
        int num1 = Integer.parseInt(otpField1Txt.getText());
        int num2 = Integer.parseInt(otpField2Txt.getText());
        int num3 = Integer.parseInt(otpField3Txt.getText());
        int num4 = Integer.parseInt(otpField4Txt.getText());
        int total = num1 * 1000 + num2 * 100 + num3 * 10 + num4;

        return total==otp;
    }
}
