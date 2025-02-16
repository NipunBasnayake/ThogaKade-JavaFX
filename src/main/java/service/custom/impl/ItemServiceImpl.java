package service.custom.impl;

import dto.Item;
import dto.OrderDetail;
import service.custom.ItemService;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {
    private static ItemServiceImpl itemController;

    public static ItemServiceImpl getInstance() {
        if (itemController == null) {
            itemController = new ItemServiceImpl();
        }
        return itemController;
    }

    @Override
    public boolean addItem(Item item) {
        String sql = "INSERT INTO item VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(sql, item.getItemCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateItem(Item item) {
        String sql = "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?";
        try {
            return CrudUtil.execute(sql, item.getDescription(), item.getUnitPrice(), item.getQtyOnHand(), item.getItemCode());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteItem(String id) {
        String sql = "DELETE FROM item WHERE code=?";
        try {
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Item searchItem(String id) {
        String sql = "SELECT * FROM item WHERE code=?";
        try {
            ResultSet res = CrudUtil.execute(sql, id);
            res.next();
            return new Item(
                    res.getString(1),
                    res.getString(2),
                    res.getDouble(3),
                    res.getInt(4)
            );
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Item> getItems() {
        String sql = "SELECT * FROM item";
        List<Item> itemsList = new ArrayList<>();
        try {
            ResultSet res = CrudUtil.execute(sql);
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
            return itemsList.isEmpty() ? null : itemsList;
        }
    }

    @Override
    public String getLastId() {
        String sql = "SELECT code FROM item ORDER BY code DESC LIMIT 1";
        try {
            ResultSet res = CrudUtil.execute(sql);
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<String> getItemCodes() {
        List<String> itemCodes = new ArrayList<>();
        getItems().forEach(item -> itemCodes.add(item.getItemCode()));
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
        String sql = "UPDATE item SET qtyOnHand=qtyOnHand-? WHERE code=?";
        try {
            return CrudUtil.execute(sql, orderDetail.getQty(), orderDetail.getItemCode());
        } catch (SQLException e) {
            return false;
        }
    }

    public int getQtyOnHand(String itemId) {
        Item item = searchItem(itemId);
        return item.getQtyOnHand();
    }
}
