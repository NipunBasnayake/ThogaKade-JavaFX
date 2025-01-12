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
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";

        String customerId = customer.getId();
        String customerName = customer.getName();
        String customerAddress = customer.getAddress();
        double customerSalary = customer.getSalary();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerId);
            statement.setString(2, customerName);
            statement.setString(3, customerAddress);
            statement.setDouble(4, customerSalary);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add customer", e);
        }
    }


    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customer SET name=?,address=?,salary=? WHERE id=?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

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
        String sql = "DELETE FROM customer WHERE id=?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String id) {
        String sql = "SELECT id, name, address, salary FROM customer WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    return new Customer(
                            res.getString("id"),
                            res.getString("name"),
                            res.getString("address"),
                            res.getDouble("salary")
                    );
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to search customer with ID: " + id, e);
        }
    }


    @Override
    public List<Customer> getCustomers() {
        String sql = "SELECT id, name, address, salary FROM customer";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet res = statement.executeQuery()) {

            while (res.next()) {
                customers.add(new Customer(
                        res.getString("id"),
                        res.getString("name"),
                        res.getString("address"),
                        res.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve customers", e);
        }
        return customers;
    }


    @Override
    public String getLastId() {
        String sql = "SELECT id FROM customer ORDER BY id DESC LIMIT 1";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet res = statement.executeQuery(sql)) {

            if (res.next()) {
                return res.getString("id");
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve the last customer ID", e);
        }
    }


    @Override
    public List<String> getCustomerIDs() {
        String sql = "SELECT id FROM customer";
        List<String> customerIDs = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                customerIDs.add(resultSet.getString("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerIDs;
    }


    @Override
    public String getCustomerName(String id) {
        String sql = "SELECT name FROM customer WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve customer name", e);
        }
    }



}
