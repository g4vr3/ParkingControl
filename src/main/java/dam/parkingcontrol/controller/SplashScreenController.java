package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.ViewManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreenController {

    @FXML
    private ImageView gifView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {
        // Configura la duración de la pantalla de presentación
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(4), new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.setCycleCount(1);

        timeline.setOnFinished(event -> {
            // Cierra la pantalla de presentación
            Stage stage = (Stage) gifView.getScene().getWindow();
            stage.close();

            // Muestra la vista principal
            Platform.runLater(() -> {
                try {
                    ViewManager.changeView(gifView, "/views/parking-view.fxml", "parking_view_title", 1200, 800, true, true, true, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        timeline.play();
    }
}