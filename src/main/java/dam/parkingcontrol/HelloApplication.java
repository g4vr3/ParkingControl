package dam.parkingcontrol;

import dam.parkingcontrol.database.DatabaseInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/parking-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
        stage.setTitle("Parking Control");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Inicializar la base de datos
        DatabaseInitializer.createTables();
        launch(args);
    }
}