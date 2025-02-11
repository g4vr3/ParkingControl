package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.LanguageManager;
import dam.parkingcontrol.service.ReportManager;
import dam.parkingcontrol.service.ViewManager;
import dam.parkingcontrol.utils.IconUtil;
import dam.parkingcontrol.utils.Notifier;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controlador para el inicio de sesión.
 * Gestiona la autenticación del usuario, la selección de idioma,
 * la navegación a la vista principal y la ayuda.
 *
 * @version 1.0.2
 */

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

    @FXML
    private Hyperlink helpLink;

    private ResourceBundle bundle;

    private int failedAttempts = 0; // Contador de intentos de inicio de sesión fallidos


    /**
     * Inicialización de la interfaz y componentes.
     * Configura los elementos de la UI y asigna eventos a los componentes.
     */
    @FXML
    public void initialize() {
        setUI();
        // Acción del botón de login
        loginButton.setOnAction(_ -> handleLogin());
        applyFloatingEffect();

        // Acción del hyperlink
        helpLink.setOnAction(_ -> {
            try {
                openWebPage(Objects.requireNonNull(getClass().getResource("/help-web/index.html")).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        IconUtil.setAppIcon((Stage) loginButton.getScene().getWindow());
    }

    /**
     * Configura la interfaz de usuario, incluyendo idioma manejando los distintos idiomas y acciones de los elementos gráficos.
     */
    @FXML
    private void setUI() {
        // Obtiene el bundle gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Configura el ComboBox selector de idioma
        LanguageManager.setLanguageCombobox(languageComboBox, this::updateUILanguage);

        // Focus inicial en username field
        Platform.runLater(() -> tfUsername.requestFocus());

        // Botón por defecto al pulsar Enter
        loginButton.setDefaultButton(true);
    }

    /**
     * Actualiza los textos de la UI según el idioma seleccionado.
     */
    private void updateUILanguage() {
        // Obtiene el bundle actual, gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Actualiza el título de la ventana
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setTitle("ArrulloPark - " + bundle.getString("login_title_text"));

        // Establece los textos de la UI según el bundle
        tfUsername.setPromptText(bundle.getString("email_username_prompt"));
        pfPass.setPromptText(bundle.getString("password_prompt"));
        loginButton.setText(bundle.getString("login_button_text"));
        helpLink.setText(bundle.getString("help_link_text"));

        tfUsername.setTooltip(new Tooltip(bundle.getString("email_username_tooltip")));
        pfPass.setTooltip(new Tooltip(bundle.getString("password_tooltip")));
        loginButton.setTooltip(new Tooltip(bundle.getString("login_button_tooltip")));
        helpLink.setTooltip(new Tooltip(bundle.getString("help_link_tooltip")));
        languageComboBox.setTooltip(new Tooltip(bundle.getString("language_combobox_tooltip")));
    }

    /**
     * Maneja el evento de inicio de sesión verificando las credenciales ingresadas.
     * Si los intentos fallidos superan el límite, se genera un reporte de auditoría.
     */
    private void handleLogin() {
        String username = tfUsername.getText();
        String password = pfPass.getText();

        if (validateCredentials(username, password)) {
            try {
                // Cambiar a la vista de la pantalla de presentación
                ViewManager.changeView(loginButton, "/views/splash_screen-view.fxml", "loading_text", 700, 500, false, false, false, false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                Notifier.showAlert(AlertType.ERROR, "error_text", "login_failed_text", "login_failed_too_many_attempts_message_text");
                Platform.exit();

                // Generar un reporte de auditoría de inicio de sesión
                // TODO: Sería interesante guardar en base de datos o fichero un contador de intentos fallidos para permitir bloqueos temporales o permanentes
                ReportManager.generateLoginAuditReport();
            }
            Notifier.showAlert(AlertType.ERROR, "error_text", "login_failed_text", "login_failed_message_text");
        }
    }

    /**
     * Verifica las credenciales ingresadas para autenticar al usuario.
     *
     * @param username Nombre de usuario o correo electrónico.
     * @param password Contraseña.
     * @return true si las credenciales son correctas, false si no lo son.
     */
    // Validar credenciales de usuario
    private boolean validateCredentials(String username, String password) {
        return (("a".equals(username) || "admin@arrullopark.com".equals(username)) && "".equals(password));
    }

    /**
     * Abre la página web en el navegador predeterminado del sistema.
     *
     * @param uri URI de la página web a abrir.
     */
    private void openWebPage(URI uri) {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aplica un efecto de animación flotante al contenedor de la aplicación.
     */
    // Aplicar efecto flotante al box de login
    private void applyFloatingEffect() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), vboxCenter);
        transition.setByY(-10);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }
}