package controller.item;

import db.DBConnection;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemServices {
    private static ItemController itemController;

    public static ItemController getInstance() {
        return itemController == null ? itemController = new ItemController() : itemController;
    }

    @Override
    public boolean addItem(Item item) {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            statement.setString(1, item.getItemCode());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getUnitPrice());
            statement.setInt(4, item.getQtyOnHand());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
            statement.setString(1, item.getDescription());
            statement.setDouble(2, item.getUnitPrice());
            statement.setInt(3, item.getQtyOnHand());
            statement.setString(4, item.getItemCode());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM item WHERE code = '" + id + "'") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String id) {
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM item WHERE code = '" + id + "'");
            res.next();
            return new Item(
                    res.getString(1),
                    res.getString(2),
                    res.getDouble(3),
                    res.getInt(4)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getItems() {
        List<Item> itemsList = new ArrayList<>();
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM item");
            while (res.next()) {
                itemsList.add(new Item(
                        res.getString(1),
                        res.getString(2),
                        res.getDouble(3),
                        res.getInt(4)
                ));
            }
            return itemsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastId() {
        ResultSet res = null;
        try {
            res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT code from item ORDER BY code DESC LIMIT 1");
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
