package controller.palceorder;

import db.DBConnection;

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




}
