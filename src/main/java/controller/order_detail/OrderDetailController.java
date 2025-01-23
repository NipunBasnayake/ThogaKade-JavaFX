package controller.order_detail;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailController implements OrderDetailServices {
    private static OrderDetailController orderDetailController;

    public static OrderDetailController getInstance() {
        if (orderDetailController == null) {
            orderDetailController = new OrderDetailController();
        }
        return orderDetailController;
    }

    @Override
    public boolean addOrderDetail(ArrayList<OrderDetail> orderDetails) {
       for (OrderDetail orderDetail : orderDetails) {
           boolean isAdded = addOrderDetail(orderDetail);
           if (!isAdded) {
               return false;
           }
       }
       return true;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO orderdetail VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(sql, orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getUnitPrice());
        } catch (SQLException e) {
            return false;
        }
    }
}
