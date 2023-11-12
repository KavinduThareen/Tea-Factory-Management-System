package lk.ijse.teaFactory.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.dto.LeavesStokeDto;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lk.ijse.teaFactory.dto.tm.LeaveStokeTm;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.LeavesStokeModel;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CustomerOrdersAddController {

    @FXML
    private TextField WeigthTxt;

    @FXML
    private TextField cIdTxt;

    @FXML
    private TextField catagaryTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private TextField descreptionTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private AnchorPane root;

    @FXML
    void addOnAction(ActionEvent event) {
        String id = idTxt.getText();
        String cId = cIdTxt.getText();
        String catagary = catagaryTxt.getText();
        String weigth =  WeigthTxt.getText();
        String date = dateTxt.getText();
        String descreption = descreptionTxt.getText();
        String complete = "0";

        var dto = new CusOrderDto(id,cId,catagary,weigth,date,descreption,complete);
        var model = new CusOrderModel();

        try {
            boolean isSaved = model.cusOrdersSaved(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customerOrders.fxml"))));


    }

}
