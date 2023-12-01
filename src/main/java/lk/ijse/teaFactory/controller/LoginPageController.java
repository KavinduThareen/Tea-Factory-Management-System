package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.LoginDetailsDto;
import lk.ijse.teaFactory.model.LoginDetailModel;
import lk.ijse.teaFactory.model.RegisterModel;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     private RegisterModel registerModel = new RegisterModel();





    @FXML
    void keyOnAction(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {

            loginroot.getChildren().clear();
            loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml"))));

        }
    }

    @FXML
    void loginbtnOnAction(ActionEvent event) throws IOException, SQLException {


        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        try {
            boolean isLogin = RegisterModel.searchUser(username, password);
            if (isLogin) {
                loginroot.getChildren().clear();
                loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml"))));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String  inTime = ((LocalDateTime.now().format(formatter)));
               // System.out.println(inTime);

                Date date = Date.valueOf((LocalDate.now().toString()));
               // System.out.println(date);

                String uid = registerModel.findUserIdByUsername(username);



                loginDetail(inTime , date ,uid);






                return;
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Username Or Passowrd").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void loginDetail(String inTime, Date date, String uid) throws SQLException {

        LoginDetailModel model = new LoginDetailModel();
        String userid = uid;
        String intime = inTime;
        Date loginDate = date;

        var dto = new LoginDetailsDto(userid,intime,loginDate);
        boolean isSaved = model.logdetail(dto);

        if (isSaved){
            System.out.println("saved");
        }



    }





/*
    public void login(){
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        var model = new LoginModel();
        try {
            LoginDto dto = model.finduserName();

            if (dto != null && dto.getUsername().equals(username) && dto.getPassword().equals(password)) {
                // new Alert(Alert.AlertType.INFORMATION, "ok!").show();
                loginroot.getChildren().clear();
                loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml"))));
            } else {
                new Alert(Alert.AlertType.INFORMATION, "User not found or invalid credentials!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

 */


    @FXML
    void registerbtnOnAction(ActionEvent event) throws IOException {
        loginroot.getChildren().clear();
        loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/register_page.fxml"))));

    }

    @FXML
    void fogetpwOnAction(ActionEvent event) throws IOException {

            loginroot.getChildren().clear();
            loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/fogetpw.fxml"))));


    }

    // delete login detail replase otp table


}
