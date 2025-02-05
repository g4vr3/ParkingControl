package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.LanguageManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
        loginButton.setOnAction(event -> handleLogin());
    }

    @FXML
    private void setUI() {
        // Obtiene el bundle gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Añade a la lista de idiomas los idiomas soportados
        languageComboBox.setItems(LanguageManager.getSupportedLanguages());

        // Establece por defecto el idioma del sistema si lo soporta
        languageComboBox.setValue(LanguageManager.getSystemLanguage());

        // Listener para cambios en el idioma (selección de idioma en ComboBox)
        languageComboBox.valueProperty().addListener((_, _, newValue) -> {
            // Carga el bundle con el nuevo idioma
            LanguageManager.loadLanguage(newValue);
            // Actualiza el idioma de la UI
            updateUIlanguage();
        });
    }

    private void updateUIlanguage() {
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
            showAlert(AlertType.INFORMATION, "Inicio de Sesión Exitoso", "Bienvenido!");
            try {
                changeScene("/dam/parkingcontrol/parking-view.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            showAlert(AlertType.ERROR, "Error de Inicio de Sesión", "Usuario o contraseña incorrectos.");
        }
    }

    //Método para validar credenciales del Usuario
    private boolean validateCredentials(String username, String password) {
        return username.equals("admin") && password.equals("admin123");
    }

    //Método para crear alertas con mensaje
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void changeScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        if (loader.getLocation() == null) {
            System.err.println("Error: No se pudo cargar la vista " + fxml);
            return;
        }
        Parent root = loader.load();

        //Nuevo Escenario
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

