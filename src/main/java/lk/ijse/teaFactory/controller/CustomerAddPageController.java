package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;

public class CustomerAddPageController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField cusAddressTxt;

    @FXML
    private TextField cuscontacTxt;

    @FXML
    private TextField cusidTxt;

    @FXML
    private TextField cusnameTxt;

    @FXML
    private TextField empidTxt;

    @FXML
    void addNewCusOnAction(ActionEvent event) {

    }

    @FXML
    void addcusBackeBtn(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_page.fxml"))));

    }

    @FXML
    void cusSaveOnAction(ActionEvent event) {

        String cusid = cusidTxt.getText();
        String empid = empidTxt.getText();
        String cusname = cusnameTxt.getText();
        String cusAddress = cusAddressTxt.getText();
        String cusCantac = cuscontacTxt.getText();
        String complete = "0";

        var dto = new CustomerDto(cusid,empid,cusname,cusAddress,cusCantac,complete);

        var model = new CustomerModel();
        boolean isValidated = validate();

        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();

            try {
                boolean isSaved = model.customerSaved(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "saved").show();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean validate() {

        String idText = cusidTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[E][0-9]{3,}", idText);
        if (!isIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String UidText = empidTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isUIDValidated = Pattern.matches("[U][0-9]{3,}", UidText);
        if (!isUIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }


        String nameText = cusnameTxt.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }

        String addressText = cusAddressTxt.getText();
//        boolean isAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

        String cantacText = cuscontacTxt.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer contac").show();
            return false;
        }

        return true;
    }




    @FXML
    void updateOnAction(ActionEvent event) {

        String cusid = cusidTxt.getText();
        String empid = empidTxt.getText();
        String cusname = cusnameTxt.getText();
        String cusAddress = cusAddressTxt.getText();
        String cusCantac = cuscontacTxt.getText();
        String complete = "0";

        var dto = new CustomerDto(cusid,empid,cusname,cusAddress,cusCantac,complete);

        var model = new CustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    void clearFields() {
        cusidTxt.setText("");
        empidTxt.setText("");
        cusnameTxt.setText("");
        cusAddressTxt.setText("");
        cuscontacTxt.setText("");
    }





}
