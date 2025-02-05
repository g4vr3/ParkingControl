package dam.parkingcontrol.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase ViewManager proporciona métodos para cambiar la vista en la aplicación.
 *
 * @version 1.0
 * @author g4vr3
 */
public class ViewManager {

    /**
     * Cambia la vista.
     *
     * @param node el nodo desde el cual se obtiene el Stage actual
     * @param fxml la ruta del archivo FXML de la nueva vista
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void changeView(Node node, String fxml) throws IOException {
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
        stage.setScene(new Scene(root));
        stage.show();
    }
}