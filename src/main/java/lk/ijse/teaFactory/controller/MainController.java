package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.teaFactory.dto.CustomerDto;
import lk.ijse.teaFactory.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class MainController {

    @FXML
    private AnchorPane dashbordRoot;

    @FXML
    private JFXButton singoutbtn;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField searchTxt;

    private CustomerModel customerModel = new CustomerModel();



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
    void searchOnAction(KeyEvent event) throws IOException, SQLException {
        String id = searchTxt.getText();
        if (event.getCode() == KeyCode.ENTER) {
                if (id.equals("C001")) {
                    customerModel.searchCustomer(id);
                    dashbordRoot.getChildren().clear();
                    dashbordRoot.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/customer_add_page.fxml"))));

                }
        }
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
