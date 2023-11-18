package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.RegisterDto;
import lk.ijse.teaFactory.dto.tm.EmployeeTm;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.RegisterModel;

import java.io.IOException;
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
    private TextField empbdTxt;

    @FXML
    private AnchorPane employeeroot;

    @FXML
    private TableView<?> tabl;

    @FXML
    private TableColumn<?, ?> colSelectEmployeNmaes;

    @FXML
    private TableView<EmployeeTm> tbl2;

    @FXML
    void updateOnAction(ActionEvent event) {
        String employeeId = employeeIdTxt.getText();
        String employeeName = empNameTxt.getText();
        String empGender = empGenderTxt.getText();
        String empbd= empbdTxt.getText();
        String uId = (String) uidTxt.getValue();
        String empContac = empContacTxt.getText();
        String empAddress = empAddressTxt.getText();
        String delete = "0";

        var dto = new EmployeeDto(employeeId,employeeName,empGender,empbd,uId,empContac,empAddress,delete);
        var model = new EmployeeModel();

        try {
            boolean isUpdated = model.update(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
                tabl.refresh();


            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void saveEmployeeOnAction(ActionEvent event) {

         String employeeId = employeeIdTxt.getText();
         String employeeName = empNameTxt.getText();
         String empGender = empGenderTxt.getText();
         String empbd= empbdTxt.getText();
         String uId = (String) uidTxt.getValue();
         String empContac = empContacTxt.getText();
         String empAddress = empAddressTxt.getText();
         String delete = "0";

         var dto = new EmployeeDto(employeeId,employeeName,empGender,empbd,uId,empContac,empAddress,delete);
         var model = new EmployeeModel();
     //    boolean isValidated = validate();

      //  if (isValidated) {
         //   new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();
         try {
             boolean isSaved = model.employeeSave(dto);


                 if (isSaved) {

                     new Alert(Alert.AlertType.CONFIRMATION, "Emplooyee Saved").show();
                     clearFields();
                 }

         } catch (SQLException e) {
             new Alert(Alert.AlertType.CONFIRMATION, "Error").show();
         }

        }

  //  }

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

    /*
    private boolean validate() {

        String idText = employeeIdTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[E][0-9]{3,}", idText);
        if (!isIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String nameText = empNameTxt.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }

        String genderText = empGenderTxt.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isGenderValidated = Pattern.matches("(Male)|(Female)", genderText);
        if (!isGenderValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }

        String UidText = employeeIdTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isUIDValidated = Pattern.matches("[U][0-9]{3,}", UidText);
        if (!isUIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String cantacText = empContacTxt.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer contac").show();
            return false;
        }

        String addressText = empAddressTxt.getText();
//        boolean isAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }


        return true;
    }

     */

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
                                dto.getEmployeeId(),
                                dto.getEmployeeName(),
                                dto.getEmpGender(),
                                dto.getEmpbd(),
                                dto.getUId(),
                                dto.getEmpContac(),
                                dto.getEmpAddress()
                        )
                );

            }
            tbl2.setItems(obList);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colSelectEmployeNmaes.setCellValueFactory(new PropertyValueFactory<>("uId"));
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
        empbdTxt.setText("");
        empContacTxt.setText("");
        empAddressTxt.setText("");

    }
}



