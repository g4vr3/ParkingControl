package dam.parkingcontrol.service;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * La clase ViewManager proporciona métodos para cambiar la vista en la aplicación.
 *
 * @version 1.0
 */
public class ViewManager {

    /**
     * Cambia la vista.
     *
     * @param node      el nodo desde el cual se obtiene el Stage actual
     * @param fxml      la ruta del archivo FXML de la nueva vista
     * @param width     el ancho de la nueva ventana
     * @param height    la altura de la nueva ventana
     * @param maximized si la ventana se maximiza
     * @param resizable si la ventana se puede redimensionar
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void changeView(Node node, String fxml, String title, double width, double height, boolean maximized, boolean resizable) throws IOException {
        ResourceBundle bundle = LanguageManager.getBundle();
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(fxml));
        loader.setResources(bundle);
        if (loader.getLocation() == null) {
            System.err.println("Error: No se pudo cargar la vista " + fxml);
            return;
        }
        Parent root = loader.load();

        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root, width, height));

        stage.setTitle("ArrulloPark - " + title);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        if (maximized) {
            Platform.runLater(() -> stage.setMaximized(true));

            stage.setResizable(resizable);
            stage.show();
        }
    }
}