package dam.parkingcontrol.utils;

import dam.parkingcontrol.service.LanguageManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.ResourceBundle;

/**
 * La clase Notifier proporciona métodos para mostrar alertas y notificaciones en la aplicación.
 *
 * @version 2.0
 */
public class Notifier {

    /**
     * Muestra una alerta.
     *
     * @param alertType   el tipo de alerta
     * @param title       el título
     * @param headerText  el texto del encabezado
     * @param contentText el texto del contenido
     */
    public static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        ResourceBundle bundle = LanguageManager.getBundle();
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(bundle.getString(title));
            alert.setHeaderText(bundle.getString(headerText));
            alert.setContentText(bundle.getString(contentText));
            alert.show();
        });
    }

    /**
     * Muestra un tooltip en la pantalla.
     *
     * @param node       el nodo desde el cual se obtiene la ventana actual
     * @param messageKey la clave del mensaje en el ResourceBundle
     */
    public static void showTooltip(Node node, String messageKey) {
        ResourceBundle bundle = LanguageManager.getBundle();
        Platform.runLater(() -> {
            String message = bundle.getString(messageKey);
            Tooltip tooltip = new Tooltip(message);
            tooltip.setAutoHide(true);

            Window window = node.getScene().getWindow();
            tooltip.show(window);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> tooltip.hide());
            pause.play();
        });
    }
}