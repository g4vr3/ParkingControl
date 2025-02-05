package dam.parkingcontrol.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

/**
 * La clase ViewManager proporciona métodos para cambiar la vista en la aplicación.
 *
 * @version 1.0
 * author g4vr3
 */
public class ViewManager {

    /**
     * Cambia la vista.
     *
     * @param node el nodo desde el cual se obtiene el Stage actual
     * @param fxml la ruta del archivo FXML de la nueva vista
     * @param width el ancho de la nueva ventana
     * @param height la altura de la nueva ventana
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void changeView(Node node, String fxml, double width, double height) throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(fxml));
        // Cargar el bundle gestionado por el LanguageManager
        loader.setResources(LanguageManager.getBundle());
        if (loader.getLocation() == null) {
            System.err.println("Error: No se pudo cargar la vista " + fxml);
            return;
        }
        Parent root = loader.load();

        // Obtener el Stage actual desde cualquier nodo de la escena actual
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root, width, height));

        // Posicionar la ventana en el centro de la pantalla
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.show();
    }
}