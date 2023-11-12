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
import lk.ijse.teaFactory.dto.PacketStokeDto;
import lk.ijse.teaFactory.dto.tm.CompleteTm;
import lk.ijse.teaFactory.dto.tm.PacketStokeTm;
import lk.ijse.teaFactory.model.PacketStokeModel;

import java.sql.SQLException;
import java.util.List;

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
    private TextField expirTxt;

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
        String date = expirTxt.getText();
        String complete = "0";




        var dto = new PacketStokeDto(id,catagory,weigth,date,complete);
        var model = new PacketStokeModel();

        try {
            boolean isSaved = model.packetStokeSaved(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"saved").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public  void loadAll(){

    var model = new PacketStokeModel();
        ObservableList<CompleteTm> obList = FXCollections.observableArrayList();

        try {
            List<PacketStokeDto> dtoList = model.loadAll();
            for (PacketStokeDto dto : dtoList){
                obList.add(
                        new CompleteTm(
                                dto.getId(),
                                dto.getCatagory(),
                                dto.getWeigth(),
                                dto.getDate()

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
    }

}
