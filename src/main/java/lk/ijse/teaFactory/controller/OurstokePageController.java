package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class OurstokePageController {

    @FXML
    private JFXButton leavesstokebtn;

    @FXML
    private JFXButton ourstokebackebtn;

    @FXML
    private JFXButton packetstokebtn;

    @FXML
    private AnchorPane ourstokeRoot;

    @FXML
    void leavesstokebtnOnAction(ActionEvent event) throws IOException {
        ourstokeRoot.getChildren().clear();
        ourstokeRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/leves_stoke_page.fxml"))));

    }

    @FXML
    void ourstokebackebtnOnAction(ActionEvent event) {

    }

    @FXML
    void packetstokebtnOnAction(ActionEvent event) throws IOException {

        ourstokeRoot.getChildren().clear();
        ourstokeRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/packet_stoke_page.fxml"))));

    }

}
