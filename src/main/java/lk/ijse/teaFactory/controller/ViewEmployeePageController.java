package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.NotificationAnimation;
import lk.ijse.teaFactory.dto.tm.EmployeeTm;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ViewEmployeePageController {

    @FXML
    private TableColumn<?, ?> colEid;

    @FXML
    private TableColumn<?, ?> colUid;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableColumn<?, ?> colbd;

    @FXML
    private TableColumn<?, ?> colcantac;

    @FXML
    private TableColumn<?, ?> colgender;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    NotificationAnimation notifi = new NotificationAnimation();

    @FXML
    private AnchorPane root;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
    }

    public void loadAllEmployees(){
        var model =new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList =model.getAllEmployee();
            for (EmployeeDto dto : dtoList){


                JFXButton btnDelete = new JFXButton("Deleted");
                btnDelete.setCursor(javafx.scene.Cursor.HAND);
                btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

                btnDelete.setPrefWidth(100);
                btnDelete.setPrefHeight(30);

                btnDelete .setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblEmployee.getSelectionModel().getSelectedIndex();
                        String id = (String) colEid.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblEmployee.refresh();
                    }
                });
                obList.add(
                        new EmployeeTm(
                                dto.getUId(),
                                dto.getEmployeeId(),
                                dto.getEmpGender(),
                                dto.getEmpbd(),
                                dto.getEmployeeName(),
                                dto.getEmpAddress(),
                                dto.getEmpContac(),
                                btnDelete

                        )
                );

            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = EmployeeModel.deleteItem(id);
            if(isDeleted)
                notifi.showNotification("Delete");
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {

        colEid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colgender.setCellValueFactory(new PropertyValueFactory<>("empGender"));
        colbd.setCellValueFactory(new PropertyValueFactory<>("empbd"));
        colUid.setCellValueFactory(new PropertyValueFactory<>("uId"));
        colcantac.setCellValueFactory(new PropertyValueFactory<>("empContac"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    @FXML
    void backBtnOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/employee_page.fxml"))));

    }

}
