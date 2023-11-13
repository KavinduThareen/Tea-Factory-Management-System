package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.CusOrderDto;
import lk.ijse.teaFactory.dto.tm.CusOrderTm;
import lk.ijse.teaFactory.model.CusOrderModel;

import java.io.IOException;
import java.sql.SQLException;
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
/*
    void csetFields(CusOrderTm dto) {
        idTxt.setText(dto.getId());
        cIdTxt.setText(dto.getCId());
        catagaryTxt.setText(String.valueOf(dto.getCatagary()));
        WeigthTxt.setText(String.valueOf(dto.getWeigth()));
        dateTxt.setText(String.valueOf(dto.getDate()));
        descreptionTxt.setText(String.valueOf(dto.getDescreption()));
    }

 */

    @FXML
    void updateOnAction(ActionEvent event) {
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
            boolean isUpdated = model.update(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


}
