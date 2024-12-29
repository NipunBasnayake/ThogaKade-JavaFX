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

    List<Customer> customerList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        txtID.setText(generateCustomerId());
        System.out.println(generateCustomerId());
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

            customerList.add(customer);
            loadTable();
            insertTODatabase(customer);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        deleteByID(txtID.getText());
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        searchByID(txtID.getText());
        loadTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );
        updateCustomer(customer);
        loadTable();
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

    private void updateCustomer(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement("update customer set name=?,address=?,salary=? where id=?");
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getAddress());
            statement.setDouble(3, customer.getSalary());
            statement.setString(4, customer.getId());

            if(statement.executeUpdate() >0){
                System.out.println("Customer has been updated");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteByID(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            int res =  statement.executeUpdate("DELETE FROM customer WHERE id = '" + id + "'");
            if (res > 0) {
                System.out.println("Customer has been deleted");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertTODatabase(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement statement = connection.prepareStatement("insert into customer values(?,?,?,?)");
            statement.setString(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setDouble(4, customer.getSalary());

            if(statement.executeUpdate() >0){
                System.out.println("Customer inserted successfully");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchByID(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * FROM customer where id = '" + id + "'");
            res.next();

            txtName.setText(res.getString(2));
            txtAddress.setText(res.getString(3));
            txtSalary.setText(res.getString(4));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTable() {
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