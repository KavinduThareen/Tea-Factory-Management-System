package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private AnchorPane dashbordRoot;

    @FXML
    private JFXButton singoutbtn;

    @FXML
    private AnchorPane root;




    public AnchorPane getDashbordRoot() {
        return dashbordRoot;
    }

    public void initialize() throws IOException {
        initializeDashboard();
    }

    private void initializeDashboard() throws IOException {
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/dashboard1.fxml"));

        this.dashbordRoot.getChildren().clear();
        this.dashbordRoot.getChildren().add(node);
    }

    @FXML
    void dashboardOnAction(ActionEvent event) throws IOException {
        initializeDashboard();
    }

    @FXML
    void profileOnAction(ActionEvent event) throws IOException {
        dashbordRoot.getChildren().clear();
        dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/userProfile.fxml"))));

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
