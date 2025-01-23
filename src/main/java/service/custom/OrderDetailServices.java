package service.custom;

import model.OrderDetail;

import java.util.ArrayList;

public interface OrderDetailServices {
    boolean addOrderDetail(ArrayList<OrderDetail> orderDetail);
}
