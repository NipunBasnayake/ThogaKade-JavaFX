package controller.customer;

import model.Customer;

import java.util.List;

public interface CustomerServices {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomer(String id);
    List<Customer> getCustomers();
    String getLastId();
    List<String> getCustomerIDs();
}
