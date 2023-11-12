package lk.ijse.teaFactory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.SupplierDto;
import lk.ijse.teaFactory.dto.tm.SupplierTm;
import lk.ijse.teaFactory.model.SupplierModel;

import java.util.List;

public class SupplierPageController {

    @FXML
    private TextField Address;

    @FXML
    private TextField Contac;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTm> tbl;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCantac;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> cold;

    @FXML
    private TableColumn<?, ?> coldelte;

    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String name = nameTxt.getText();
        String address = Address.getText();
        String contac= Contac.getText();
        String complete = "0";

        var dto = new SupplierDto(id,name,address,contac,complete);
        var model = new SupplierModel();

        try {
            boolean isSaved = model.supplierSaved(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAll(){
        var model =new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
    try {
        List<SupplierDto> dtoList = model.loadAll();
        for (SupplierDto dto : dtoList){
            obList.add(
                    new SupplierTm(
                            dto.getId(),
                            dto.getName(),
                            dto.getAddress(),
                            dto.getContac()

                    )
            );

        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
        for (int i = 0; i < obList.size(); i++) {
            int current = i;
            obList.get(i).getBtnDelete().setOnAction(event -> {
                obList.remove(current);
            });
        }
        tbl.setItems(obList);

    }

    private void setCellValueFactory() {

        cold.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("address"));
        colCantac.setCellValueFactory(new PropertyValueFactory<>("contac"));
        coldelte.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
    }


}
