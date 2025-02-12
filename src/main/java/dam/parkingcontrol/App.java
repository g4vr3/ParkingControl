package dam.parkingcontrol;

import dam.parkingcontrol.database.DatabaseInitializer;
import dam.parkingcontrol.service.LanguageManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * La clase App es el punto de entrada de la aplicación y administra la interfaz gráfica.
 *
 * @version 1.0.2
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Configurar el soporte de idioma
        LanguageManager.setLanguageSupport();
        ResourceBundle bundle = LanguageManager.getBundle();

        // Cargar la vista de inicio de sesión
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/login-view.fxml"));
        fxmlLoader.setResources(bundle);
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);

        // Añadir la hoja de estilos
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());

        // Configurar el escenario (Stage)
        stage.setTitle("ArrulloPark - " + bundle.getString("login_title_text"));
        stage.setScene(scene);

        stage.setMinWidth(500);
        stage.setMinHeight(500);

        // Añadir handler de eventos para la solicitud de cierre de la ventana
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();

        // Centrar la ventana en la pantalla
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // Inicializar la base de datos
        DatabaseInitializer.createTables();
        DatabaseInitializer.insertSampleVehicles(20);
        // Lanzar la aplicación
        launch(args);
    }
}