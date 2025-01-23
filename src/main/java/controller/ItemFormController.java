package controller;

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
import model.Item;
import service.custom.impl.ItemController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colQtyOnHand;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        generateItemCode();
        loadTable();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (txtDescription.getText().isEmpty() || txtUnitPrice.getText().isEmpty() || txtQtyOnHand.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill in all fields to add the item.");
            alert.show();
        }else {
            if (ItemController.getInstance().addItem((new Item(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
            )))) {
                clearFields();
                generateItemCode();
                loadTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Item Insertion Failed");
                alert.setContentText("There was an issue adding the item. Please verify the input data.");
                alert.show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isExist = false;
        for (Item item : ItemController.getInstance().getItems()) {
            if (item.getItemCode().equals(txtItemCode.getText())) {
                isExist = true;
                break;
            }
        }
        if(isExist) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.NO);
            if (buttonType == ButtonType.YES) {
                if (ItemController.getInstance().deleteItem(txtItemCode.getText())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Item Deleted");
                    alert.show();
                    clearFields();
                    generateItemCode();
                    loadTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Deletion Failed");
                    alert.setHeaderText("An error occurred while deleting the item.");
                    alert.show();
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item Not Found");
            alert.setHeaderText("Please enter a valid existing Item Code");
            alert.show();
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        boolean isExist = false;
        for (Item item : ItemController.getInstance().getItems()) {
            if (item.getItemCode().equals(txtItemCode.getText())) {
                isExist = true;
                break;
            }
        }
        if(isExist) {
            Item item = ItemController.getInstance().searchItem(txtItemCode.getText());
            if (item != null) {
                txtDescription.setText(item.getDescription());
                txtUnitPrice.setText(item.getUnitPrice().toString());
                txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Item Not Found");
                alert.setHeaderText("No item was found with the provided ID.");
                alert.show();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item Not Found");
            alert.setHeaderText("Please enter a valid existing Item Code");
            alert.show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (txtDescription.getText().isEmpty() || txtUnitPrice.getText().isEmpty() || txtQtyOnHand.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill in all fields to add the item.");
            alert.show();
        }else{
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this customer?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            ButtonType buttonType = result.orElse(ButtonType.NO);
            if (buttonType == ButtonType.YES) {
                if (ItemController.getInstance().updateItem((new Item(
                        txtItemCode.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQtyOnHand.getText())
                )))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Item Updated");
                    alert.setHeaderText("The item's details have been successfully updated.");
                    alert.show();
                    clearFields();
                    loadTable();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Update Failed");
                    alert.setHeaderText("There was an issue updating the item's details. Please try again.");
                    alert.show();
                }
            }
        }
    }

    private void generateItemCode() {
        int num = Integer.parseInt(ItemController.getInstance().getLastId().substring(1));
        num++;
        String newId = String.format("P%03d", num);
        txtItemCode.setText(newId);
    }

    private void loadTable() {
        ObservableList<Item> itemsObservableArray = FXCollections.observableArrayList();
        ItemController.getInstance().getItems().forEach(item -> {
            itemsObservableArray.add(item);
        });
        tblItem.setItems(itemsObservableArray);
    }

    private void clearFields() {
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
    }

}
