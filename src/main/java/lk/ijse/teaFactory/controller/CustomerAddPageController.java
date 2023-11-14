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

        try {
            boolean isSaved = model.customerSaved(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


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
