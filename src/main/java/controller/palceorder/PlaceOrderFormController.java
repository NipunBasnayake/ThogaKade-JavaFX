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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    public TableColumn colUnitPrice;

    @FXML
    public JFXTextField txtQuantity;

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
    private Label lblQuantityOnHand;

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
    void btnAddtoCartOnAction() {
        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem().toString();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());

        itemsObservableArray.add(new CartItem(itemCode, description, qty, unitPrice));
        tblCart.setItems(itemsObservableArray);
        setTotal();
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
        System.out.println("btnNewCustomerOnAction");
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : itemsObservableArray) {
            orderDetails.add(new OrderDetail(
                    lblOrderId.getText(),
                    cartItem.getItemCode(),
                    cartItem.getQty(),
                    cartItem.getUnitPrice()
            ));
        }

        Order order = new Order(
                lblOrderId.getText(),
                lblDate.getText(),
                cmbCustomerId.getSelectionModel().getSelectedItem().toString(),
                orderDetails
        );

        if (PlaceOrderController.getInstance().placeOrder(order)) {
            showSuccessAlert("Order Placed Successfully", "The order has been placed successfully!");
            generateOrderId();
            clearFields();
        } else {
            showErrorAlert("Order Failed", "Failed to place the order. Please try again.");
        }

        itemsObservableArray.clear();
        tblCart.refresh();
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


        loadDateAndTime();
        generateOrderId();
        loadCustomerId();
        loadItemCodes();
    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    lblTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void generateOrderId() {
        int num = Integer.parseInt(PlaceOrderController.getInstance().getLastOrderID().substring(1)) + 1;
        lblOrderId.setText(String.format("D%03d", num));
    }

    private void loadCustomerId() {
        ObservableList<String> ids = FXCollections.observableArrayList(CustomerController.getInstance().getCustomerIDs());
        cmbCustomerId.setItems(ids);
    }

    private void getCustomerName() {
        String customerId = cmbCustomerId.getValue().toString();
        if (customerId != null) {
            String customerName = CustomerController.getInstance().getCustomerName(customerId);
            lblCustomerName.setText(customerName != null ? customerName : "");
        }
    }

    private void loadItemCodes() {
        ObservableList<String> itemIDs = FXCollections.observableArrayList(ItemController.getInstance().getItemCodes());
        cmbItemCode.setItems(itemIDs);
    }

    private void setItemDetails() {
        String itemCode = cmbItemCode.getValue().toString();
        if (itemCode != null) {
            Item item = ItemController.getInstance().searchItem(itemCode);
            if (item != null) {
                lblDescription.setText(item.getDescription());
                lblUnitPrice.setText(String.format("%.2f", item.getUnitPrice()));
                lblQuantityOnHand.setText(String.valueOf(item.getQtyOnHand()));
            } else {
                showErrorAlert("Item Not Found", "The selected item code does not exist.");
            }
        }
    }

    private void setTotal() {
        double total = itemsObservableArray.stream().mapToDouble(CartItem::getTotal).sum();
        lblTotal.setText(String.format("%.2f", total));
    }

    public void clearFields() {
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        lblCustomerName.setText("");
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQuantityOnHand.setText("");
        txtQty.clear();
        lblTotal.setText("0.00");
        tblCart.getItems().clear();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
