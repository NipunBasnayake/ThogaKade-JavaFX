package dao;

import dao.custom.impl.*;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }
    private DaoFactory() {}

    public <T extends SuperDao> T getDao(DaoType daoType) {
        switch (daoType) {
            case CUSTOMER: return (T) new CustomerDaoImpl();
            case ITEM: return (T) new ItemDaoImpl();
            case LOGIN: return (T) new LoginSignupDaoImpl();
            case ORDERDETAIL: return (T) new OrderDetailDaoImpl();
            case PLACEORDER: return (T) new PlaceOrderDaoImpl();
        }
        return null;
    }


}
