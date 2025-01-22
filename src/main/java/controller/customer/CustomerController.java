package controller.customer;

import model.Customer;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerServices {
    private static CustomerController customerController;

    public static CustomerController getInstance() {
        return customerController == null ? customerController = new CustomerController() : customerController;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String SQL = "INSERT INTO customer VALUES (?,?,?,?)";
        try {
            return CrudUtil.execute(SQL, customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        try {
            return CrudUtil.execute(SQL, customer.getName(), customer.getAddress(), customer.getSalary(), customer.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String SQL = "DELETE FROM customer WHERE id=?";
        try {
            return CrudUtil.execute(SQL, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        String SQL = "SELECT * FROM customer WHERE id=?";
        try {
            ResultSet res = CrudUtil.execute(SQL, id);
            return new Customer(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    Double.parseDouble(res.getString(4))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getCustomers() {
        String SQL = "SELECT * FROM customer";
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet res = CrudUtil.execute(SQL);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastId() {
        String SQL = "SELECT id FROM customer ORDER BY id DESC LIMIT 1";
        try {
            ResultSet res = CrudUtil.execute(SQL);
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getCustomerIDs() {
        List<String> customerIDs = new ArrayList<>();
        getCustomers().forEach(customer -> {
            customerIDs.add(customer.getId());
        });
        return customerIDs;
    }

    @Override
    public String getCustomerName(String id) {
        Customer customer = searchCustomer(id);
        return customer.getName();
    }
}
