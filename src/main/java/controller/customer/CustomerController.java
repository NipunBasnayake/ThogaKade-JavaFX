package controller.customer;

import db.DBConnection;
import model.Customer;

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
        PreparedStatement statement = null;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            statement.setString(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setDouble(4, customer.getSalary());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        PreparedStatement statement = null;
        try {
            statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET name=?,address=?,salary=? WHERE id=?");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setDouble(3, customer.getSalary());
            statement.setString(4, customer.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM customer WHERE id = '" + id + "'") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        try {
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM customer WHERE id = '" + id + "'");
            res.next();
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
        Connection connection = null;
        try {
            List<Customer> customers = new ArrayList<>();
            ResultSet res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM customer");
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
        ResultSet res = null;
        try {
            res = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1");
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
