package controller.order_detail;

import db.DBConnection;
import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailController implements OrderDetailServices {
    private static OrderDetailController orderDetailController;

    public static OrderDetailController getInstance() {
        return orderDetailController == null ? orderDetailController = new OrderDetailController() : orderDetailController;
    }

    @Override
    public boolean addOrderDetail(ArrayList<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isAdded = addToTable(orderDetail);
            if (!isAdded) {
                return false;
            }
        }
        return !orderDetails.isEmpty();
    }

    public static boolean addToTable(OrderDetail orderDetail) {
        PreparedStatement stm = null;
        try {
            stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO orderdetail VALUES(?,?,?,?)");
            stm.setObject(1, orderDetail.getOrderId());
            stm.setObject(2, orderDetail.getItemCode());
            stm.setObject(3, orderDetail.getQty());
            stm.setObject(4, orderDetail.getUnitPrice());
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
