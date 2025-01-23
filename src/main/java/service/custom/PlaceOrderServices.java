package service.custom;

import model.Order;

import java.sql.SQLException;

public interface PlaceOrderServices {
    String getLastOrderID();
    boolean placeOrder(Order order) throws SQLException;
}
