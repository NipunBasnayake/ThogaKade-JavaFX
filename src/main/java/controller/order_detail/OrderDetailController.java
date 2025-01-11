package controller.order_detail;

import model.OrderDetail;

public class OrderDetailController implements OrderDetailServices{
    private static OrderDetailController orderDetailController;

    public static OrderDetailController getInstance() {
        return orderDetailController==null?orderDetailController=new OrderDetailController():orderDetailController;
    }

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        return false;
    }
}
