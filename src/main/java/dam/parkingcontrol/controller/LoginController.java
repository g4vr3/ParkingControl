package dam.parkingcontrol.controller;

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
import java.util.Locale;
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
    private Locale locale;

    @FXML
    protected void parking() {
        try {
            changeScene("src/main/resources/dam/parkingcontrol/parking-view.fxml");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void initialize() {
        //Cargar idioma español por defecto
        setLanguage("es");

        languageComboBox.setOnAction(event -> handleLanguageChange());

        // Acción del botón de login
        loginButton.setOnAction(event -> handleLogin());

    }

    //Método para cambiar el idioma
    private void handleLanguageChange() {
        String selectedLanguage = languageComboBox.getSelectionModel().getSelectedItem();
        if ("Español".equals(selectedLanguage) || "Spanish".equals(selectedLanguage) || "Espagnol".equals(selectedLanguage)) {
            setLanguage("es");
        } else if ("Inglés".equals(selectedLanguage) || "English".equals(selectedLanguage)|| "Anglais".equals(selectedLanguage)) {
            setLanguage("en");
        } else if ("Francés".equals(selectedLanguage) || "French".equals(selectedLanguage)|| "Francais".equals(selectedLanguage)) {
            setLanguage("fr");
        }
    }


    private void setLanguage(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("MessagesBundle", locale);

        // Actualizar textos de los elementos de la UI
        tfUsername.setPromptText(bundle.getString("tfUsername"));
        tfPass.setPromptText(bundle.getString("tfPass"));
        loginButton.setText(bundle.getString("loginButton"));

        // Actualizar nombres de los idiomas en el ComboBox según el idioma seleccionado
        if (lang.equals("es")) {
            languageComboBox.getItems().setAll("Español", "Inglés", "Francés");
        } else if (lang.equals("en")) {
            languageComboBox.getItems().setAll("Spanish", "English", "French");
        } else if (lang.equals("fr")) {
            languageComboBox.getItems().setAll("Espagnol", "Anglais", "Francais");
        }

        // Seleccionar el idioma actual en el ComboBox
        languageComboBox.getSelectionModel().select(bundle.getString("languageSelected"));
    }

    private void handleLogin()  {
        String username = tfUsername.getText();
        String password = tfPass.getText();

        if (validateCredentials(username, password)) {
            showAlert(AlertType.INFORMATION, "Inicio de Sesión Exitoso", "Bienvenido!");
            parking();
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
        Parent root = loader.load();

        //Nuevo Escenario
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

