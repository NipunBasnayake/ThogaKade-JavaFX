package dao.custom.impl;

import dao.custom.ItemDao;

import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemDaoImpl entity) {
        return false;
    }

    @Override
    public ItemDaoImpl search(String s) {
        return null;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public boolean update(ItemDaoImpl entity) {
        return false;
    }

    @Override
    public List<ItemDaoImpl> getAll() {
        return List.of();
    }
}
