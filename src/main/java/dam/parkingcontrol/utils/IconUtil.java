package dam.parkingcontrol.utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * La clase IconUtil proporciona métodos para gestionar el icono de la aplicación.
 *
 * @version 1.0
 */
public class IconUtil {
    /**
     * Establece el icono de la aplicación.
     *
     * @param stage el escenario al que se le establecerá el icono
     */
    public static void setAppIcon(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(IconUtil.class.getResourceAsStream("/images/icon.png"))));
    }
}