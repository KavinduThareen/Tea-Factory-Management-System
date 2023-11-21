package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;
import lk.ijse.teaFactory.dto.tm.PacketStokeTm;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.PacketStokeModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PacketStokePageController {

    @FXML
    private TextField catagaryTxt;

    @FXML
    private TableColumn<?, ?> colCatagary;

    @FXML
    private TableColumn<?, ?> colDelet;

    @FXML
    private TableColumn<?, ?> colEpaieDate;

    @FXML
    private TableColumn<?, ?> colWeigth;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TextField expirTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TableView<CompleteTm> tblView;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField weigthTxt;

    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String catagory = catagaryTxt.getText();
        String   weigth = weigthTxt.getText();
        String date = expirTxt.getText();




        var dto = new PacketStokeDto(id,catagory,weigth,date);
        var model = new PacketStokeModel();

        try {
            boolean isSaved = model.packetStokeSaved(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String catagory = catagaryTxt.getText();
        String   weigth = weigthTxt.getText();
        String date = expirTxt.getText();


        var dto = new PacketStokeDto(id,catagory,weigth,date);
        var model = new PacketStokeModel();

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

    public  void loadAll(){

    var model = new PacketStokeModel();
        ObservableList<CompleteTm> obList = FXCollections.observableArrayList();

        try {
            List<PacketStokeDto> dtoList = model.loadAll();
            for (PacketStokeDto dto : dtoList){

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
                        int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
                        String id = (String) colid.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblView .refresh();
                    }
                });


                obList.add(
                        new CompleteTm(
                                dto.getId(),
                                dto.getCatagory(),
                                dto.getWeigth(),
                                dto.getDate(),
                                btnDelete


                        )
                );
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        tblView.setItems(obList);

    }


    private void setCellValueFactory() {

        colid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colCatagary.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("catogary"));
        colWeigth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("weigth"));
        colEpaieDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDelet.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
        generateNextCusId();
    }

    private void generateNextCusId() {
        try {
            String orderId = PacketStokeModel.generateNextOrderId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void deleteItem(String id) {
        try {
            boolean isDeleted = PacketStokeModel.delete(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void clearFields() {
        idTxt.setText("");
        catagaryTxt.setText("");
        weigthTxt .setText("");
        expirTxt.setText("");
    }

}
