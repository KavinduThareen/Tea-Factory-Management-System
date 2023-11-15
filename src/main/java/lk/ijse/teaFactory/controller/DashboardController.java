package lk.ijse.teaFactory.controller;

import com.jfoenix.animation.alert.CenterTransition;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.stage.EmbeddedWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DashboardController{

    @FXML
    private AnchorPane dashbordRoot;

    @FXML
    private JFXButton singoutbtn;

    @FXML
    private AnchorPane root;




    public AnchorPane getDashbordRoot() {
        return dashbordRoot;
    }

    @FXML
    void SupplierOnAction(ActionEvent event) throws IOException {
        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/supplier_page.fxml"))));


    }

    @FXML
    void customerOnAction(ActionEvent event) throws IOException {
        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_page.fxml"))));

    }

    @FXML
    void employeeOnBtn(ActionEvent event) throws IOException {
        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/employee_page.fxml"))));

    }

    @FXML
    void ordersOnAction(ActionEvent event) throws IOException {

        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customerOrders.fxml"))));


    }

    @FXML
    void paymentOnAction(ActionEvent event) throws IOException {
        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/payment.fxml"))));

    }

    @FXML
    void singoutbtnOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/login_page.fxml"))));

    }

    @FXML
    void stokebtnOnAction(ActionEvent event) throws IOException {

        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/ourStoke.fxml"))));

    }

}
