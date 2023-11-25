package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.SupOrderDto;
import lk.ijse.teaFactory.dto.SupplierDto;
import lk.ijse.teaFactory.dto.tm.SupOrderTm;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.LeavesStokeModel;
import lk.ijse.teaFactory.model.SupOrderModel;
import lk.ijse.teaFactory.model.SupplierModel;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierOrdersController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colSid;

    @FXML
    private TableColumn<?, ?> colSoId;

    @FXML
    private TableColumn<?, ?> colWeigth;

    @FXML
    private TableColumn<?, ?> colPayment;


    @FXML
    private TextField dateTxt;

    @FXML
    private TextField paymentTxt;


    @FXML
    private AnchorPane root;

    @FXML
    private JFXComboBox<String > sIdTxt;

    @FXML
    private TextField sOidTxt;

    @FXML
    private TableView<SupOrderTm> tbl;

    @FXML
    private TextField weigthTxt;

    @FXML
    void addOnAction(ActionEvent event) {

       String id = sOidTxt.getText();
       String sId = sIdTxt.getValue();
       String date = dateTxt.getText();
       String weigth = weigthTxt.getText();
       int payment = (int) (Double.valueOf(paymentTxt.getText()) * Double.valueOf(weigthTxt.getText()));
       String isDelete = "0";

       var dto = new SupOrderDto(id,sId,date,weigth,payment,isDelete);

        var model = new SupOrderModel();

        boolean isValidated = validate();

        if (isValidated) {
           // new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();
            try {
                boolean isSaved = model.SupOrderSaved(dto);
             //   boolean isSaved2 = model2.addLeavesStoke2(dto2);
                if (isSaved) {
                    tbl.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "saved").show();
                    clearFields();

                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
                throw new RuntimeException(e);
            }
        }

    }

    private boolean validate() {

        String idText = sOidTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[s][0-9]{3,}", idText);
        if (!isIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String UidText = sIdTxt.getValue();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isUIDValidated = Pattern.matches("[S][0-9]{3,}", UidText);
        if (!isUIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }






        return true;
    }


    public void loadAll(){
        var model = new SupOrderModel();
        ObservableList<SupOrderTm> obList = FXCollections.observableArrayList();

        try {
            List<SupOrderDto> dtoList = model.loadAll();

            for(SupOrderDto dto : dtoList) {

                JFXButton btnDelete = new JFXButton("Deleted");
                btnDelete.setCursor(javafx.scene.Cursor.HAND);
                btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

                btnDelete.setPrefWidth(100);
                btnDelete.setPrefHeight(30);

                //   CusOrderTm tm = new CusOrderTm();

                //   tm.getBtnDelete()
                btnDelete .setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tbl.getSelectionModel().getSelectedIndex();
                        String id = (String) colSoId.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tbl.refresh();
                    }
                });
                obList.add(
                        new SupOrderTm(
                                dto.getId(),
                                dto.getSId(),
                                dto.getDate(),
                                dto.getWeigth(),
                                dto.getPayment(),
                                btnDelete


                        )
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tbl.setItems(obList);
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = SupOrderModel.deleteItem(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {

        colSoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSid.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colWeigth.setCellValueFactory(new PropertyValueFactory<>("weigth"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
       // setListener();
        generateNextId();
        loadSupId();
    }


    private void clearFields() {
        sOidTxt.setText("");
       // sIdTxt  .setText("");
        dateTxt .setText("");
        weigthTxt.setText("");
    }



    @FXML
    void updateOnAction(ActionEvent event) {
        String id = sOidTxt.getText();
        String sId = sIdTxt.getValue();
        String date = dateTxt.getText();
        String weigth = weigthTxt.getText();
        int payment = Integer.parseInt(paymentTxt.getText());
        String isDelete = "0";

        var dto = new SupOrderDto(id,sId,date,weigth,payment,isDelete);
        var model = new SupOrderModel();

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

    private void generateNextId() {
        try {
            String orderId = SupOrderModel.generateNextOrderId();
            sOidTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> empList = SupplierModel.loadAllItems();

            for (SupplierDto empDto : empList) {
                obList.add(empDto.getId());
            }

            sIdTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
