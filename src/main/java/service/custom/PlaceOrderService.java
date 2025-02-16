package service.custom;

import dto.Order;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderService extends SuperService {
    String getLastOrderID();
    List<Order> getOrders();
    boolean placeOrder(Order order) throws SQLException;
}
