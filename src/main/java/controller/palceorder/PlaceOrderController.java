package controller.palceorder;

import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?)");
            boolean isOrderAdded = statement.executeUpdate() > 0;
            if (isOrderAdded) {
                boolean idOrderDetailsAdded =
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


}
