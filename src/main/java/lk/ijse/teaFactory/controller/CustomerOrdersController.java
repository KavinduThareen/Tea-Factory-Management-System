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
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lk.ijse.teaFactory.model.CusOrderModel;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    private AnchorPane root;

    @FXML
    private TableView<CusOrderTm> tbl;

    @FXML
    void addOrderOnAction(ActionEvent event) throws IOException {
        root.getChildren();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/Customer_orders_add.fxml"))));

    }

    public void loadAll(){
        var model = new CusOrderModel();
        ObservableList<CusOrderTm> obList = FXCollections.observableArrayList();

        try {
            List<CusOrderDto> dtoList = model.loadAll();
            for (CusOrderDto dto : dtoList){
                obList.add(
                        new CusOrderTm(
                                dto.getId(),
                                dto.getCId(),
                                dto.getCatagary(),
                                dto.getWeigth(),
                                dto.getDate(),
                                dto.getDescreption()
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

        colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colCId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cId"));
        colCatagary.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("catagary"));
        colweigth.setCellValueFactory(new PropertyValueFactory<>("weigth"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("descreption"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
    }


}
