package dao.custom;

import dao.CrudDao;
import dao.custom.impl.CustomerDaoImpl;
import entity.CustomerEntity;

public interface CustomerDao extends CrudDao<CustomerEntity, String> {
}
