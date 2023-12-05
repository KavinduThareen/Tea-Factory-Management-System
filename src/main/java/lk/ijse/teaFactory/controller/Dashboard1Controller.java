package lk.ijse.teaFactory.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.teaFactory.QrcodeReader.QrCodeScanner;
import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard1Controller {

    @FXML
    private Label timeTxt;

    @FXML
    private Label lblordersTxt;

    @FXML
    private Label lblemp;

    @FXML
    private Label lblCus;

    @FXML
    private Label lblstokeCount;

    @FXML
    private Label lblPacket;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblqrAttendent;


    @FXML
    private Label lblPacketSalles;

    private ExecutorService qrScannerExecutor;

    @FXML
    public void initialize() throws SQLException {
        // Call the method to start updating the time
        generateRealTime();
        generateOrderCount();
        generateCustemereCount();
        generatempCount();
        generatestokeCount();
        generatePacketstokeCount();
        generateRealDate();
        generateordersCount();
       generateEmpAttendens();
    }

    private void generateRealTime() {
        timeTxt.setText(LocalDate.now().toString());

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            timeTxt.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void generateRealDate() {
        lblDate.setText(LocalDate.now().toString());

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblDate.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void generatempCount() throws SQLException {
        EmployeeModel order = new EmployeeModel();
        int a = order.empCount();
        lblemp.setText(String.valueOf(a));
    }


   public void generateOrderCount() throws SQLException {
      CusOrderModel order = new CusOrderModel();
       int a = order.cusCount();
        lblordersTxt.setText(String.valueOf(a));
   }

    public void generateCustemereCount() throws SQLException {
        CustomerModel customerModel = new CustomerModel();
        int a = customerModel.cusCount();

        lblCus.setText(String.valueOf(a));

    }

    public void generatestokeCount() throws SQLException {
        LeavesStokeModel leavesStokeModel = new LeavesStokeModel();
        int a = leavesStokeModel.stokeCount();

        lblstokeCount.setText(String.valueOf(a));

    }

    public void generateordersCount() throws SQLException {
        OrderDetailModel orderDetailModel = new OrderDetailModel()          ;
        int a = orderDetailModel.ordersCount();

        lblPacketSalles.setText(String.valueOf(a));

    }

    public void generatePacketstokeCount() throws SQLException {
        PacketStokeModel packetStokeModel = new PacketStokeModel();
        int a = packetStokeModel.stokeCount();

        lblPacket.setText(String.valueOf(a));

    }

    public void generateEmpAttendens() throws SQLException {
        EmpAttendensModel empAttendensModel = new EmpAttendensModel();
        int a = empAttendensModel.empAttendes();


        lblqrAttendent.setText(String.valueOf(a));

    }





    @FXML
    void leavesStokeRepoteOnAction(ActionEvent event) {

        try {
            // Load the JasperReport template
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/L_Stoke_repot.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void packetRepoteOnAction(ActionEvent event) {

        try {
            // Load the JasperReport template
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/packetStokeRepote.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void orderRepoteOnAction(ActionEvent event) {

        try {
            // Load the JasperReport template
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/OrderRepote.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void attendentRepotOnAction(ActionEvent event) {
        try {
            // Load the JasperReport template
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/empAttendens.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void qrOnAction(ActionEvent event) {

        startQRScanner();
    }

    private void startQRScanner() {
        qrScannerExecutor = Executors.newSingleThreadExecutor();
        // Run the QR code scanning process continuously
        qrScannerExecutor.execute(QrCodeScanner::qrCodeScan);
    }

}

//  QrCodeScanner.qrCodeScan();