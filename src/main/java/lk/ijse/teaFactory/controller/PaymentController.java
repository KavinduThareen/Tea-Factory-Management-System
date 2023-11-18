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
import lk.ijse.teaFactory.dto.SalaryDto;
import lk.ijse.teaFactory.dto.tm.SalaryTm;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.EmployeeModel;
import lk.ijse.teaFactory.model.SalaryModel;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PaymentController {

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> coldelete;

    @FXML
    private TableColumn<?, ?> colempId;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colsCount;

    @FXML
    private TextField countTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private JFXComboBox<String > empIdTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TableView<SalaryTm> tbl;

    @FXML
    private AnchorPane root;





    @FXML
    void addOnAction(ActionEvent event) {
         String id = idTxt.getText();
         String empId = (String) empIdTxt.getValue();
         String date = dateTxt.getText();
         String count = countTxt.getText();
         String delete = "0";

        var dto = new SalaryDto(id,empId,date,count,delete);

        var model = new SalaryModel();

        try {
            boolean isSaved = model.salarySaved(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String empId = (String) empIdTxt.getValue();
        String date = dateTxt.getText();
        String count = countTxt.getText();
        String delete = "0";

        var dto = new SalaryDto(id,empId,date,count,delete);

        var model = new SalaryModel();
        try {
            boolean isUpdated = model.updateSalary(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                //clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
        loadEmpId();
        generateNextPaymentId();
    }

    public void loadAllEmployees(){
        var model =new SalaryModel();

        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<SalaryDto> dtoList =model.getAllSalary();
            for (SalaryDto dto : dtoList){


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
                        String id = (String) colid.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tbl.refresh();
                    }
                });

                obList.add(
                        new SalaryTm(
                                dto.getId(),
                                dto.getEmpId(),
                                dto.getDate(),
                                dto.getCount(),
                                btnDelete

                        )
                );

            }
            tbl.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {

        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colempId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colsCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        coldelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = SalaryModel.deleteItem(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void loadEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> empList = EmployeeModel.loadAllItems();

            for (EmployeeDto empDto : empList) {
                obList.add(empDto.getEmployeeName());
            }

            empIdTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextPaymentId() {
        try {
            String orderId = SalaryModel.generateNextOrderId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
