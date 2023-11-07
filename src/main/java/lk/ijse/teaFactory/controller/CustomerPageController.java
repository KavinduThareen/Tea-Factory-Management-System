package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class CustomerPageController {

    @FXML
    private AnchorPane cuspageroot;

    @FXML
    void addcusOnAction(ActionEvent event) throws IOException {

        cuspageroot.getChildren().clear();
        cuspageroot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_add_page.fxml"))));


    }

}
