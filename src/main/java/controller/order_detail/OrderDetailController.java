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
        Connection connection = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM orderdetail WHERE orderId = ? AND itemCode = ?");
            insertStmt = connection.prepareStatement("INSERT INTO orderdetail VALUES(?,?,?,?)");

            for (OrderDetail orderDetail : orderDetails) {
                checkStmt.setString(1, orderDetail.getOrderId());
                checkStmt.setString(2, orderDetail.getItemCode());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    continue;
                }
                insertStmt.setString(1, orderDetail.getOrderId());
                insertStmt.setString(2, orderDetail.getItemCode());
                insertStmt.setInt(3, orderDetail.getQty());
                insertStmt.setDouble(4, orderDetail.getUnitPrice());
                insertStmt.addBatch();
            }

            int[] results = insertStmt.executeBatch();

            for (int result : results) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Failed to add order details", e);

        } finally {
            try {
                if (insertStmt != null) insertStmt.close();
                if (checkStmt != null) checkStmt.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
