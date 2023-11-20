package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.PaseOrderDto;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.PacketStokeModel;
import lk.ijse.teaFactory.model.PlaseOrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private JFXComboBox<String> cIdTxt;

    @FXML
    private JFXComboBox<String> catagaryTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private TextArea descreptionTxt;

    @FXML
    private TextField WeigthTxt;


    @FXML
    private TextField idTxt;

    @FXML
    private TextField paymentTxt;

    @FXML
    private TableView<CusOrderTm> tbl;

    private ObservableList<CusOrderTm> obList2 = FXCollections.observableArrayList();

    @FXML
    void addOrderOnAction(ActionEvent event) throws IOException {
        root.getChildren();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/Customer_orders_add.fxml"))));

    }

    @FXML
    void addOnAction(ActionEvent event) {


        String id = idTxt.getText();
        String cId = (String) cIdTxt.getValue();
        String catagary = (String) catagaryTxt.getValue();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        Double payment = Double.valueOf(paymentTxt.getText()) * Double.valueOf(WeigthTxt.getText());
        String complete = "0";

        var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,payment,complete);

        var model = new CusOrderModel();
        //   boolean isValidated = validate();

        //  if (isValidated) {
        //  new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();

        try {
            boolean isSaved = model.cusOrdersSaved(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "saved").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


         /*


        String id = idTxt.getText();
        String cId = (String) cIdTxt.getValue();
        String catagary = (String) catagaryTxt.getValue();
        String weigth = WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        Double payment = Double.valueOf(paymentTxt.getText()) * Double.valueOf(WeigthTxt.getText());
        // String complete = "0";
        JFXButton btnDelete = new JFXButton("Deleted");
        btnDelete.setCursor(Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);

        //   CusOrderTm tm = new CusOrderTm();

        //   tm.getBtnDelete()
        btnDelete.setOnAction((e) -> {

            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tbl.getSelectionModel().getSelectedIndex();
                String cid = (String) colId.getCellData(selectedIndex);

                deleteItem(cid);   //delete item from the database

                obList2.remove(selectedIndex);   //delete item from the JFX-Table
                tbl.refresh();
            }
        });

        if (!obList2.isEmpty()) {
            for (int i = 0; i < tbl.getItems().size(); i++) {
                if (colId.getCellData(i).equals(id)) {
                    int col_qty = (int) colPayment.getCellData(i);
                    weigth += col_qty;
                    payment = payment * Double.valueOf(weigth);


                    obList2.get(i).setWeigth(weigth);
                    obList2.get(i).setPayment(payment);

                    calculateTotal();
                    tbl.refresh();
                    return;
                }
            }
        }
        var cartTm = new CusOrderTm(id, cId, catagary, weigth, date, descreption, payment, btnDelete);

        obList2.add(cartTm);

        tbl.setItems(obList2);
        calculateTotal();
        // we.clear();
        */
    }




    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tbl.getItems().size(); i++) {
            total += (double) colPayment.getCellData(i);
        }
        paymentTxt.setText(String.valueOf(total));
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
        setCellValueFactory();
        loadAll();

    }

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


    @FXML
    void updateOnAction(ActionEvent event) {

        /*
        String id = idTxt.getText();
        String cId = (String) cIdTxt.getValue();
        String catagary = (String) catagaryTxt.getValue();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        Double payment = Double.valueOf(paymentTxt.getText());
        String complete = "0";


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

         */



    }

    void clearFields() {

        idTxt.setText("");
        //  catagaryTxt.setText("");
        WeigthTxt.setText("");
        dateTxt.setText("");
        descreptionTxt.setText("");
        paymentTxt.setText("");

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



    @FXML
    void plaseOrderOnAction(ActionEvent event) throws SQLException {
        /*
        // Check if an item is selected in the table
        if (tbl.getSelectionModel().getSelectedItem() != null) {
           // CusOrderTm selectedOrder = tbl.getSelectionModel().getSelectedItem();

            String id = idTxt.getText();
            String cId = (String) cIdTxt.getValue();
            String catagary = (String) catagaryTxt.getValue();
            String weigth =  WeigthTxt.getText();
            String date = dateTxt.getText();
            String descreption = descreptionTxt.getText();
            Double payment = Double.valueOf(paymentTxt.getText()) * Double.valueOf(WeigthTxt.getText());
            String complete = "0";

            List<CusOrderTm> cartTmList = new ArrayList<>();
            for (int i = 0; i < tbl.getItems().size(); i++) {
                CusOrderTm cartTm = obList2.get(i);

                cartTmList.add(cartTm);
            }

            System.out.println("Place order from controller: " + cartTmList);
            var paseOrderDto = new PaseOrderDto(id, cId, catagary, weigth, date, descreption,payment,complete, cartTmList);
            boolean isSuccess = PlaseOrderModel.placeOrder(paseOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
            }
        } else {
            // Show an alert or handle the case where no item is selected in the table
            new Alert(Alert.AlertType.WARNING, "Please select an item in the table.").show();
        }

         */
    }


}







