package dao.custom;

import dao.CrudDao;
import dao.custom.impl.CustomerDaoImpl;

public interface CustomerDao extends CrudDao<CustomerDaoImpl, String> {
}
