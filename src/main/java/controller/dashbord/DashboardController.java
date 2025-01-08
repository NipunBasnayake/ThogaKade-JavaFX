package controller.dashbord;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;

public class DashboardController {
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab placeOrderPane;
    @FXML
    public Tab customerPane;
    @FXML
    public Tab itemPane;

    public void placeOrderOnSelectionChanged() throws IOException {
        URL resource = this.getClass().getResource("/view/placeorder-form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.placeOrderPane.setContent(load);
    }

    public void customerOnSelectionChanged() throws IOException {
        URL resource = this.getClass().getResource("/view/customer-form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.customerPane.setContent(load);
    }

    public void itemOnSelectionChanged() throws IOException {
        URL resource = this.getClass().getResource("/view/item-form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.itemPane.setContent(load);
    }
}
