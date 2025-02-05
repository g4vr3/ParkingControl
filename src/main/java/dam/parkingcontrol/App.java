package dam.parkingcontrol;

import dam.parkingcontrol.database.DatabaseInitializer;
import dam.parkingcontrol.service.LanguageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        LanguageManager.setLanguageSupport();
        ResourceBundle bundle = LanguageManager.getBundle();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/login-view.fxml"));
        fxmlLoader.setResources(bundle);
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
        stage.setTitle("ArrulloPark - " + bundle.getString("login_title_text"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Inicializar la base de datos
        DatabaseInitializer.createTables();
        launch(args);
    }
}