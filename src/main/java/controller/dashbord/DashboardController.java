package controller.dashbord;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardController {

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    void CustomerFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/customer-form.fxml");

        assert resource != null;
        Parent load = FXMLLoader.load(resource);

        this.dashboardPane.getChildren().clear();
        this.dashboardPane.getChildren().setAll(load);
    }

    @FXML
    void ItemFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/item-form.fxml");

        assert resource != null;
        Parent load = FXMLLoader.load(resource);

        this.dashboardPane.getChildren().clear();
        this.dashboardPane.getChildren().setAll(load);
    }

    @FXML
    void OrderFormOnAction(ActionEvent event) {

    }

}
