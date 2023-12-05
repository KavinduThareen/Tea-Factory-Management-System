package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.db.DbConnection;
import lk.ijse.teaFactory.dto.*;
import lk.ijse.teaFactory.dto.tm.SupOrderTm;
import lk.ijse.teaFactory.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierOrdersController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colSid;

    @FXML
    private TableColumn<?, ?> colSoId;

    @FXML
    private TableColumn<?, ?> colWeigth;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private DatePicker dateTxt;

    @FXML
    private TextField paymentTxt;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXComboBox<String > sIdTxt;

    @FXML
    private TextField sOidTxt;

    @FXML
    private TableView<SupOrderTm> tbl;

    @FXML
    private TextField weigthTxt;

    ErrorAnimation errora = new ErrorAnimation();
    NotificationAnimation notifi = new NotificationAnimation();

    @FXML
    void addOnAction(ActionEvent event) {
        String id = sOidTxt.getText();
        String sId = sIdTxt.getValue();
        Date date = Date.valueOf(dateTxt.getValue());
        String weigth = weigthTxt.getText();
        String payment = paymentTxt.getText();

        try {
            Double weigthValue = Double.parseDouble(weigth);
            Double paymentValue = Double.parseDouble(payment);
            double total = weigthValue * paymentValue;

            var dto = new SupOrderDto(id, sId, date, weigth, total);
            var model = new SupOrderModel();

            boolean isValidated = validate();

            if (isValidated) {
                try {
                    boolean isSaved = model.SupOrderSaved(dto);
                    if (isSaved) {
                        printCustomer();
                         notifi.showNotification("Saved");
                        loadAll();
                        clearFields();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error occurred: " + e.getMessage()).show();
                    throw new RuntimeException(e);
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for weight and payment").show();
        }
    }

    private boolean validate() {

        String idText = sOidTxt.getText();
        boolean isIDValidated = Pattern.matches("[s][0-9]{3,}", idText);
        if (!isIDValidated) {
            errora.animateError(sOidTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid ID!").show();
            return false;
        }

        String UidText = sIdTxt.getValue();
        boolean isUIDValidated = Pattern.matches("[S][0-9]{3,}", UidText);
        if (!isUIDValidated) {
            errora.animateError(sOidTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid supplier ID!").show();
            return false;
        }

        String addressText = weigthTxt.getText();
        boolean isAddressValidated = Pattern.matches("\\d+(\\.\\d+)?", addressText);
        if (!isAddressValidated) {
            errora.animateError(weigthTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid address").show();
            return false;
        }
        return true;
    }

    public void loadAll(){
        var model = new SupOrderModel();
        ObservableList<SupOrderTm> obList = FXCollections.observableArrayList();

        try {
            List<SupOrderDto> dtoList = model.loadAll();

            for(SupOrderDto dto : dtoList) {

                JFXButton btnDelete = new JFXButton("Deleted");
                btnDelete.setCursor(javafx.scene.Cursor.HAND);
                btnDelete.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #ffffff");

                btnDelete.setPrefWidth(100);
                btnDelete.setPrefHeight(30);

                btnDelete .setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tbl.getSelectionModel().getSelectedIndex();
                        String id = (String) colSoId.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tbl.refresh();
                    }
                });
                obList.add(
                        new SupOrderTm(
                                dto.getId(),
                                dto.getSId(),
                                dto.getDate(),
                                dto.getWeigth(),
                                dto.getPayment(),
                                btnDelete
                        )
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tbl.setItems(obList);
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = SupOrderModel.deleteItem(id);
            if(isDeleted)
                notifi.showNotification("Delete");
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {

        colSoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSid.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colWeigth.setCellValueFactory(new PropertyValueFactory<>("weigth"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colDelete.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
       // setListener();
        generateNextId();
        loadSupId();
    }

    private void clearFields() {
        sOidTxt.setText("");
       // sIdTxt  .setText("");
       // dateTxt .setText("");
        weigthTxt.setText("");
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        String id = sOidTxt.getText();
        String sId = sIdTxt.getValue();
        Date date = Date.valueOf(dateTxt.getValue());
        String weigth = weigthTxt.getText();
        int payment = Integer.parseInt(paymentTxt.getText());

        var dto = new SupOrderDto(id,sId,date,weigth,payment);
        var model = new SupOrderModel();

        try {
            boolean isUpdated = model.update(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                notifi.showNotification("Update");
                loadAll();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void printCustomer() throws JRException, SQLException {

        try {
            // Load the JasperReport template
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/SupplingBill.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("SELECT * FROM supplier_orders WHERE s_orders_id = "+"\""+sOidTxt.getText()+"\"");
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

    private void generateNextId() {
        try {
            String orderId = SupOrderModel.generateNextOrderId();
            sOidTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> empList = SupplierModel.loadAllItems();

            for (SupplierDto empDto : empList) {
                obList.add(empDto.getId());
            }

            sIdTxt.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {

        String id = sOidTxt.getText();

        var model = new SupOrderModel();
        try {
            SupOrderDto supOrderDto = model.searchSuppli(id);

            if (supOrderDto != null) {
                sOidTxt.setText(supOrderDto.getId());
                sIdTxt.setValue(supOrderDto.getSId());
                dateTxt.setValue(supOrderDto.getDate().toLocalDate());
                weigthTxt.setText(supOrderDto.getWeigth());
                paymentTxt.setText(String.valueOf(supOrderDto.getPayment()));
            } else {
                notifi.showNotification("Supplier not found");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
