package controller.item;

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
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
        if (ItemController.getInstance().addItem((new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        )))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Inserted");
            alert.show();
            loadTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item Insertion Failed");
            alert.show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (ItemController.getInstance().deleteItem(txtItemCode.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Deleted");
            alert.show();
            loadTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item Deletion Failed");
            alert.show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = ItemController.getInstance().searchItem(txtItemCode.getText());
        if (item != null) {
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(item.getUnitPrice().toString());
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item Search Failed");
            alert.show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (ItemController.getInstance().updateItem((new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        )))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Item Updated");
            alert.show();
            loadTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item Update Failed");
            alert.show();
        }
    }

    private void loadTable() {
        ObservableList<Item> itemsObservableArray = FXCollections.observableArrayList();
        ItemController.getInstance().getItems().forEach(item -> {
            itemsObservableArray.add(item);
        });
        tblItem.setItems(itemsObservableArray);
    }

    private void generateItemCode() {
        int num = Integer.parseInt(ItemController.getInstance().getLastId().substring(1));
        num++;
        String newId = String.format("P%03d", num);
        txtItemCode.setText(newId);
    }

}
