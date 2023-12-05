package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.ErrorAnimation;
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
import java.util.regex.Pattern;

public class LoginPageController{

    @FXML
    private JFXButton loginbtn;

    @FXML
    private AnchorPane loginroot;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private JFXButton registerbtn;

    @FXML
    private TextField usernameTxt;
     private RegisterModel registerModel = new RegisterModel();
     private ErrorAnimation errorAnimation = new ErrorAnimation();

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

        boolean isValidated = validate();

        if (isValidated) {
            try {
                boolean isLogin = RegisterModel.searchUser(username, password);
                if (isLogin) {
                    loginroot.getChildren().clear();
                    loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard.fxml"))));

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                    String inTime = ((LocalDateTime.now().format(formatter)));
                    Date date = Date.valueOf((LocalDate.now().toString()));
                    String uid = registerModel.findUserIdByUsername(username);

                    loginDetail(inTime, date, uid);

                } else {
                   new Alert(Alert.AlertType.WARNING, "Invalid Username Or Passowrd").show();
                }
            } catch (SQLException | IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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

    private boolean validate() {

        String nameText = usernameTxt.getText();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            errorAnimation.animateError(usernameTxt);
            return false;
        }

        String passwordText = passwordTxt.getText();
        boolean isPwValidated = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", passwordText);
        if (!isPwValidated) {
            return false;
        }
        return true;
    }

    @FXML
    void registerbtnOnAction(ActionEvent event) throws IOException {
      register();
    }

    public void register() throws IOException {
        loginroot.getChildren().clear();
        loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/register_page.fxml"))));

    }

    @FXML
    void fogetpwOnAction(ActionEvent event) throws IOException {

            loginroot.getChildren().clear();
            loginroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/fogetpw.fxml"))));

    }

}
