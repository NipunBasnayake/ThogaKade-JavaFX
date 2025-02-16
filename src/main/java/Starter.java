import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;
import util.AppModule;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new AppModule());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
        loader.setControllerFactory(injector::getInstance);
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
