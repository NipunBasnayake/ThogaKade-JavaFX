package dao.custom.impl;


import dao.custom.CustomerDao;
import entity.CustomerEntity;
import org.hibernate.Session;
import util.HibernateConfig;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public boolean save(CustomerEntity entity) {
        Session session = HibernateConfig.getSession();
        session.beginTransaction();

        session.persist(entity);
        session.beginTransaction().commit();
        session.close();

        return true;
    }

    @Override
    public CustomerEntity search(String s) {
        System.out.println("Id : " + s);
        return null;
    }

    @Override
    public boolean delete(String s) {
        System.out.println("Id : " + s);
        return false;
    }

    @Override
    public boolean update(CustomerEntity entity) {
        System.out.println("Entity : " + entity);
        return false;
    }

    @Override
    public List<CustomerEntity> getAll() {
        return List.of();
    }
}
