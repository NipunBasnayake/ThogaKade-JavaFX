package service.custom;

import model.OrderDetail;
import service.SuperService;

import java.util.ArrayList;
import java.util.List;

public interface OrderDetailService extends SuperService {
    List<OrderDetail> getOrderDetails();
    boolean addOrderDetail(ArrayList<OrderDetail> orderDetail);
}
