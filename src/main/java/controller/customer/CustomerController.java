package controller.customer;

import model.Customer;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerServices {
    private static CustomerController customerController;

    public static CustomerController getInstance() {
        if (customerController == null) {
            customerController = new CustomerController();
        }
        return customerController;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(sql, customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getSalary());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        try {
            return CrudUtil.execute(sql, customer.getCustomerName(), customer.getAddress(), customer.getSalary(), customer.getCustomerId());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String sql = "DELETE FROM customer WHERE id=?";
        try {
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        String sql = "SELECT * FROM customer WHERE id=?";
        try {
            ResultSet res = CrudUtil.execute(sql, id);
            return new Customer(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    Double.parseDouble(res.getString(4))
            );
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Customer> getCustomers() {
        String sql = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet res = CrudUtil.execute(sql);
            while (res.next()) {
                customers.add(new Customer(
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        Double.parseDouble(res.getString(4))
                ));
            }
            return customers;
        } catch (SQLException e) {
            return customers.isEmpty() ? null : customers;
        }
    }

    @Override
    public String getLastId() {
        String sql = "SELECT id FROM customer ORDER BY id DESC LIMIT 1";
        try {
            ResultSet res = CrudUtil.execute(sql);
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<String> getCustomerIDs() {
        List<String> customerIDs = new ArrayList<>();
        getCustomers().forEach(customer -> customerIDs.add(customer.getCustomerId()));
        return customerIDs;
    }

    @Override
    public String getCustomerName(String id) {
        Customer customer = searchCustomer(id);
        return customer.getCustomerName();
    }
}
