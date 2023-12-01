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
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.tm.CustomerTm;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.LeavesStokeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
public class CustomerPageController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContac;

    @FXML
    private TableColumn<?, ?> colDelete1;

    @FXML
    private TableColumn<?, ?> colEid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private AnchorPane cuspageroot;

    @FXML
    private TableView<CustomerTm> tabl;

    @FXML
    void addcusOnAction(ActionEvent event) throws IOException {
        cuspageroot.getChildren();
        cuspageroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_add_page.fxml"))));

    }

    public void loadAll(){
        var model = new CustomerModel();
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.loadAll();

            for(CustomerDto dto : dtoList) {

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
                        int selectedIndex = tabl.getSelectionModel().getSelectedIndex();
                        String id = (String) colid.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tabl.refresh();
                    }
                });
                obList.add(
                        new CustomerTm(
                                dto.getCusid(),
                                dto.getEmpid(),
                                dto.getCusname(),
                                dto.getCusAddress(),
                                dto.getCusCantac(),
                                btnDelete
                        )
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tabl.setItems(obList);
    }

    private void setCellValueFactory() {

        colid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cusid"));
        colEid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("empid"));
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cusname"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        colContac.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cusCantac"));
        colDelete1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = CustomerModel.delete(id);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }


}
