import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

public class Starter extends Application {
    public static void main(String[] args) {
        String key = "12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginandsignup-form.fxml"))));
        stage.show();
    }

}
