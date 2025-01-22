package controller.order_detail;

import db.DBConnection;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
           boolean isAdded = addOrderDetail(orderDetail);
           if (!isAdded) {
               return false;
           }
       }
       return true;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String SQL = "INSERT INTO orderdetail VALUES (?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, orderDetail.getOrderId());
            preparedStatement.setString(2, orderDetail.getItemCode());
            preparedStatement.setInt(3, orderDetail.getQty());
            preparedStatement.setDouble(4, orderDetail.getUnitPrice());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
