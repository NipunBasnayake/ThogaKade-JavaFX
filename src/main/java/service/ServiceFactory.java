package service;

import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    private ServiceFactory() {}

    public <T extends SuperService> T getService(ServiceType serviceType) {
        switch (serviceType) {
            case CUSTOMER: return (T) CustomerServiceImpl.getInstance();
            case ITEM: return (T) ItemServiceImpl.getInstance();
            case LOGIN: return (T) LoginSignupServiceImpl.getInstance();
            case ORDERDETAIL: return (T) OrderDetailServiceImpl.getInstance();
            case PLACEORDER: return (T) PlaceOrderServiceImpl.getInstance();
        }
        return null;
    }
}
