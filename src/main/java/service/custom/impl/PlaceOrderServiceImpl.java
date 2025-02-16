package service.custom.impl;

import db.DBConnection;
import dto.Order;
import service.custom.PlaceOrderService;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderServiceImpl implements PlaceOrderService {
    private static PlaceOrderServiceImpl placeOrderController;

    public static PlaceOrderServiceImpl getInstance() {
        if (placeOrderController == null) {
            placeOrderController = new PlaceOrderServiceImpl();
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
    public List<Order> getOrders() {
        String sql = "SELECT * FROM orders";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getString(1),
                        LocalDate.parse(resultSet.getString(2)),
                        resultSet.getString(3),
                        null
                ));
            }
            return orders;
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
                    boolean isAddedToOrderDetails = OrderDetailServiceImpl.getInstance().addOrderDetail(order.getOrderDetails());
                    if (isAddedToOrderDetails) {
                        boolean isUpdatedItem = ItemServiceImpl.getInstance().updateSellItem(order.getOrderDetails());
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
