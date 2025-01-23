package controller.customer;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.Optional;
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
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill in all fields to add the customer.");
            alert.show();
        } else {
            if (CustomerController.getInstance().addCustomer(new Customer(
                    txtID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ))) {
                clearFields();
                generateCustomerId();
                loadTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed to Add Customer");
                alert.setContentText("There was an issue adding the customer. Please verify the input data.");
                alert.show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isExist = false;
        for (Customer customer : CustomerController.getInstance().getCustomers()) {
            if (customer.getCustomerId().equals(txtID.getText())) {
                isExist = true;
                break;
            }
        }
        if (isExist) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.NO);
            if (buttonType == ButtonType.YES) {
                if (CustomerController.getInstance().deleteCustomer(txtID.getText())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Customer Deleted");
                    alert.setHeaderText("Customer Successfully Deleted");
                    alert.show();
                    clearFields();
                    generateCustomerId();
                    loadTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Deletion Failed");
                    alert.setHeaderText("An error occurred while deleting the customer.");
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Not Found");
            alert.setHeaderText("Please enter a valid existing Customer ID.");
            alert.show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        boolean isExist = false;
        for (Customer customer : CustomerController.getInstance().getCustomers()) {
            if (customer.getCustomerId().equals(txtID.getText())) {
                isExist = true;
                break;
            }
        }
        if (isExist) {
            Customer customer = CustomerController.getInstance().searchCustomer(txtID.getText());
            if (customer != null) {
                txtName.setText(customer.getCustomerName());
                txtAddress.setText(customer.getAddress());
                txtSalary.setText(customer.getSalary().toString());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Customer Not Found");
                alert.setHeaderText("No customer was found with the provided ID.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Not Found");
            alert.setHeaderText("Please enter a valid existing Customer ID.");
            alert.show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtSalary.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill in all fields to update the customer.");
            alert.show();
        } else {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this customer?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.NO);
            if (buttonType == ButtonType.YES) {
                if (CustomerController.getInstance().updateCustomer(new Customer(
                        txtID.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                ))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Customer Updated");
                    alert.setHeaderText("The customer's details have been successfully updated.");
                    alert.show();
                    clearFields();
                    loadTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Update Failed");
                    alert.setHeaderText("There was an issue updating the customer's details. Please try again.");
                    alert.show();
                }
            }
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

    private void clearFields() {
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }
}
