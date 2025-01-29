package dao.custom;

import dao.CrudDao;
import dao.custom.impl.ItemDaoImpl;

public interface ItemDao extends CrudDao<ItemDaoImpl, String> {
}
