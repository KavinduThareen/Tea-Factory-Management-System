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
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;
import lk.ijse.teaFactory.dto.tm.LeaveStokeTm;
import lk.ijse.teaFactory.model.LeavesStokeModel;

import java.sql.SQLException;
import java.util.List;

public class LevesStokePageController {

    @FXML
    private TextField WeigthTxt;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colEdate;

    @FXML
    private TableColumn<?, ?> colSdate;

    @FXML
    private TableColumn<?, ?> colWeigth;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TextField eDateTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField sDateTxt;

    @FXML
    private TableView<LeaveStokeTm> table;

    @FXML
    void addBtnOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String weigth = WeigthTxt.getText();
        String   sDate = sDateTxt.getText();
        String eDate = eDateTxt.getText();
        String complete = "0";

        var dto = new LeavesStokeDto(id,weigth,sDate,eDate,complete);
        var model = new LeavesStokeModel();

        try {
            boolean isSaved = model.addLeavesStoke(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAll(){
        var model = new LeavesStokeModel();
        ObservableList<LeaveStokeTm> obList = FXCollections.observableArrayList();

        try {
            List<LeavesStokeDto> dtoList = model.loadAll();
            for (LeavesStokeDto dto : dtoList){
                obList.add(
                        new LeaveStokeTm(
                                dto.getId(),
                                dto.getWeigth(),
                                dto.getSDate(),
                                dto.getEDate()
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
        table.setItems(obList);
    }

    private void setCellValueFactory() {

        colid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colWeigth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("weigth"));
        colSdate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("SDate"));
        colEdate.setCellValueFactory(new PropertyValueFactory<>("EDate"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
    }


}
