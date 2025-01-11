package controller.palceorder;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.SimpleDateFormat;
import javafx. util. Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private TableColumn ColUnitPrice;

    @FXML
    private JFXComboBox cmbCustomerId;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQyantityOnHand;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private JFXTextField txtQty;

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAdnTime();
        generateOrderId();
        loadCustomerId();
    }

    private void loadDateAdnTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        lblDate.setText(formatter.format(date));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    lblTime.setText(now.format(dateFormatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void generateOrderId() {
        int num = Integer.parseInt(PlaceOrderController.getInstance().getLastOrderID().substring(1));
        num++;
        String newId = String.format("D%03d", num);
        lblOrderId.setText(newId);
    }

    private void loadCustomerId() {
        List<String> customerIDs = CustomerController.getInstance().getCustomerIDs();
        ObservableList<String> ids = FXCollections.observableArrayList();
        customerIDs.forEach(customerID -> {
            ids.add(customerID);
        });
        cmbCustomerId.setItems(ids);
    }


}
