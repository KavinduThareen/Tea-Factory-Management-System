package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.*;
import lk.ijse.teaFactory.dto.tm.CartTm;
import lk.ijse.teaFactory.model.CusOrderModel;
import lk.ijse.teaFactory.model.CustomerModel;
import lk.ijse.teaFactory.model.PacketStokeModel;
import lk.ijse.teaFactory.model.PlaseOrderModel;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;



import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
public class CustomerOrdersController {
    @FXML
    private TableColumn<?, ?> colCId;

    @FXML
    private TableColumn<?, ?> colCatagary;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colweigth;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXComboBox<String> cIdTxt;

    @FXML
    private JFXComboBox<String> catagaryTxt;

    @FXML
    private TextField dateTxt;

    @FXML
    private TextField descreptionTxt;

    @FXML
    private TextField WeigthTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField paymentTxt;

    @FXML
    private TableView<CartTm> tbl;

    @FXML
    private JFXComboBox<String> itemIdTxt;

    @FXML
    private  Label tolLbl;

    ErrorAnimation errorAnimation = new ErrorAnimation();
    NotificationAnimation noti = new NotificationAnimation();

  //  private CustomerModel customerModel = new CustomerModel();
  //  private PacketStokeModel packetStokeModel = new PacketStokeModel();
   // private CusOrderModel cusOrderModel = new CusOrderModel();
    private PlaseOrderModel plaseOrderModel = new PlaseOrderModel();

    private ObservableList<CartTm> obList2 = FXCollections.observableArrayList();

    @FXML
    void addOnAction(ActionEvent event) {

        String Itemid = itemIdTxt.getValue();
        String cusid = cIdTxt.getValue();
        String descreption = descreptionTxt.getText();
        String catagary = (String) catagaryTxt.getValue();
        Double weigth = Double.valueOf(WeigthTxt.getText());
        LocalDate date = LocalDate.parse(dateTxt.getText());
        Double payment = Double.valueOf(paymentTxt.getText());
        tolLbl.setText(String.valueOf(weigth * payment));
      //  Double total =  Double.valueOf(paymentTxt.getText()) * Double.valueOf(WeigthTxt.getText());
        JFXButton btnDelete = new JFXButton("Deleted");

        setRemoveBtnAction(btnDelete);
        btnDelete.setCursor(Cursor.HAND);
        btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

        btnDelete.setPrefWidth(100);
        btnDelete.setPrefHeight(30);

        boolean isValidated = validate();

        if (isValidated) {
                noti.showNotification("Order Saved Successfully!");

            if (!obList2.isEmpty()) {
                for (int i = 0; i < tbl.getItems().size(); i++) {
                    if (colId.getCellData(i).equals(Itemid)) {
                        int col_qty = (int) colPayment.getCellData(i);
                        weigth += col_qty;
                        payment = payment * Double.valueOf(weigth);


                        obList2.get(i).setWeigth(weigth);
                        obList2.get(i).setPayment(payment);

                        calculateTotal();
                        tbl.refresh();
                        return;
                    }
                }
            }

            var cartTm = new CartTm(Itemid, cusid, descreption, catagary, weigth, payment, date, btnDelete);

            obList2.add(cartTm);

            tbl.setItems(obList2);
            calculateTotal();
        }
    }

    private boolean validate() {

        String descreptionTxtText = descreptionTxt.getText();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", descreptionTxtText);
        if (!isNameValidated) {
            errorAnimation.animateError(descreptionTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid descreption").show();
            return false;
        }

        String catagaryTxtValue = catagaryTxt.getValue();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", catagaryTxtValue);
        if (!isAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid catagary").show();
            return false;
        }

        String weight = WeigthTxt.getText();
        boolean isweightValidated = Pattern.matches("\\d+(\\.\\d+)?", weight);
        if (!isweightValidated) {
            errorAnimation.animateError(WeigthTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid weight").show();
            return false;
        }
        return true;
    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tbl.getItems().size(); i++) {
            total += (double) colPayment.getCellData(i);
        }
        paymentTxt.setText(String.valueOf(total));
    }

    private void loadCusOrdersId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = CustomerModel.loadAllItems();

            for (CustomerDto cusODto : cusList) {
                obList.add(cusODto.getCusid());
            }

            cIdTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextCusOrderId() {
        try {
            String orderId = CusOrderModel.generateNextOrderId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        loadCusOrdersId();
        generateNextCusOrderId();
        loadCatagary();
        setCellValueFactory();
        loadItemCodes();
        setDate();

    }

    private void loadCatagary() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PacketStokeDto> empList = PacketStokeModel.loadAllcatagary();

            for (PacketStokeDto cusODto : empList) {
                obList.add(cusODto.getCatagory());
            }
            catagaryTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PacketStokeDto> itemDtos = PacketStokeModel.loadAllItems();

            for (PacketStokeDto dto : itemDtos) {
                obList.add(dto.getId());
            }
            itemIdTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tbl.getSelectionModel().getSelectedIndex();

                obList2.remove(focusedIndex);
                tbl.refresh();
                clearFields();
                calculateTotal();
            }
        });
    }

    @FXML
    void updateOnAction(ActionEvent event) throws JRException, SQLException {
        try {
            printCustomer();
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    void clearFields() {

        idTxt.setText("");
        catagaryTxt.setValue("");
        WeigthTxt.setText("");
        dateTxt.setText("");
        descreptionTxt.setText("");
        paymentTxt.setText("");

    }


    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colCId.setCellValueFactory(new PropertyValueFactory<>("cusid"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("descreption"));
        colCatagary.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("catagary"));
        colweigth.setCellValueFactory(new PropertyValueFactory<>("weigth"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    private void setDate() {
        dateTxt.setText(String.valueOf(LocalDate.now()));
    }
    public int count = 0;

    @FXML
    void plaseOrderOnAction(ActionEvent event) throws SQLException {

            String id = idTxt.getText();
            String cId = cIdTxt.getValue();
            String catagary = catagaryTxt.getValue();
            Double weigth = Double.valueOf(WeigthTxt.getText());
            LocalDate date = LocalDate.parse(dateTxt.getText());
            String descreption = descreptionTxt.getText();
            Double payment = Double.valueOf(paymentTxt.getText()) * Double.valueOf(WeigthTxt.getText());

            List<CartTm> cartTmList = new ArrayList<>();
            for (int i = 0; i < tbl.getItems().size(); i++) {
                CartTm cartTm = obList2.get(i);

                cartTmList.add(cartTm);
            }

            System.out.println("Place order form controller: " + cartTmList);
            var placeOrderDto = new PaseOrderDto(id, cId, catagary,weigth,date,descreption,payment,cartTmList);
            try {
                boolean isSuccess = plaseOrderModel.placeOrder(placeOrderDto);
                if (isSuccess) {
                    noti.showNotification("Order Success!");
                    count++;
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
                throw new RuntimeException(e);
            }

    }

// jaspere repot genarater

    private void printCustomer() throws JRException, SQLException {
        try {
            // Load the JasperReport template
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/ordersBill.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("SELECT * FROM orders WHERE order_id = "+"\""+idTxt.getText()+"\"");
            load.setQuery(jrDesignQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cusidOnAction(ActionEvent event) {
        /*

        String id = cIdTxt.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            CustomerDto customerDto = customerModel.searchCustomer(id);
            colId.setText(customerDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */


    }

    @FXML
    void paymentOnAction(ActionEvent event) {
        addOnAction(event);
    }

}







