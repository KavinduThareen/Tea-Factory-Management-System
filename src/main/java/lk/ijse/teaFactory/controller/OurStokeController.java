package lk.ijse.teaFactory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class OurStokeController {

    @FXML
    private AnchorPane root;

    @FXML
    void levesStokeBtnOnAction(ActionEvent event) throws IOException {

        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/leves_stoke_page.fxml"))));

    }

    @FXML
    void packetStokeBtnOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/packet_stoke_page.fxml"))));

    }

}
