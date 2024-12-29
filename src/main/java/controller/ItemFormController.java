package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        loadTable();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        boolean isInserted =  insertItems(new Item(
                    txtItemCode.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
        ));
        if (isInserted) {
            System.out.println("Inserted successfully");
            loadTable();
        }else{
            System.out.println("Insertion failed");
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean isDeleted = deleteItem(txtItemCode.getText());
        if (isDeleted) {
            System.out.println("Deleted Successfully");
            loadTable();
        }else{
            System.out.println("Deletion failed");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = searchByItemCode(txtItemCode.getText());
        if (item != null) {
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(item.getUnitPrice().toString());
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        }else {
            System.out.println("Item not found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isUpdated =  updateItem(new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        ));
        if (isUpdated) {
            System.out.println("Updated Successfully");
            loadTable();
        }else{
            System.out.println("Update failed");
        }
    }

    private boolean deleteItem(String itemCode) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            int res = statement.executeUpdate("DELETE FROM items WHERE itemCode = '" + itemCode + "'");
            return res > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean updateItem(Item items) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?"
            );
            statement.setString(1, items.getDescription());
            statement.setDouble(2, items.getUnitPrice());
            statement.setInt(3, items.getQtyOnHand());
            statement.setString(4, items.getItemCode());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean insertItems(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            statement.setString(1, item.getItemCode());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getUnitPrice());
            statement.setInt(4, item.getQtyOnHand());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Item searchByItemCode(String itemCode) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM item WHERE code = '" + itemCode + "'");
            res.next();
            Item item = new Item(
                    res.getString(1),
                    res.getString(2),
                    Double.parseDouble(res.getString(3)),
                    Integer.parseInt(res.getString(4))
            );
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTable() {
        List<Item> itemList = new ArrayList<>();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * FROM item");
            while (res.next()) {
                itemList.add(new Item(
                        res.getNString(1),
                        res.getString(2),
                        Double.parseDouble(res.getString(3)),
                        Integer.parseInt(res.getString(4))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Item> itemsObservableArray = FXCollections.observableArrayList();
        itemList.forEach(item -> {
            itemsObservableArray.add(item);
        });
        tblItem.setItems(itemsObservableArray);
    }

}
