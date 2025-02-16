package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.Order;
import dto.OrderDetail;
import dto.OrdersTableItems;
import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.ItemServiceImpl;
import service.custom.impl.OrderDetailServiceImpl;
import service.custom.impl.PlaceOrderServiceImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private TableColumn ColDate;

    @FXML
    private TableColumn colCustomer;

    @FXML
    private TableColumn colID;

    @FXML
    private TableColumn colItem;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableView tblOrders;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtOrderDate;

    @FXML
    private JFXTextField txtOrderID;

    ObservableList<OrdersTableItems> observableTableItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        populateTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (txtOrderID.getText().isEmpty() &&
                txtOrderDate.getText().isEmpty() &&
                txtCustomerID.getText().isEmpty() &&
                txtCustomerName.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Search Warning");
            alert.setHeaderText("Please enter at least one search criterion!");
            alert.showAndWait();
            return;
        }

        List<OrdersTableItems> filteredItems = new ArrayList<>();

        for (OrdersTableItems item : observableTableItems) {
            boolean matchesOrderId = txtOrderID.getText().isEmpty() || item.getOrderId().equals(txtOrderID.getText());
            boolean matchesOrderDate = txtOrderDate.getText().isEmpty() || txtOrderDate.getText().equals(item.getDate().toString());
            boolean matchesCustomerName = txtCustomerName.getText().isEmpty() || txtCustomerName.getText().equals(item.getCustomer().split(" - ")[1]);
            boolean matchesCustomerId = txtCustomerID.getText().isEmpty() || txtCustomerID.getText().equals(item.getCustomer().split(" - ")[0]);

            if (matchesOrderId && matchesOrderDate && matchesCustomerName && matchesCustomerId) {
                filteredItems.add(item);
            }
        }

        if (filteredItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Results");
            alert.setHeaderText("No matching orders found.");
            alert.showAndWait();
        } else {
            tblOrders.setItems(FXCollections.observableList(filteredItems));
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent actionEvent) {
        populateTable();
    }

    private void populateTable() {
        List<Order> orders = PlaceOrderServiceImpl.getInstance().getOrders();
        List<OrderDetail> orderDetails = OrderDetailServiceImpl.getInstance().getOrderDetails();
        List<OrdersTableItems> tableItems = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            for (Order order : orders) {
                if (order.getOrderId().equals(orderDetail.getOrderId())) {
                    tableItems.add(new OrdersTableItems(
                            order.getOrderId(),
                            order.getOrderDate(),
                            order.getCustomerId() + " - " + CustomerServiceImpl.getInstance().getCustomerName(order.getCustomerId()),
                            orderDetail.getItemCode() + " - " + ItemServiceImpl.getInstance().searchItem(orderDetail.getItemCode()).getDescription(),
                            orderDetail.getQty(),
                            orderDetail.getUnitPrice(),
                            orderDetail.getQty() * orderDetail.getUnitPrice()
                    ));
                }
            }
        }
        observableTableItems = FXCollections.observableList(tableItems);
        tblOrders.setItems(observableTableItems);
    }
}
