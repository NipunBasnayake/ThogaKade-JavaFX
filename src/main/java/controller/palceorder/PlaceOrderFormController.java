package controller.palceorder;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.SimpleDateFormat;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CartItem;
import model.Item;
import model.Order;
import model.OrderDetail;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    public TableColumn colUnitPrice;

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
    private TableView tblCart;

    @FXML
    private JFXTextField txtQty;

    ObservableList<CartItem> itemsObservableArray = FXCollections.observableArrayList();

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {
        itemsObservableArray.add(new CartItem(
                cmbItemCode.getSelectionModel().getSelectedItem().toString(),
                lblDescription.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(lblUnitPrice.getText())
        ));
        tblCart.setItems(itemsObservableArray);
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
        System.out.println("btnNewCustomerOnAction");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if (PlaceOrderController.getInstance().placeOrder(new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbCustomerId.getSelectionModel().getSelectedItem().toString()
        ))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Order Placed Successfully");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Place Order");
            alert.show();
        }
    }

    @FXML
    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        getCustomerName();
    }

    @FXML
    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        setItemDetails();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadDateAdnTime();
        generateOrderId();
        loadCustomerId();
        loadItemCodes();
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

    private void getCustomerName() {
        String customerName = CustomerController.getInstance().getCustomerName(cmbCustomerId.getValue().toString());
        if (customerName != null) {
            lblCustomerName.setText(customerName);
        }
    }

    private void loadItemCodes() {
        ObservableList<String> itemIDs = FXCollections.observableArrayList(ItemController.getInstance().getItemCodes());
        cmbItemCode.setItems(itemIDs);
    }

    private void setItemDetails() {
        Item item = ItemController.getInstance().searchItem(cmbItemCode.getValue().toString());
        if (item != null) {
            lblDescription.setText(item.getDescription());
            lblUnitPrice.setText(item.getUnitPrice().toString());
            lblQyantityOnHand.setText(String.valueOf(item.getQtyOnHand()));
        } else {

        }
    }


}
