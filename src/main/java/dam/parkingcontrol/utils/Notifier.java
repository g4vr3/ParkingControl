package dam.parkingcontrol.utils;

import javafx.scene.control.Alert;

/**
 * La clase Notifier proporciona métodos para mostrar alertas y notificaciones en la aplicación.
 *
 * @version 1.0
 * @author g4vr3
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
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}