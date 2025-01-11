package controller.palceorder;

import model.Order;

import java.util.List;

public interface PlaceOrderServices {
    String getLastOrderID();
    boolean placeOrder(Order order);
}
