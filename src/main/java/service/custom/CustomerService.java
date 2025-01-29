package service.custom;

import model.Customer;
import service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomer(String id);
    List<Customer> getCustomers();
    String getLastId();
    List<String> getCustomerIDs();
    String getCustomerName(String id);
}
