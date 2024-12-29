package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableView tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        txtID.setText(generateCustomerId());
        loadTable();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtSalary.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fields are empty");
            alert.setContentText("Please fill all the fields");
            alert.show();
            return;
        }else {
            Customer customer = new Customer(
                    txtID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            );
            boolean isInserted = insertTODatabase(customer);
            if (isInserted) {
                loadTable();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer not inserted");
                alert.setContentText("Please check the fields");
                alert.show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = deleteByID(txtID.getText());
        if (isDeleted) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer deleted");
            alert.show();
            loadTable();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not deleted");
            alert.show();
            loadTable();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = searchByID(txtID.getText());
        if (customer != null) {
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(customer.getSalary().toString());
        }else{
            System.out.println("Customer not found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated = updateCustomer(new Customer(
                txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        ));
        if (isUpdated) {
            System.out.println("Customer updated");
        }else {
            System.out.println("Customer not updated");
        }
    }

    private String generateCustomerId(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT id from customer ORDER BY id DESC LIMIT 1");
            res.next();
            String lastId = res.getString(1);
            int num = Integer.parseInt(lastId.substring(1));
            num++;
            String newId = String.format("C%03d", num);
            return newId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean updateCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("update customer set name=?,address=?,salary=? where id=?");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setDouble(3, customer.getSalary());
            statement.setString(4, customer.getId());
            return statement.executeUpdate() >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean deleteByID(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            int res =  statement.executeUpdate("DELETE FROM customer WHERE id = '" + id + "'");
            return res > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean insertTODatabase(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into customer values(?,?,?,?)");
            statement.setString(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setDouble(4, customer.getSalary());
            return statement.executeUpdate() >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Customer searchByID(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * FROM customer where id = '" + id + "'");
            res.next();
            Customer customer = new Customer(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    Double.parseDouble(res.getString(4))
            );
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTable() {
        List<Customer> customerList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * FROM customer");
            while (res.next()) {
                customerList.add(new Customer(
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        Double.parseDouble(res.getString(4))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Customer> customerObservableArray = FXCollections.observableArrayList();
        customerList.forEach(customer -> {
            customerObservableArray.add(customer);
        });
        tblCustomer.setItems(customerObservableArray);
    }

}
