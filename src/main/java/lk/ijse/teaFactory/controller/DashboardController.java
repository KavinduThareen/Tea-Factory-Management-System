package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {

    @FXML
    private JFXButton dashboardbtn;

    @FXML
    private JFXButton singoutbtn;

    @FXML
    private JFXButton stokebtn;

    @FXML
    private AnchorPane dashbordRoot;

    @FXML
    void dashbordbtnOnAction(ActionEvent event) {

    }

    @FXML
    void singoutbtnOnAction(ActionEvent event) {

    }

    @FXML
    void stokebtnOnAction(ActionEvent event) throws IOException {

        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/ourstoke_page.fxml"))));

    }

}
