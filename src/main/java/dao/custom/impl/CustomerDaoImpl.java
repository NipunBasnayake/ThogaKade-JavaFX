package dao.custom.impl;

import dao.custom.CustomerDao;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerDaoImpl entity) {
        return false;
    }

    @Override
    public CustomerDaoImpl search(String s) {
        return null;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public boolean update(CustomerDaoImpl entity) {
        return false;
    }

    @Override
    public List<CustomerDaoImpl> getAll() {
        return List.of();
    }
}
