package lk.ijse.teaFactory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.EmployeeDto;
import lk.ijse.teaFactory.dto.tm.EmployeeTm;
import lk.ijse.teaFactory.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
    private TableView<EmployeeTm> tblEmployee;

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
                obList.add(
                        new EmployeeTm(
                                dto.getUId(),
                                dto.getEmployeeId(),
                                dto.getEmpGender(),
                                dto.getEmpbd(),
                                dto.getEmployeeName(),
                                dto.getEmpAddress(),
                                dto.getEmpContac()

                        )
                );

            }
            tblEmployee.setItems(obList);



        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    }

    @FXML
    void backBtnOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/employee_page.fxml"))));

    }
  





}
