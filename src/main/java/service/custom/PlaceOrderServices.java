package service.custom;

import model.Order;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderServices {
    String getLastOrderID();
    List<Order> getOrders();
    boolean placeOrder(Order order) throws SQLException;
}
