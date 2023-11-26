package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.OtpDto;

import java.io.IOException;
import java.util.Objects;

public class OtpPageController {

    @FXML
    private TextField otpTxt;

    @FXML
    private AnchorPane root;

 //   private OtpDto otpDto = new OtpDto();

   // private FogetpwController fogetpwController=new FogetpwController();


    private int otp;

/*
    @FXML
    void initialize() {
        // Assuming you want to get the initial value from OtpDto
      // otp = otpDto.getOtp();


    }

 */

    public void save(final OtpDto dto) {
        otp = dto.getOtp();
    }

    @FXML
    void okOnAction(ActionEvent event) {
        int enteredOtp;
        try {
            enteredOtp = Integer.parseInt(otpTxt.getText());
            isCorrect(enteredOtp);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number").show();
        }
    }

    public void isCorrect(int enteredOtp) {
        if (otp == enteredOtp) {
            System.out.println("ok");
            System.out.println(otp);
        } else {
            System.out.println("wrong");
        }
    }


    @FXML
    void tOnAction(ActionEvent event) throws IOException {

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/verifiedPage.fxml"))));


    }

}
