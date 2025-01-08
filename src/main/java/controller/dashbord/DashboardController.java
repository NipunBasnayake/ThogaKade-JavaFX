package controller.dashbord;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab placeOrderPane;
    @FXML
    public Tab customerPane;
    @FXML
    public Tab itemPane;

    @FXML
    private AnchorPane dashboardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void placeOrderOnSelectionChanged(Event event) {

    }

    public void customerOnSelectionChanged(Event event) throws IOException {
        URL resource = this.getClass().getResource("/view/customer-form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.customerPane.setContent(load);
    }

    public void itemOnSelectionChanged(Event event) throws IOException {
        URL resource = this.getClass().getResource("/view/item-form.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        this.itemPane.setContent(load);
    }
}
