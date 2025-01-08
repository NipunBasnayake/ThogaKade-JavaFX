package controller.palceorder;

import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblDate;
    public Label lblTime;
    public Label lblOrderId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAdnTime();
        generateOrderId();
    }

    private void loadDateAdnTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        lblDate.setText(formatter.format(date));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    lblTime.setText(now.format(dateFormatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void generateOrderId() {
        int num = Integer.parseInt(PlaceOrderController.getInstance().getLastOrderID().substring(1));
        num++;
        String newId = String.format("D%03d", num);
        lblOrderId.setText(newId);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }


}
