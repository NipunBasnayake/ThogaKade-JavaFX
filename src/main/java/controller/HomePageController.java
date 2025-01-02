package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {

    @FXML
    void btnCustomer(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customer-form.fxml"))));
        stage.setTitle("Customer Management");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnItem(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item-form.fxml"))));
        stage.setTitle("Item Management");
        stage.setResizable(false);
        stage.show();
    }

}
