package dam.parkingcontrol.utils;

import dam.parkingcontrol.service.LanguageManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

/**
 * La clase Notifier proporciona métodos para mostrar alertas y notificaciones en la aplicación.
 *
 * @author g4vr3
 * @version 1.0
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
}