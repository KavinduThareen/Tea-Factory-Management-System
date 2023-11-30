package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.ErrorAnimation;
import lk.ijse.teaFactory.dto.SupplierDto;
import lk.ijse.teaFactory.dto.tm.SupplierTm;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.SupplierModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierPageController {

    @FXML
    private TextField Address;

    @FXML
    private TextField Contac;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTm> tbl;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCantac;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> cold;

    @FXML
    private TableColumn<?, ?> coldelte;

    ErrorAnimation errora = new ErrorAnimation();

    @FXML
    void addSDetailOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/supplier_orders.fxml"))));


    }

    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String name = nameTxt.getText();
        String address = Address.getText();
        String contac= Contac.getText();
      //  String complete = "0";

        var dto = new SupplierDto(id,name,address,contac);
        var model = new SupplierModel();
        boolean isValidated = validate();

        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();

            try {
                boolean isSaved = model.supplierSaved(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "saved").show();
                    tbl.refresh();
                    clearFields();
                }
                else{
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }



    private boolean validate() {

        String idText = idTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[S][0-9]{3,}", idText);
        if (!isIDValidated) {
            errora.animateError(idTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String nameText = nameTxt.getText();
//        boolean isCustomerNameValidated = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isNameValidated) {
            errora.animateError(nameTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid customer name").show();
            return false;
        }
        String cantacText = Contac.getText();
//        boolean isCustomerAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isCantacValidated = Pattern.matches("[0-9]{10}", cantacText);
        if (!isCantacValidated) {
            errora.animateError(Contac);
            new Alert(Alert.AlertType.ERROR, "Invalid customer contac").show();
            return false;
        }

        String addressText = Address.getText();
//        boolean isAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);
        if (!isAddressValidated) {
            errora.animateError(Address);
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }
        return true;

    }



    private void generateNextSupId() {
        try {
            String orderId = SupplierModel.generateNextOrderId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void loadAll(){
        var model =new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
    try {
        List<SupplierDto> dtoList = model.loadAll();
        for (SupplierDto dto : dtoList){


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
                    String id = (String) cold.getCellData(selectedIndex);

                    deleteItem(id);   //delete item from the database

                    obList.remove(selectedIndex);//delete item from the JFX-Table
                    tbl.refresh();
                }
            });

            obList.add(
                    new SupplierTm(
                            dto.getId(),
                            dto.getName(),
                            dto.getAddress(),
                            dto.getContac(),
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
            boolean isDeleted = SupplierModel.deleteItem(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }


    private void setCellValueFactory() {

        cold.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("address"));
        colCantac.setCellValueFactory(new PropertyValueFactory<>("contac"));
        coldelte.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
        generateNextSupId();
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String name = nameTxt.getText();
        String address = Address.getText();
        String contac= Contac.getText();
     //   String complete = "0";

        var dto = new SupplierDto(id,name,address,contac);
        var model = new SupplierModel();

        try {
            boolean isUpdated = model.update(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                tbl.refresh();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void clearFields() {
        idTxt.setText("");
        nameTxt.setText("");
        Address .setText("");
        Contac.setText("");
    }

    @FXML
    void searchOnAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String id = idTxt.getText();

            var model = new SupplierModel();
            try {
                SupplierDto supplierDto = model.searchCustomer(id);
//            System.out.println(customerDto);
                if (supplierDto != null) {
                    idTxt.setText(supplierDto.getId());
                    nameTxt.setText(supplierDto.getName());
                    Address.setText(supplierDto.getAddress());
                    Contac.setText(supplierDto.getContac());

                } else {
                    new Alert(Alert.AlertType.INFORMATION, "customer not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }



}
