package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lk.ijse.teaFactory.model.CusOrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;

public class CustomerOrdersAddController {

    @FXML
    private TextField WeigthTxt;

    @FXML
    private TextField cIdTxt;

    @FXML
    private TextField catagaryTxt;

    @FXML
    private TextField dateTxt;



    @FXML
    private TextField descreptionTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private AnchorPane root;



    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String cId = cIdTxt.getText();
        String catagary = catagaryTxt.getText();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        String complete = "0";

        var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,complete);
        var model = new CusOrderModel();
        boolean isValidated = validate();

        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();


            try {
                boolean isSaved = model.cusOrdersSaved(dto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean validate() {

        String idText = idTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[E][0-9]{3,}", idText);
        if (!isIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String UidText = cIdTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isUIDValidated = Pattern.matches("[U][0-9]{3,}", UidText);
        if (!isUIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }


        String nameText = catagaryTxt.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }

        String addressText = WeigthTxt.getText();
//        boolean isAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

        String cantacText = dateTxt.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer contac").show();
            return false;
        }

        String descreptionText = dateTxt.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isDiscreptionValidated = Pattern.matches("[0-9]{10}", descreptionText);
        if (!isDiscreptionValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer contac").show();
            return false;
        }

        return true;
    }



    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customerOrders.fxml"))));


    }
/*
    void csetFields(CusOrderTm dto) {
        idTxt.setText(dto.getId());
        cIdTxt.setText(dto.getCId());
        catagaryTxt.setText(String.valueOf(dto.getCatagary()));
        WeigthTxt.setText(String.valueOf(dto.getWeigth()));
        dateTxt.setText(String.valueOf(dto.getDate()));
        descreptionTxt.setText(String.valueOf(dto.getDescreption()));
    }

 */

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String cId = cIdTxt.getText();
        String catagary = catagaryTxt.getText();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        String complete = "0";

        var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,complete);
        var model = new CusOrderModel();

        try {
            boolean isUpdated = model.update(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


}
