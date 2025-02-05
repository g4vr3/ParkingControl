package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.LanguageManager;
import dam.parkingcontrol.service.ViewManager;
import dam.parkingcontrol.utils.Notifier;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPass;

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private VBox vboxCenter;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        setUI();
        // Acción del botón de login
        loginButton.setOnAction(_ -> handleLogin());
        applyFloatingEffect();
    }

    private void applyFloatingEffect() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), vboxCenter);
        transition.setByY(-10);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    @FXML
    private void setUI() {
        // Obtiene el bundle gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Añade a la lista de idiomas los idiomas soportados
        languageComboBox.setItems(LanguageManager.getSupportedLanguages());

        // Establece por defecto el idioma del sistema si lo soporta
        languageComboBox.setValue(LanguageManager.getLanguageNameFromCode(LanguageManager.getSystemLanguage()));

        // Listener para cambios en el idioma (selección de idioma en ComboBox)
        languageComboBox.valueProperty().addListener((_, _, newValue) -> {
            // Carga el bundle con el nuevo idioma
            LanguageManager.loadLanguage(LanguageManager.getLanguageCodeFromName(newValue));
            // Actualiza el idioma de la UI
            updateUILanguage();
        });
    }

    private void updateUILanguage() {
        // Obtiene el bundle actual, gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Establece los textos de la UI según el bundle
        tfUsername.setPromptText(bundle.getString("email_username_prompt"));
        pfPass.setPromptText(bundle.getString("password_prompt"));
        loginButton.setText(bundle.getString("login_button_text"));
    }

    private void handleLogin() {
        String username = tfUsername.getText();
        String password = pfPass.getText();

        if (validateCredentials(username, password)) {
            try {
                ViewManager.changeView(loginButton, "/views/parking-view.fxml", 900, 700);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            Notifier.showAlert(AlertType.ERROR, "Error", "Inicio de sesión incorrecto", "Usuario o contraseña incorrectos.");
        }
    }

    // Validar credenciales de usuario
    private boolean validateCredentials(String username, String password) {
        return username.equals("admin") || username.equals("admin@arrullopark.com") && password.equals("admin123");
    }
}