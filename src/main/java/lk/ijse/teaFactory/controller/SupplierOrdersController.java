package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.SupOrderDto;
import lk.ijse.teaFactory.dto.tm.SupOrderTm;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.SupOrderModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    private TextField dateTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField sIdTxt;

    @FXML
    private TextField sOidTxt;

    @FXML
    private TableView<SupOrderTm> tbl;

    @FXML
    private TextField weigthTxt;

    @FXML
    void addOnAction(ActionEvent event) {
       String id = sOidTxt.getText();
       String sId = sIdTxt.getText();
       String date = dateTxt.getText();
       String weigth = weigthTxt.getText();
       String isDelete = "0";

       var dto = new SupOrderDto(id,sId,date,weigth,isDelete);
        var model = new SupOrderModel();

        try {
            boolean isSaved = model.SupOrderSaved(dto);
            if (isSaved){
                tbl.refresh();
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
        setListener();
    }

    private void setListener() {
        tbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new SupOrderDto(
                            newValue.getId(),
                            newValue.getSId(),
                            newValue.getDate(),
                            newValue.getWeigth()

                    );
                    setFields(dto);
                });
    }

    private void setFields(SupOrderDto dto) {
        sOidTxt.setText(dto.getId());
        sIdTxt.setText(dto.getSId());
        dateTxt.setText((dto.getDate()));
        weigthTxt.setText((dto.getWeigth()));
    }





    @FXML
    void updateOnAction(ActionEvent event) {
        String id = sOidTxt.getText();
        String sId = sIdTxt.getText();
        String date = dateTxt.getText();
        String weigth = weigthTxt.getText();
        String isDelete = "0";

        var dto = new SupOrderDto(id,sId,date,weigth,isDelete);
        var model = new SupOrderModel();

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
