package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.customerDto;
import lk.ijse.teaFactory.model.customerModel;

import java.io.IOException;
import java.util.Objects;

public class CustomerAddPageController {

    @FXML
    private AnchorPane addcusbackBtn;

    @FXML
    private TextField cusAddressTxt;

    @FXML
    private TextField cuscontacTxt;

    @FXML
    private TextField cusidTxt;

    @FXML
    private TextField cusnameTxt;

    @FXML
    private TextField empidTxt;

    @FXML
    void addNewCusOnAction(ActionEvent event) {

    }

    @FXML
    void addcusBackeBtn(ActionEvent event) throws IOException {
        addcusbackBtn.getChildren().clear();
        addcusbackBtn.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_page.fxml"))));

    }

    @FXML
    void cusSaveOnAction(ActionEvent event) {

        String cusid = cusidTxt.getText();
        String empid = empidTxt.getText();
        String cusname = cusnameTxt.getText();
        String cusAddress = cusAddressTxt.getText();
        String cusCantac = cuscontacTxt.getText();

        var dto = new customerDto(cusid,empid,cusname,cusAddress,cusCantac);

        var model = new customerModel();

        try {
            boolean isSaved = model.customerSaved(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // not complet countiniue this


    }

    @FXML
    void addnewcusOnAction(ActionEvent event) {

    }

}
