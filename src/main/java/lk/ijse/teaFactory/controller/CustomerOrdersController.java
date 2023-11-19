package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lk.ijse.teaFactory.model.CusOrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CustomerOrdersController {
    @FXML
    private TableColumn<?, ?> colCId;

    @FXML
    private TableColumn<?, ?> colCatagary;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colweigth;

    @FXML
    private TableColumn<?, ?> colPayment;


    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CusOrderTm> tbl;

    @FXML
    void addOrderOnAction(ActionEvent event) throws IOException {
        root.getChildren();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/Customer_orders_add.fxml"))));

    }


    public void loadAll() {
        var model = new CusOrderModel();
        ObservableList<CusOrderTm> obList = FXCollections.observableArrayList();

        try {
            List<CusOrderDto> dtoList = model.loadAll();
           for (CusOrderDto dto : dtoList){

             JFXButton btnDelete = new JFXButton("Deleted");
                btnDelete.setCursor(Cursor.HAND);
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
                        String id = (String) colId.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tbl.refresh();
                    }
                });
                obList.add(
                        new CusOrderTm(
                                dto.getId(),
                                dto.getCId(),
                                dto.getCatagary(),
                                dto.getWeigth(),
                                dto.getDate(),
                                dto.getDescreption(),
                                dto.getPayment(),
                                btnDelete
                        )
                );

          }
            tbl.setItems(obList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }




    private void deleteItem(String id) {
        try {
            boolean isDeleted = CusOrderModel.deleteItem(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cId"));
        colCatagary.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("catagary"));
        colweigth.setCellValueFactory(new PropertyValueFactory<>("weigth"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("descreption"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }




    public void initialize() {
        setCellValueFactory();
        loadAll();
       // setListener();

    }
/*
    private void setListener() {
        CustomerOrdersAddController cus = new CustomerOrdersAddController();
        tbl.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var tm = new CusOrderTm(
                            newValue.getId(),
                            newValue.getCId(),
                            newValue.getCatagary(),
                            newValue.getWeigth(),
                            newValue.getDate(),
                            newValue.getDescreption(),
                            newValue.getPayment(),
                            newValue.getBtnDelete()

                    );
                 //  cus.csetFields(tm);
                });
    }

 */


}
