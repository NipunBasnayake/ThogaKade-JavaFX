package controller.palceorder;

import model.Order;
import model.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderServices {
    String getLastOrderID();
    boolean placeOrder(Order order) throws SQLException;
}
