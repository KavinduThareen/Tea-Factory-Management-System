package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;
import lk.ijse.teaFactory.dto.tm.PacketStokeTm;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.LeavesStokeModel;
import lk.ijse.teaFactory.model.PacketStokeModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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
    private DatePicker expirTxt;


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
        Date date = Date.valueOf(expirTxt.getValue());




        var dto = new PacketStokeDto(id,catagory,weigth,date);
        var model = new PacketStokeModel();
        boolean isValidated = validate();

        if (isValidated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();
            try {
                boolean isSaved = model.packetStokeSaved(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "saved").show();
                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean validate() {

        String idText = idTxt.getText();
//        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        boolean isIDValidated = Pattern.matches("[P][0-9]{3,}", idText);
        if (!isIDValidated) {
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
/*
        String addressText = weigthTxt.getText();
//        boolean isAddressValidated = Pattern.compile("[A-Za-z0-9]{3,}").matcher(addressText).matches();
        boolean isAddressValidated = Pattern.matches("\\b(([0-9]\\d|[0-9]\\d\\,\\d{1,2}|[1]\\d\\d|[1]\\d\\d\\,\\d{1,2})\\b", addressText);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

 */

        return true;
    }


    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String catagory = catagaryTxt.getText();
        String   weigth = weigthTxt.getText();
        Date date = Date.valueOf(expirTxt.getValue());


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
      ///  expirTxt.setValue("");
    }

    @FXML
    void searchOnAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String id = idTxt.getText();

            var model = new PacketStokeModel();
            try {
                PacketStokeDto packetStokeDto = model.searchCustomer(id);
//            System.out.println(customerDto);
                if (packetStokeDto != null) {
                    idTxt.setText(packetStokeDto.getId());
                    catagaryTxt.setText(packetStokeDto.getCatagory());
                    weigthTxt.setText(packetStokeDto.getWeigth());
                    expirTxt.setValue(packetStokeDto.getDate().toLocalDate());

                } else {
                    new Alert(Alert.AlertType.INFORMATION, "customer not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


}
