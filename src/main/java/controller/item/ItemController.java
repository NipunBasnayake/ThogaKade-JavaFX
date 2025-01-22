package controller.item;

import db.DBConnection;
import model.Item;
import model.OrderDetail;
import util.CrudUtil;

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
        String SQL = "INSERT INTO item VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(SQL, item.getItemCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        String SQL = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        try {
            return CrudUtil.execute(SQL, item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) {
        String SQL = "DELETE FROM item WHERE code=?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String id) {
        String SQL = "SELECT * FROM item WHERE code=?";
        try {
            ResultSet res = CrudUtil.execute(SQL, id);
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
        String SQL = "SELECT * FROM item";
        List<Item> itemsList = new ArrayList<>();
        try {
            ResultSet res = CrudUtil.execute(SQL);
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
        String SQL = "SELECT code from item ORDER BY code DESC LIMIT 1";
        try {
            ResultSet res = CrudUtil.execute(SQL);
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getItemCodes() {
        List<String> itemCodes = new ArrayList<>();
        getItems().forEach(item -> {
            itemCodes.add(item.getItemCode());
        });
        return itemCodes;
    }

    @Override
    public boolean updateSellItem(ArrayList<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isItemUpdate = updateSellItem(orderDetail);
            if (!isItemUpdate) {
                return false;
            }
        }
        return true;
    }

    public boolean updateSellItem(OrderDetail orderDetail) {
        String SQL = "UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?";
        try {
            return CrudUtil.execute(SQL, orderDetail.getQty(), orderDetail.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getQtyOnHand(String itemId) {
        Item item = searchItem(itemId);
        return item.getQtyOnHand();
    }
}
