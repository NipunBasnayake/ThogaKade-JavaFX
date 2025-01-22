package controller.palceorder;

import controller.item.ItemController;
import controller.order_detail.OrderDetailController;
import db.DBConnection;
import model.Order;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceOrderController implements PlaceOrderServices {
    private static PlaceOrderController placeOrderController;

    public static PlaceOrderController getInstance() {
        return placeOrderController == null ? placeOrderController = new PlaceOrderController() : placeOrderController;
    }

    @Override
    public String getLastOrderID() {
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM orders ORDER BY id DESC LIMIT 1");
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean placeOrder(Order order) {
        Connection connection = null;
        try {
            String SQL = "INSERT INTO orders VALUES (?,?,?)";

            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                PreparedStatement insertStmt = connection.prepareStatement(SQL);
                insertStmt.setString(1, order.getId());
                insertStmt.setString(2, order.getDate().toString());
                insertStmt.setString(3, order.getCustromerId());
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
                throw new RuntimeException(e);
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
