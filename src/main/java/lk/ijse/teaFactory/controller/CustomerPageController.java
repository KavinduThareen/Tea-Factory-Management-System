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
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.tm.CustomerTm;
import lk.ijse.teaFactory.model.CustomerModel;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
                obList.add(
                        new CustomerTm(
                                dto.getCusid(),
                                dto.getEmpid(),
                                dto.getCusname(),
                                dto.getCusAddress(),
                                dto.getCusCantac()
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


}
