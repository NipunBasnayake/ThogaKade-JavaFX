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

public class PlaceOrderController implements PlaceOrderServices{
    private static PlaceOrderController placeOrderController;

    public static PlaceOrderController getInstance() {
        return placeOrderController==null?placeOrderController=new PlaceOrderController():placeOrderController;
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
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (addOrder(connection, order)) {
                if (OrderDetailController.getInstance().addOrderDetail(order.getOrderDetails())) {
                    if (ItemController.getInstance().updateItems(order.getOrderDetails())) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean addOrder(Connection connection, Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getId());
            statement.setString(2, convertDateFormat(order.getDate()));
            statement.setString(3, order.getCustromerId());
            return statement.executeUpdate() > 0;
        }
    }



    public static String convertDateFormat(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



}
