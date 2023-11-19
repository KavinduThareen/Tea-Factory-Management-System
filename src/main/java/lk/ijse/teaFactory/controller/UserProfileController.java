package lk.ijse.teaFactory.controller;

import com.sun.javafx.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.RegisterDto;
import lk.ijse.teaFactory.dto.tm.CustomerTm;
import lk.ijse.teaFactory.dto.tm.RegisterTm;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.LoginModel;
import lk.ijse.teaFactory.model.RegisterModel;

import javax.security.auth.spi.LoginModule;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserProfileController {

    @FXML
    private TextField cantcTxt;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colIntime;

    @FXML
    private TableColumn<?, ?> colOutTime;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField pwTxt;


    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tbl;

    @FXML
    private TextField uNameTxt;


    private void generateId() {
        try {
            String Id = LoginModel.generateId();
            String pw = LoginModel.password();
            String username = RegisterModel.username();
            idTxt.setText(Id);
            pwTxt.setText(pw);
            uNameTxt.setText(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void initialize() {
        generateId();
    }


     @FXML
    void UpdateOnAction(ActionEvent event) {

    }


}
