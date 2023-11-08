package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.loginDto;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.loginModel;
import lk.ijse.teaFactory.model.registerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EmployeePageController {

    @FXML
    private TextField empAddressTxt;

    @FXML
    private TextField empContacTxt;

    @FXML
    private TextField empNameTxt;

    @FXML
    private TextField employeeIdTxt;

    @FXML
    private TextField uidTxt;


    @FXML
    private TextField empGenderTxt;

    @FXML
    private TextField empbdTxt;

    @FXML
    private AnchorPane employeeroot;

    @FXML
    void E_customerOnAction(ActionEvent event) {

    }

    @FXML
    void E_dashboardOnAction(ActionEvent event) {

    }

    @FXML
    void E_empBtnOnAction(ActionEvent event) {

    }

    @FXML
    void E_ourstokeOnAction(ActionEvent event) {

    }

    @FXML
    void E_paymentBtnOnAction(ActionEvent event) {

    }

    @FXML
    void E_supplierBtnOnAction(ActionEvent event) {

    }

    @FXML
    void addNewEmpOnAction(ActionEvent event) {

    }

    @FXML
    void saveEmployeeOnAction(ActionEvent event) {

         String employeeId = employeeIdTxt.getText();
         String employeeName = empNameTxt.getText();
         String empGender = empGenderTxt.getText();
         String empbd= empbdTxt.getText();
         String uId = uidTxt.getText();
         String empContac = empContacTxt.getText();
         String empAddress = empAddressTxt.getText();

         var dto = new EmployeeDto(employeeId,employeeName,empGender,empbd,uId,empContac,empAddress);
         var model = new EmployeeModel();


         try {
             boolean isSaved = model.employeeSave(dto);


                 if (isSaved) {

                     new Alert(Alert.AlertType.CONFIRMATION, "Emplooyee Saved").show();
                 }

         } catch (SQLException e) {
             new Alert(Alert.AlertType.CONFIRMATION, "Error").show();
         }

    }

    @FXML
    void viewempOnAction(ActionEvent event) throws IOException {

        employeeroot.getChildren().clear();
        employeeroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/view_employee_page.fxml"))));


    }
}



