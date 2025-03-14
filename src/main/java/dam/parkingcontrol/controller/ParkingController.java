package dam.parkingcontrol.controller;

import dam.parkingcontrol.model.DTOVehicle;
import dam.parkingcontrol.service.LanguageManager;
import dam.parkingcontrol.service.ParkingManager;
import dam.parkingcontrol.service.ReportManager;
import dam.parkingcontrol.service.ViewManager;
import dam.parkingcontrol.utils.IconUtil;
import dam.parkingcontrol.utils.Notifier;
import dam.parkingcontrol.utils.PDFUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Controlador para la gestión del estacionamiento.
 * Se encarga de manejar la ocupación y liberación de plazas de aparcamiento,
 * así como de simular la entrada y salida de vehículos de forma automática.
 *
 * @version 1.4.3
 */
public class ParkingController {

    @FXML
    private Hyperlink helpLink;
    @FXML
    private Button spot0;
    @FXML
    private Button spot1;
    @FXML
    private Button spot2;
    @FXML
    private Button spot3;
    @FXML
    private Button spot4;
    @FXML
    private Button spot5;
    @FXML
    private Button spot6;
    @FXML
    private Button spot7;
    @FXML
    private Button spot8;
    @FXML
    private Button spot9;
    @FXML
    private Button spot10;
    @FXML
    private Button spot11;
    @FXML
    private Button spot12;
    @FXML
    private Button spot13;
    @FXML
    private Button spot14;
    @FXML
    private Button spot15;
    @FXML
    private Button spot16;
    @FXML
    private Button spot17;
    @FXML
    private Button spot18;
    @FXML
    private Button spot19;
    @FXML
    private Button btnOpenParking;
    @FXML
    private Button btnCloseParking;
    @FXML
    private Button btnOpenBrandModelColorReportGeneration;
    @FXML
    private Label parkingStatus;
    @FXML
    private Label freeSpotsLabel;
    @FXML
    private Label lastRecordLabelInfo;
    @FXML
    private Label lastRecordLabel;
    @FXML
    private Label parkingToolsLabel;

    @FXML
    private ImageView entryIcon, exitIcon;

    @FXML
    private ComboBox<String> languageComboBox;

    private ResourceBundle bundle;

    private final Color COLOR_DEFAULT = Color.web("#60605B");
    private final Color COLOR_PRIMARY = Color.web("#EE5593");
    private ScheduledExecutorService scheduler;
    private boolean openedParking;
    private Button[] parkingSpots;

    private ParkingManager parkingManager = new ParkingManager();

    /**
     * Inicializa el controlador, asociando todas las plazas de aparcamiento a un array.
     */
    public void initialize() {
        setUI(); // Configurar la interfaz de usuario

        // Asociar las plazas a un array para gestionarlas fácilmente
        parkingSpots = new Button[]{
                spot0, spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9,
                spot10, spot11, spot12, spot13, spot14, spot15, spot16, spot17, spot18, spot19
        };
        btnOpenParking.setOnAction(event -> openParking());
        btnCloseParking.setOnAction(event -> closeParking());
        helpLink.setOnAction(event -> PDFUtils.openUserManualPdf());
        btnOpenBrandModelColorReportGeneration.setOnAction(event -> {
            try {
                ViewManager.changeView(btnOpenBrandModelColorReportGeneration, "/views/brand_model_color_report-view.fxml", "report_view_title", 800, 600, true, true, true, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnOpenParking.setVisible(true);
        btnCloseParking.setVisible(false);

        // Ocultar campos de información de última entrada/salida y no manejar el espacio que ocupan
        btnOpenParking.setVisible(true);
        btnCloseParking.setVisible(false);
        lastRecordLabelInfo.setVisible(false);
        lastRecordLabel.setVisible(false);
        entryIcon.setVisible(false);
        exitIcon.setVisible(false);
        lastRecordLabelInfo.setManaged(false);
        lastRecordLabel.setManaged(false);
        entryIcon.setManaged(false);
        exitIcon.setManaged(false);

        Platform.runLater(() -> {
            IconUtil.setAppIcon((Stage) btnOpenParking.getScene().getWindow());
            btnCloseParking.getScene().getWindow().setOnCloseRequest(event -> {
                if (openedParking) {
                    closeParking();
                }
            });
            registerKeyEvents();
        }); // Detener la simulación al cerrar la ventana);

        updateFreeSpotsLabel(); // Actualizar el Label de plazas libres
    }

    /**
     * Configura la interfaz de usuario, incluyendo idioma manejando los distintos idiomas y acciones de los elementos gráficos.
     *
     * @since 1.3
     */
    @FXML
    private void setUI() {
        // Obtiene el bundle gestionado por el LanguageManager
        bundle = LanguageManager.getBundle();

        // Configura el ComboBox selector de idioma
        LanguageManager.setLanguageCombobox(languageComboBox, this::updateUILanguage);

        // Focus inicial en el campo de estado del parking (evitando el focus en el ComboBox)
        Platform.runLater(() -> parkingStatus.requestFocus());
    }

    /**
     * Maneja los eventos de teclas presionadas.
m     * Se asignan Ctrl+O para abrir el estacionamiento y Ctrl+C para cerrarlo.
     */
    private void handleKeyPress(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.O) {
            openParking();  // Abre el estacionamiento cuando se presiona Ctrl+O
        } else if (event.isControlDown() && event.getCode() == KeyCode.C) {
            closeParking();  // Cierra el estacionamiento cuando se presiona Ctrl+C
        }
    }

    /**
     * Registra el evento de teclado en la escena.
     */
    private void registerKeyEvents() {
        btnOpenParking.getScene().setOnKeyPressed(this::handleKeyPress);
    }

    private void updateUILanguage() {
        bundle = LanguageManager.getBundle();
        btnOpenParking.setText(bundle.getString("open_parking_button_text"));
        btnCloseParking.setText(bundle.getString("close_parking_button_text"));
        parkingStatus.setText(bundle.getString("parking_closed_text"));
        lastRecordLabelInfo.setText(bundle.getString("last_entry_exit_record_text"));
        freeSpotsLabel.setText(bundle.getString("free_spots_label_text") + ": " + parkingManager.getFreeSpotsCount());
        btnOpenBrandModelColorReportGeneration.setText(bundle.getString("generate_filters_report"));
        parkingToolsLabel.setText(bundle.getString("parking_tools_text"));
        helpLink.setText(bundle.getString("help_link_text"));

        btnOpenParking.setTooltip(new Tooltip(bundle.getString("open_parking_button_tooltip")));
        btnCloseParking.setTooltip(new Tooltip(bundle.getString("close_parking_button_tooltip")));
        languageComboBox.setTooltip(new Tooltip(bundle.getString("language_combobox_tooltip")));
        btnOpenBrandModelColorReportGeneration.setTooltip(new Tooltip(bundle.getString("button_open_brand_model_color_report_generation_tooltip")));
        helpLink.setTooltip(new Tooltip(bundle.getString("help_link_tooltip")));

        updateFreeSpotsLabel();
    }

    private void updateFreeSpotsLabel() {
        // Cargar el bundle actual
        bundle = LanguageManager.getBundle();

        int freeSpots = parkingManager.getFreeSpotsCount();
        Platform.runLater(() -> freeSpotsLabel.setText(bundle.getString("free_spots_label_text") + ": " + freeSpots));
    }

    /**
     * Registra la entrada de un vehículo en una plaza de aparcamiento en concreto.
     *
     * @param spotId Número de la plaza.
     */
// Simular la entrada de un vehículo y ocupar una plaza
    public synchronized void registerEntry(int spotId) {
        if (spotId >= 0 && spotId < parkingSpots.length) {
            System.out.println("Registrando entrada en la plaza: " + spotId); // Debug
            DTOVehicle vehicle = parkingManager.getVehicleBySpot(spotId);
            Platform.runLater(() -> {
                lastRecordLabel.setText(vehicle.getLicensePlate());
                entryIcon.setVisible(true);
                exitIcon.setVisible(false);
            });
            updateFreeSpotsLabel(); // Actualizar el Label de plazas libres
            parkingSpots[spotId].setStyle("-fx-background-color: " + toRgbString(COLOR_PRIMARY) + ";");
        } else {
            System.out.println("Índice de plaza inválido: " + spotId); // Debug
        }
    }

    /**
     * Registra la salida de un vehículo y libera la plaza de aparcamiento que usaba.
     *
     * @param spotId Número de la plaza a liberar.
     */
    public synchronized void registerExit(int spotId) {
        if (spotId >= 0 && spotId < parkingSpots.length) {
            System.out.println("Registrando salida en la plaza: " + spotId); // Debug
            DTOVehicle vehicle = parkingManager.getVehicleBySpot(spotId);

            Platform.runLater(() -> {
                if (vehicle != null) {
                    lastRecordLabel.setText(vehicle.getLicensePlate());
                    exitIcon.setVisible(true);
                    entryIcon.setVisible(false);
                    updateFreeSpotsLabel(); // Actualizar el Label de plazas libres
                    parkingSpots[spotId].setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";");
                } else {
                    System.out.println("La plaza ya está vacía: " + spotId); // Debug
                }
            });
        } else {
            System.out.println("Índice de plaza inválido: " + spotId); // Debug
        }
    }

    /**
     * Abre el estacionamiento inicializando todas las plazas como disponibles.
     */
// Abrir el parking (inicia vacío)
    public void openParking() {
        for (Button spot : parkingSpots) {
            spot.setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";"); // Todas las plazas vacías
        }
        startSimulation();
        Notifier.showTooltip(btnOpenParking, "parking_opened_tooltip");
        btnOpenParking.setVisible(false);
        btnCloseParking.setVisible(true);

        // Mostrar campos de información de última entrada/salida y manejar el espacio que ocupan
        lastRecordLabelInfo.setVisible(true);
        lastRecordLabel.setVisible(true);
        lastRecordLabelInfo.setManaged(true);
        lastRecordLabel.setManaged(true);
        entryIcon.setManaged(true);
        exitIcon.setManaged(true);

        parkingStatus.setText(bundle.getString("parking_opened_text"));
    }

    /**
     * Cierra el estacionamiento, vaciando todas las plazas. Genera un informe de fin de día.
     */
// Cerrar el parking (vacía todas las plazas) y generar reporte diario
    public void closeParking() {
        for (Button spot : parkingSpots) {
            spot.setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";"); // Vaciar todas las plazas
        }
        stopSimulation();
        ReportManager.generateEndOfDayReport(); // Generar reporte de fin de día
        btnOpenParking.setVisible(true);
        btnCloseParking.setVisible(false);

        parkingStatus.setText(bundle.getString("parking_closed_text"));

        updateFreeSpotsLabel(); // Actualizar el Label de plazas libres
    }

    /**
     * Simula la entrada de un vehículo a la primera plaza libre.
     */
// Simular un coche entrando al parking
    public void simulateRandomEntry() {
        int spot = parkingManager.parkVehicle();
        if (spot != -1) {
            registerEntry(spot);
        }
    }

    /**
     * Simula la salida de un vehículo.
     */
// Simular un coche saliendo del parking
    public void simulateRandomExit() {
        int spot = parkingManager.findOccupiedSpot();
        if (spot != -1) {
            registerExit(spot);
            parkingManager.unParkVehicle(spot);
        }
    }

    /**
     * Inicia la simulación automática de entradas y salidas de vehículos.
     */
    public void startSimulation() {
        openedParking = true;
        scheduler = Executors.newScheduledThreadPool(2);

        Runnable entryTask = () -> {
            if (openedParking) {
                simulateRandomEntry();
            }
        };

        Runnable exitTask = () -> {
            if (openedParking) {
                simulateRandomExit();
            }
        };

        scheduler.scheduleAtFixedRate(entryTask, 0, 3 + new Random().nextInt(6), TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(exitTask, 20, 7 + new Random().nextInt(10), TimeUnit.SECONDS);
    }

    /**
     * Finaliza la simulación automática de entradas y salidas de vehículos.
     */
    public void stopSimulation() {
        openedParking = false;
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        parkingManager.clearParking(); // Vaciar todas las plazas
    }

    /**
     * Convierte un color en formato {@link Color} a su representación en RGB.
     *
     * @param color Color a convertir.
     * @return Representación RGB en formato de cadena.
     */
    private String toRgbString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}