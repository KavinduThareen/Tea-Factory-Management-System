package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.*;
import lk.ijse.teaFactory.dto.tm.EmployeeTm;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
    private JFXComboBox<String> uidTxt;

    @FXML
    private TextField empGenderTxt;

    @FXML
    private DatePicker empbdTxt;

    @FXML
    private AnchorPane employeeroot;

    @FXML
    private TableView<?> tabl;

    @FXML
    private TableColumn<?, ?> colSelectEmployeNmaes;

    @FXML
    private TableView<EmployeeTm> tbl2;

    ErrorAnimation errora = new ErrorAnimation();
    NotificationAnimation notifi = new NotificationAnimation();

    @FXML
    void updateOnAction(ActionEvent event) {
        String uId =  uidTxt.getValue();
        String employeeId = employeeIdTxt.getText();
        String empGender = empGenderTxt.getText();
        Date empbd= Date.valueOf(empbdTxt.getValue());
        String employeeName = empNameTxt.getText();
        String empAddress = empAddressTxt.getText();
        String empContac = empContacTxt.getText();

        var dto = new EmployeeDto(uId,employeeId,empGender,empbd,employeeName,empAddress,empContac);
        var model = new EmployeeModel();

        try {
            boolean isUpdated = model.update(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
              notifi.showNotification("update");
                clearFields();
                tabl.refresh();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void saveEmployeeOnAction(ActionEvent event) {

        String uId =  uidTxt.getValue();
        String employeeId = employeeIdTxt.getText();
        String empGender = empGenderTxt.getText();
        Date empbd= Date.valueOf(empbdTxt.getValue());
        String employeeName = empNameTxt.getText();
        String empAddress = empAddressTxt.getText();
        String empContac = empContacTxt.getText();

        var dto = new EmployeeDto(uId,employeeId,empGender,empbd,employeeName,empAddress,empContac);
         var model = new EmployeeModel();
        boolean isValidated = validate();

        if (isValidated) {
         try {
             boolean isSaved = model.employeeSave(dto);

                 if (isSaved) {
                     notifi.showNotification("update");
                     clearFields();
                 }

         } catch (SQLException e) {
             new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
         }

        }

    }

    private void loadUserId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RegisterDto> empList = RegisterModel.loadAllItems();

            for (RegisterDto regDto : empList) {
                obList.add(regDto.getUserid());
            }

            uidTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextEmpId() {
        try {
            String orderId = EmployeeModel.generateNextOrderId();
            employeeIdTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean validate() {

        String employeeIdTxtText = employeeIdTxt.getText();
        boolean isNameValidated = Pattern.matches("[E][0-9]{3,}", employeeIdTxtText);
        if (!isNameValidated) {
            errora.animateError(employeeIdTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid employee id").show();
            return false;
        }

        String empname = empNameTxt.getText();
        boolean isnameValidated = Pattern.matches("[A-Za-z]{3,}", empname);
        if (!isnameValidated) {
            errora.animateError(empNameTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid name!").show();
            return false;
        }

        String genderText = empGenderTxt.getText();
        boolean isGenderValidated = Pattern.matches("(Male)|(Female)", genderText);
        if (!isGenderValidated) {
            errora.animateError(empGenderTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid gender").show();
            return false;
        }

        String cantacText = empContacTxt.getText();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            errora.animateError(empContacTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid contac").show();
            return false;
        }

        String addressText = empAddressTxt.getText();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            errora.animateError(empAddressTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid addrees").show();
            return false;
        }

        return true;
    }

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
        loadUserId();
        generateNextEmpId();
    }


    public void loadAllEmployees(){
        var model =new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList =model.getAllEmployee();
            for (EmployeeDto dto : dtoList){


                obList.add(
                        new EmployeeTm(
                              dto.getUId(),
                                dto.getEmployeeId(),
                                dto.getEmpGender(),
                                dto.getEmpbd(),
                                dto.getEmployeeName(),
                                dto.getEmpAddress(),
                                dto.getEmpContac()

                        )
                );

            }
            tbl2.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colSelectEmployeNmaes.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
    }

    @FXML
    void viewempOnAction(ActionEvent event) throws IOException {

        employeeroot.getChildren().clear();
        employeeroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/view_employee_page.fxml"))));
    }

    void clearFields() {

        employeeIdTxt.setText("");
        empNameTxt.setText("");
        empGenderTxt.setText("");
        empContacTxt.setText("");
        empAddressTxt.setText("");

    }

    @FXML
    void SearchOnAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String id = employeeIdTxt.getText();

            var model = new EmployeeModel();
            try {
                EmployeeDto employeeDto = model.searchCustomer(id);
                if (employeeDto != null) {
                    uidTxt.setValue(employeeDto.getUId());
                    employeeIdTxt.setText(employeeDto.getEmployeeId());
                    empGenderTxt.setText(employeeDto.getEmpGender());
                    empbdTxt.setValue(employeeDto.getEmpbd().toLocalDate());
                    empNameTxt.setText(employeeDto.getEmployeeName());
                    empAddressTxt.setText(employeeDto.getEmpAddress());
                    empContacTxt.setText(employeeDto.getEmpContac());
                } else {
                    notifi.showNotification("Employee not found");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {

        String id = employeeIdTxt.getText();

        var model = new EmployeeModel();
        try {
            EmployeeDto employeeDto = model.searchCustomer(id);

            if (employeeDto != null) {
                uidTxt.setValue(employeeDto.getUId());
                employeeIdTxt.setText(employeeDto.getEmployeeId());
                empGenderTxt.setText(employeeDto.getEmpGender());
                empbdTxt.setValue(employeeDto.getEmpbd().toLocalDate());
                empNameTxt.setText(employeeDto.getEmployeeName());
                empAddressTxt.setText(employeeDto.getEmpAddress());
                empContacTxt.setText(employeeDto.getEmpContac());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }


}


