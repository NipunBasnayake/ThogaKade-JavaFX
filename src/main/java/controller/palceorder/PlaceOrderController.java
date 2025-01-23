package controller.palceorder;

import controller.item.ItemController;
import controller.order_detail.OrderDetailController;
import db.DBConnection;
import model.Order;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaceOrderController implements PlaceOrderServices {
    private static PlaceOrderController placeOrderController;

    public static PlaceOrderController getInstance() {
        if (placeOrderController == null) {
            placeOrderController = new PlaceOrderController();
        }
        return placeOrderController;
    }

    @Override
    public String getLastOrderID() {
        String sql = "SELECT id FROM orders ORDER BY id DESC LIMIT 1";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean placeOrder(Order order) {
        Connection connection = null;
        try {
            String sql = "INSERT INTO orders VALUES (?,?,?)";

            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                PreparedStatement insertStmt = connection.prepareStatement(sql);
                insertStmt.setString(1, order.getOrderId());
                insertStmt.setString(2, order.getOrderDate().toString());
                insertStmt.setString(3, order.getCustomerId());
                boolean isAddedToOrder = insertStmt.executeUpdate() > 0;

                if (isAddedToOrder) {
                    boolean isAddedToOrderDetails = OrderDetailController.getInstance().addOrderDetail(order.getOrderDetails());
                    if (isAddedToOrderDetails) {
                        boolean isUpdatedItem = ItemController.getInstance().updateSellItem(order.getOrderDetails());
                        if (isUpdatedItem) {
                            connection.commit();
                            return true;
                        }
                    }
                }
                connection.rollback();
                return false;
            } catch (SQLException e) {
                return false;
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
