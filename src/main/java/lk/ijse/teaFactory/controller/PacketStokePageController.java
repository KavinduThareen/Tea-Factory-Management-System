package lk.ijse.teaFactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.teaFactory.dto.*;
import lk.ijse.teaFactory.dto.tm.CompleteTm;
import lk.ijse.teaFactory.dto.tm.PacketStokeTm;
import lk.ijse.teaFactory.model.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class PacketStokePageController {

    @FXML
    private TextField catagaryTxt;

    @FXML
    private TableColumn<?, ?> colCatagary;

    @FXML
    private TableColumn<?, ?> colDelet;

    @FXML
    private TableColumn<?, ?> colEpaieDate;

    @FXML
    private TableColumn<?, ?> colWeigth;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private DatePicker expirTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TableView<CompleteTm> tblView;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField weigthTxt;

    @FXML
    private JFXComboBox<String > leavesId;

    LeavesStokeModel leavesStokeModel = new LeavesStokeModel();

    ErrorAnimation errorAnimation = new ErrorAnimation();
    NotificationAnimation notifi = new NotificationAnimation();

    @FXML
    void addOnAction(ActionEvent event) {
        String pid = idTxt.getText();
        String catagory = catagaryTxt.getText();
        String   weigth = weigthTxt.getText();
        Date date = Date.valueOf(expirTxt.getValue());
        String leavesStokeId = leavesId.getValue();

        var dto = new PacketStokeDto(pid,catagory,weigth,date);
        var model = new PacketStokeModel();
        boolean isValidated = validate();
        var stokemodel = new StokeDetailModel();

        if (isValidated) {
            try {
                boolean isSaved = model.packetStokeSaved(dto);
                boolean drop = leavesStokeModel.drop(leavesStokeId,weigth);
                boolean saved1 = stokemodel.detail(pid,leavesStokeId,date);

                if (isSaved) {
                    notifi.showNotification("Saved");
                    loadAll();
                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean validate() {

        String idText = idTxt.getText();
        boolean isIDValidated = Pattern.matches("[P][0-9]{3,}", idText);
        if (!isIDValidated) {
            errorAnimation.animateError(idTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid ID!").show();
            return false;
        }

        String catagaryTxtText = catagaryTxt.getText();
        boolean isNameValidated = Pattern.matches("[A-Za-z]{3,}", catagaryTxtText);
        if (!isNameValidated) {
            errorAnimation.animateError(catagaryTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid catagary").show();
            return false;
        }

        String weigthTxtText = weigthTxt.getText();
        boolean isAddressValidated = Pattern.matches("\\d+(\\.\\d+)?", weigthTxtText);
        if (!isAddressValidated) {
            errorAnimation.animateError(weigthTxt);
            new Alert(Alert.AlertType.ERROR, "Invalid weight").show();
            return false;
        }
        return true;
    }

    private void loadLeavesId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<LeavesStokeDto> empList = LeavesStokeModel.loadAll();

            for (LeavesStokeDto leavesDto : empList) {
                obList.add(leavesDto.getId());
            }

            leavesId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {

        String id = idTxt.getText();
        String catagory = catagaryTxt.getText();
        String   weigth = weigthTxt.getText();
        Date date = Date.valueOf(expirTxt.getValue());

        var dto = new PacketStokeDto(id,catagory,weigth,date);
        var model = new PacketStokeModel();

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

    public  void loadAll(){

    var model = new PacketStokeModel();
        ObservableList<CompleteTm> obList = FXCollections.observableArrayList();

        try {
            List<PacketStokeDto> dtoList = model.loadAll();
            for (PacketStokeDto dto : dtoList){

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
                        int selectedIndex = tblView.getSelectionModel().getSelectedIndex();
                        String id = (String) colid.getCellData(selectedIndex);

                        deleteItem(id);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblView .refresh();
                    }
                });
                obList.add(
                        new CompleteTm(
                                dto.getId(),
                                dto.getCatagory(),
                                dto.getWeigth(),
                                dto.getDate(),
                                btnDelete
                        )
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tblView.setItems(obList);

    }

    private void setCellValueFactory() {

        colid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        colCatagary.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("catogary"));
        colWeigth.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("weigth"));
        colEpaieDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDelet.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("btnDelete"));

    }

    public void initialize() {
        setCellValueFactory();
        loadAll();
        generateNextCusId();
        loadLeavesId();
    }

    private void generateNextCusId() {
        try {
            String orderId = PacketStokeModel.generateNextOrderId();
            idTxt.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String id) {
        try {
            boolean isDeleted = PacketStokeModel.delete(id);
            if(isDeleted)
                notifi.showNotification("Delete");
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void clearFields() {
        idTxt.setText("");
        catagaryTxt.setText("");
        weigthTxt .setText("");
     //   expirTxt.setDa("");
    }

    @FXML
    void searchOnAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String id = idTxt.getText();

            var model = new PacketStokeModel();
            try {
                PacketStokeDto packetStokeDto = model.searchCustomer(id);

                if (packetStokeDto != null) {
                    idTxt.setText(packetStokeDto.getId());
                    catagaryTxt.setText(packetStokeDto.getCatagory());
                    weigthTxt.setText(packetStokeDto.getWeigth());
                    expirTxt.setValue(packetStokeDto.getDate().toLocalDate());

                } else {
                    notifi.showNotification("stoke not found");
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void backOnAction(ActionEvent event) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/view/ourStoke.fxml"))));
    }


}
