package dam.parkingcontrol.utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class IconUtil {
    public static void setAppIcon(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(IconUtil.class.getResourceAsStream("/images/icon.png"))));
    }
}