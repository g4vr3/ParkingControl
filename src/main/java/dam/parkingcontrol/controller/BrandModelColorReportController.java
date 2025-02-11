package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.LanguageManager;
import dam.parkingcontrol.utils.IconUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ResourceBundle;

import static dam.parkingcontrol.service.ReportManager.generateBrandModelColorReport;
import static dam.parkingcontrol.utils.Notifier.showAlert;

public class BrandModelColorReportController {

    @FXML
    private TextField tfModel;

    @FXML
    private TextField tfBrand;

    @FXML
    private TextField tfColor;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Button btnGenerateReport;
    
    @FXML 
    private Button btnBack;

    @FXML
    private Label brandModelColorReportViewTitle, brandModelColorReportViewDescription, brandLabel, modelLabel, colorLabel;

    private ResourceBundle bundle;

    /**
     * Inicialización de la interfaz y componentes.
     * Configura los elementos de la UI y asigna eventos a los componentes.
     */
    @FXML
    private void initialize() {
        setUI();
        btnGenerateReport.setOnAction(_ -> saveVehicleData());
        btnBack.setOnAction(_ -> goBack());
        Platform.runLater(() -> IconUtil.setAppIcon((Stage) btnGenerateReport.getScene().getWindow()));
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

        // Botón por defecto al pulsar Enter
        btnGenerateReport.setDefaultButton(true);

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
        tfColor.setPromptText(bundle.getString("color_prompt"));
        btnGenerateReport.setText(bundle.getString("generate_report_button_text"));
        btnBack.setText(bundle.getString("back_button_text"));
        brandModelColorReportViewTitle.setText(bundle.getString("brand_model_color_report_view_title"));
        brandModelColorReportViewDescription.setText(bundle.getString("brand_model_color_report_view_description"));
        brandLabel.setText(bundle.getString("brand_label"));
        modelLabel.setText(bundle.getString("model_label"));
        colorLabel.setText(bundle.getString("color_label"));

        //Establecer los tooltips de la UI según el bundle
        tfModel.setTooltip(new Tooltip(bundle.getString("model_tooltip")));
        tfBrand.setTooltip(new Tooltip(bundle.getString("brand_tooltip")));
        tfColor.setTooltip(new Tooltip(bundle.getString("color_tooltip")));
        btnGenerateReport.setTooltip(new Tooltip(bundle.getString("generate_report_button_tooltip")));
        languageComboBox.setTooltip(new Tooltip(bundle.getString("language_combobox_tooltip")));
        btnBack.setTooltip(new Tooltip(bundle.getString("back_button_tooltip")));
    }

    /**
     * Guarda los datos que se escriben en los TextField donde se introducen los datos para el reporte
     */
    @FXML
    private void saveVehicleData() {
        String model = null, brand = null, color = null;
        if (!tfModel.getText().isEmpty()) {
            model = tfModel.getText();
        }
        if (!tfBrand.getText().isEmpty()) {
            brand = tfBrand.getText();
        }
        if (!tfColor.getText().isEmpty()) {
            color = tfColor.getText();
        }
        generateBrandModelColorReport(brand, model, color);
    }
    public void goBack() {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }


}
