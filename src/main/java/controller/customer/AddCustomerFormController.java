package controller.customer;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

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
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed to Add Customer");
                alert.setContentText("There was an issue adding the customer. Please verify the input data.");
                alert.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtID.setText(generateCustomerId());
        txtID.setEditable(false);
    }

    private String generateCustomerId() {
        int num = Integer.parseInt(CustomerController.getInstance().getLastId().substring(1));
        num++;
        String newId = String.format("C%03d", num);
        return newId;
    }

    private void clearFields() {
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }


}
