package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;
import lk.ijse.teaFactory.dto.tm.LeaveStokeTm;
import lk.ijse.teaFactory.model.LeavesStokeModel;
import lk.ijse.teaFactory.model.PacketStokeModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LevesStokePageController {

    @FXML
    private TextField WeigthTxt;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colEdate;

    @FXML
    private TableColumn<?, ?> colSdate;

    @FXML
    private TableColumn<?, ?> colWeigth;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TextField eDateTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField sDateTxt;

    @FXML
    private TableView<LeaveStokeTm> table;

    @FXML
    void addBtnOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String weigth = WeigthTxt.getText();
        String   sDate = sDateTxt.getText();
        String eDate = eDateTxt.getText();
        String complete = "0";

        var dto = new LeavesStokeDto(id,weigth,sDate,eDate,complete);
        var model = new LeavesStokeModel();

        try {
            boolean isSaved = model.addLeavesStoke(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAll(){
        var model = new LeavesStokeModel();
        ObservableList<LeaveStokeTm> obList = FXCollections.observableArrayList();

        try {
            List<LeavesStokeDto> dtoList = model.loadAll();
            for (LeavesStokeDto dto : dtoList){

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
                        int selectedIndex = table.getSelectionModel().getSelectedIndex();
                        String id = (String) colid.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        table.refresh();
                    }
                });


                obList.add(
                        new LeaveStokeTm(
                                dto.getId(),
                                dto.getWeigth(),
                                dto.getSDate(),
                                dto.getEDate(),
                                btnDelete
                        )
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        table.setItems(obList);
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = LeavesStokeModel.delete(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {

        colid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colWeigth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("weigth"));
        colSdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("SDate"));
        colEdate.setCellValueFactory(new PropertyValueFactory<>("EDate"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
        generateNextCusId();
    }

    private void generateNextCusId() {
        try {
            String orderId = LeavesStokeModel.generateNextLeavesId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearFields() {
        idTxt.setText("");
        WeigthTxt.setText("");
        sDateTxt .setText("");
        eDateTxt.setText("");
    }

    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String weigth = WeigthTxt.getText();
        String  sDate = sDateTxt.getText();
        String eDate = eDateTxt.getText();
        String complete = "0";

        var dto = new LeavesStokeDto(id,weigth,sDate,eDate,complete);
        var model = new LeavesStokeModel();

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






}
