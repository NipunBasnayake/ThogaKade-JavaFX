package controller.customer;

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
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtSalary.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fields are empty");
            alert.setContentText("Please fill all the fields");
            alert.show();
            return;
        } else {
            if (CustomerController.getInstance().addCustomer(new Customer(
                    txtID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText()))))
            {
                loadTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer not inserted");
                alert.setContentText("Please check the fields");
                alert.show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (CustomerController.getInstance().deleteCustomer(txtID.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer deleted");
            alert.show();
            loadTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not deleted");
            alert.show();
            loadTable();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = CustomerController.getInstance().searchCustomer(txtID.getText());
        if (customer != null) {
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(customer.getSalary().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not found");
            alert.show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (CustomerController.getInstance().updateCustomer(new Customer(
                txtID.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        ))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer updated");
            alert.show();
            loadTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer not updated");
            alert.show();
        }
    }

    private String generateCustomerId() {
            int num = Integer.parseInt(CustomerController.getInstance().getLastId().substring(1));
            num++;
            String newId = String.format("C%03d", num);
            return newId;
    }

    private void loadTable() {
        ObservableList<Customer> customerObservableArray = FXCollections.observableArrayList();
        CustomerController.getInstance().getCustomers().forEach(customer -> {
            customerObservableArray.add(customer);
        });
        tblCustomer.setItems(customerObservableArray);
    }

}
