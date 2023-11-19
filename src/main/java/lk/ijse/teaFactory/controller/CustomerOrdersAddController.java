package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.PacketStokeModel;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CustomerOrdersAddController {

    @FXML
    private TextField WeigthTxt;

    @FXML
    private JFXComboBox<String > cIdTxt;



    @FXML
    private TextField dateTxt;



    @FXML
    private TextField descreptionTxt;

    @FXML
    private TextField idTxt;


    @FXML
    private AnchorPane root;

    @FXML
    private TextField paymentTxt;

    @FXML
    private JFXComboBox<String > catagaryTxt;


    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String cId = (String) cIdTxt.getValue();
        String catagary = (String) catagaryTxt.getValue();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
       // Double payment = Double.valueOf(paymentTxt.getText());
        Double payment = Double.valueOf(paymentTxt.getText()) * Double.valueOf(WeigthTxt.getText());

        String complete = "0";


        var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,payment,complete);
        var model = new CusOrderModel();

      // boolean isValidated = validate();

     //   if (isValidated) {
       //     new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();

            try {
                boolean isSaved = model.cusOrdersSaved(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "saved").show();
                    clearFields();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



   // }
/*
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

 */

    private void loadCatagary() {
       ObservableList<String> obList = FXCollections.observableArrayList();
       try {
           List<PacketStokeDto> empList = PacketStokeModel.loadAllcatagary();

           for (PacketStokeDto cusODto : empList) {
               obList.add(cusODto.getCatagory());
           }

           catagaryTxt.setItems(obList);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

    private void loadCusOrdersId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> empList = CustomerModel.loadAllItems();

            for (CustomerDto cusODto : empList) {
                obList.add(cusODto.getCusid());
            }

            cIdTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextCusOrderId() {
        try {
            String orderId = CusOrderModel.generateNextOrderId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        loadCusOrdersId();
      generateNextCusOrderId();
      loadCatagary();

    }



    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customerOrders.fxml"))));


    }

String a=null;
    @FXML
    void updateOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String cId = (String) cIdTxt.getValue();
        String catagary = (String) catagaryTxt.getValue();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        Double payment = Double.valueOf(paymentTxt.getText());
        String complete = "0";

        a= weigth + 2;
        System.out.println(a);

        var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,payment,complete);
        var model = new CusOrderModel();

        try {
            boolean isUpdated = model.update(dto);
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

        idTxt.setText("");
    //  catagaryTxt.setText("");
      WeigthTxt.setText("");
      dateTxt.setText("");
      descreptionTxt.setText("");
      paymentTxt.setText("");

    }




}
