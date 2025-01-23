package service.custom;

import model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public interface OrderDetailServices {
    List<OrderDetail> getOrderDetails();
    boolean addOrderDetail(ArrayList<OrderDetail> orderDetail);
}
