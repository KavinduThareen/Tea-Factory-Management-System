package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.OtpDto;
import lk.ijse.teaFactory.gmail.Gmailer;
import lk.ijse.teaFactory.model.LoginDetailModel;
import lk.ijse.teaFactory.model.OtpModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class FogetpwController {

    @FXML
    private TextField emailTxt;

    @FXML
    private AnchorPane root;
    private String email;
    public int otp;

    private int otp2;

    @FXML
    void emailOnAction(ActionEvent event) throws Exception {
        email = emailTxt.getText();
        otp = generateNewOtp();
        sendOtp();

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/otpPage.fxml"))));

    }

    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/login_page.fxml"))));
    }

    public void sendOtp() {
        boolean b1 = false;
        if (email.contains("@")){
            int index = email.indexOf("@");
            if (!email.substring(index + 1).equals("gmail.com")){
                return;
            }
        } else {
            return;
        }
        try {
            b1 = Gmailer.setEmailCom(email, otp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int generateNewOtp() throws SQLException {
        do {
            Random random = new Random();
            var model = new OtpModel();
            otp2 = random.nextInt(9999);

            if (otp2 > 1000){
               var otpDto = new OtpDto(otp2);
               boolean a =  model.save(otpDto);
              //  System.out.println(otp2);
                return otp2;
            }
        }while (true);
    }



}
