package dam.parkingcontrol.service;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * La clase ViewManager proporciona métodos para cambiar la vista en la aplicación.
 *
 * @version 1.1
 */
public class ViewManager {

    /**
     * Cambia la vista de la aplicación.
     *
     * @param node       el nodo desde el cual se obtiene el Stage actual
     * @param fxml       la ruta del archivo FXML de la nueva vista
     * @param title      el título de la nueva ventana
     * @param width      el ancho de la nueva ventana
     * @param height     la altura de la nueva ventana
     * @param maximized  si la ventana debe estar maximizada
     * @param resizable  si la ventana debe ser redimensionable
     * @param showTopBar si la ventana debe tener barra superior
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void changeView(Node node, String fxml, String title, double width, double height, boolean maximized, boolean resizable, boolean showTopBar, boolean overlay) throws IOException {
        ResourceBundle bundle = LanguageManager.getBundle();
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(fxml));
        loader.setResources(bundle);

        if (loader.getLocation() == null) {
            System.err.println("Error: No se pudo cargar la vista " + fxml);
            return;
        }

        Parent root = loader.load();

        // Crea una nueva ventana en lugar de modificar la existente
        Stage newStage = new Stage();

        // Configura el estilo **antes** de mostrar la ventana
        if (!showTopBar) {
            newStage.initStyle(StageStyle.UNDECORATED);
        }

        // Configuración de la ventana
        newStage.setScene(new Scene(root, width, height));
        newStage.setTitle("ArrulloPark - " + bundle.getString(title));
        newStage.setResizable(resizable);

        // Configura el tamaño mínimo de la ventana
        newStage.setMinWidth(width);
        newStage.setMinHeight(height);

        // Obtiene las dimensiones de la pantalla y centra la ventana
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        newStage.setX((screenBounds.getWidth() - width) / 2);
        newStage.setY((screenBounds.getHeight() - height) / 2);

        if (maximized) {
            Platform.runLater(() -> newStage.setMaximized(true));
        }

        // Muestra la nueva ventana
        newStage.show();

        if (!overlay) {
            // Cierra la ventana actual
            Stage currentStage = (Stage) node.getScene().getWindow();
            currentStage.close();
        }
    }
}