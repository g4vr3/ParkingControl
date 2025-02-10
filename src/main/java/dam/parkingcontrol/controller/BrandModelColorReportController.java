package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.LanguageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

import static dam.parkingcontrol.service.ReportManager.generateBrandModelColorReport;

public class BrandModelColorReportController {

    @FXML
    private VBox vboxCenter;

    @FXML
    private TextField tfModel;

    @FXML
    private TextField tfBrand;

    @FXML
    private TextField tfColour;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Button saveButton, closeButton;

    private ResourceBundle bundle;

    /**
     * Inicialización de la interfaz y componentes.
     * Configura los elementos de la UI y asigna eventos a los componentes.
     */
    @FXML
    private void initialize() {
        setUI();
        saveButton.setOnAction(_ -> saveVehicleData());
        closeButton.setOnAction(_ -> Platform.exit());
    }

    /**
     * Configura la interfaz de usuario, incluyendo idioma manejando los distintos idiomas y acciones de los elementos gráficos.
     */
    @FXML
    private void setUI() {
        // Obtiene el bundle gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Añade a la lista de idiomas los idiomas soportados
        languageComboBox.setItems(LanguageManager.getSupportedLanguages());

        // Establece por defecto el idioma actual (seleccionado en el login o del sistema)
        languageComboBox.setValue(LanguageManager.getCurrentLanguage());

        // Listener para cambios en el idioma (selección de idioma en ComboBox)
        languageComboBox.valueProperty().addListener((_, _, newValue) -> {
            // Carga el bundle con el nuevo idioma
            LanguageManager.loadLanguage(LanguageManager.getLanguageCodeFromName(newValue));
            // Actualiza el idioma de la UI
            updateUILanguage();
        });

        // Desplegar el ComboBox cuando reciba el foco
        languageComboBox.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                languageComboBox.show();
            }
        });

        // Botón por defecto al pulsar Enter
        saveButton.setDefaultButton(true);

        // Focus inicial en el campo marca del coche (evitando el focus en el ComboBox)
        Platform.runLater(() -> tfBrand.requestFocus());
    }

    /**
     * Actualiza los textos de la UI según el idioma seleccionado.
     */
    private void updateUILanguage() {
        // Obtiene el bundle actual, gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Establece los textos de la UI según el bundle
        tfModel.setPromptText(bundle.getString("model_prompt"));
        tfBrand.setPromptText(bundle.getString("brand_prompt"));
        tfColour.setPromptText(bundle.getString("colour_prompt"));
        saveButton.setText(bundle.getString("save_button_text"));
        closeButton.setText(bundle.getString("close_button_text"));

        //Establecer los tooltips de la UI según el bundle
        tfModel.setTooltip(new Tooltip(bundle.getString("model_tooltip")));
        tfBrand.setTooltip(new Tooltip(bundle.getString("brand_tooltip")));
        tfColour.setTooltip(new Tooltip(bundle.getString("colour_tooltip")));
        saveButton.setTooltip(new Tooltip(bundle.getString("save_button_tooltip")));
        closeButton.setTooltip(new Tooltip(bundle.getString("close_button_tooltip")));
        languageComboBox.setTooltip(new Tooltip(bundle.getString("language_combobox_tooltip")));
    }

    /**
     * Guarda los datos que se escriben en los TextField donde se introducen los datos para el reporte
     */
    @FXML
    private void saveVehicleData() {
        String model = null, brand = null, colour = null;
        if (!tfModel.getText().isEmpty()) { model = tfModel.getText(); }
        if (!tfBrand.getText().isEmpty()) { brand = tfBrand.getText(); }
        if (!tfColour.getText().isEmpty()) { colour = tfColour.getText(); }
        generateBrandModelColorReport(brand, model, colour);
    }


}
