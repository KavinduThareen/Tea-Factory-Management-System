package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class CustomerAddPageController {

    @FXML
    private AnchorPane addcusbackBtn;

    @FXML
    void addcusBackeBtn(ActionEvent event) throws IOException {
        addcusbackBtn.getChildren().clear();
        addcusbackBtn.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_page.fxml"))));


    }

}
