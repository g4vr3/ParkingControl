package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.LanguageManager;
import dam.parkingcontrol.service.ViewManager;
import dam.parkingcontrol.utils.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPass;

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<String> languageComboBox;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        setUI();
        // Acción del botón de login
        loginButton.setOnAction(_ -> handleLogin());
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
        tfPass.setPromptText(bundle.getString("password_prompt"));
        loginButton.setText(bundle.getString("login_button_text"));
    }

    private void handleLogin() {
        String username = tfUsername.getText();
        String password = tfPass.getText();

        if (validateCredentials(username, password)) {
            try {
                ViewManager.changeView(loginButton, "/views/parking-view.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            Notifier.showAlert(AlertType.ERROR, "Error", "Inicio de sesión incorrecto", "Usuario o contraseña incorrectos.");
        }
    }

    // Validar credenciales de usuario
    private boolean validateCredentials(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }
}

