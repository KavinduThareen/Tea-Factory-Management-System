package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.SupplierDto;
import lk.ijse.teaFactory.dto.tm.SupplierTm;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.SupplierModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String name = nameTxt.getText();
        String address = Address.getText();
        String contac= Contac.getText();
        String complete = "0";

        var dto = new SupplierDto(id,name,address,contac,complete);
        var model = new SupplierModel();

        try {
            boolean isSaved = model.supplierSaved(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
            }
        } catch (Exception e) {
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

                    obList.remove(selectedIndex);   //delete item from the JFX-Table
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
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String name = nameTxt.getText();
        String address = Address.getText();
        String contac= Contac.getText();
        String complete = "0";

        var dto = new SupplierDto(id,name,address,contac,complete);
        var model = new SupplierModel();

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
