package service.custom.impl;

import dao.DaoFactory;
import dao.custom.CustomerDao;
import dto.Customer;
import entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import service.custom.CustomerService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private static CustomerServiceImpl customerController;
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    public static CustomerServiceImpl getInstance() {
        if (customerController == null) {
            customerController = new CustomerServiceImpl();
        }
        return customerController;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.save(entity);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.update(entity);
    }

    @Override
    public boolean deleteCustomer(String id) {
        return customerDao.delete(id);
    }

    @Override
    public Customer searchCustomer(String id) {
        CustomerEntity entity = customerDao.search(id);
        return new ModelMapper().map(entity, Customer.class);
    }

    @Override
    public List<Customer> getCustomers() {
        List<CustomerEntity> customerEntities = customerDao.getAll();
        List<Customer> customers = new ArrayList<>();

        ModelMapper mapper = new ModelMapper();
        for (CustomerEntity entity : customerEntities) {
            customers.add(mapper.map(entity, Customer.class));
        }
        return customers;
    }

    @Override
    public String getLastId() {
        List<CustomerEntity> customers = customerDao.getAll();
        if (customers.isEmpty()) {
            return null;
        }
        return customers.get(customers.size() - 1).getCustomerId();
    }

    @Override
    public List<String> getCustomerIDs() {
        List<CustomerEntity> customerEntities = customerDao.getAll();
        List<String> customerIDs = new ArrayList<>();

        for (CustomerEntity entity : customerEntities) {
            customerIDs.add(entity.getCustomerId());
        }
        return customerIDs;
    }

    @Override
    public String getCustomerName(String id) {
        CustomerEntity entity = customerDao.search(id);
        return (entity != null) ? entity.getCustomerName() : null;
    }

}
