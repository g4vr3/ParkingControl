package dam.parkingcontrol.controller;

import dam.parkingcontrol.service.ParkingManager;
import dam.parkingcontrol.service.ReportManager;
import dam.parkingcontrol.utils.Notifier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Controlador para la gestión del estacionamiento.
 * Se encarga de manejar la ocupación y liberación de plazas de aparcamiento,
 * así como de simular la entrada y salida de vehículos de forma automática.
 *
 * @version 1.2
 */
public class ParkingController {

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
    private Label parkingStatus;

    private final Color COLOR_DEFAULT = Color.web("#60605B");
    private final Color COLOR_RED = Color.web("#FF6347");
    private boolean openedParking = false;
    private Button[] parkingSpots;
    ParkingManager parkingManager = new ParkingManager();

    /**
     * Inicializa el controlador, asociando todas las plazas de aparcamiento a un array.
     */
    public void initialize() {
        // Asociar las plazas a un array para gestionarlas fácilmente
        parkingSpots = new Button[]{
                spot0, spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9,
                spot10, spot11, spot12, spot13, spot14, spot15, spot16, spot17, spot18, spot19
        };
        btnOpenParking.setOnAction(event -> openParking());
        btnCloseParking.setOnAction(event -> closeParking());
        btnOpenParking.setVisible(true);
        btnCloseParking.setVisible(false);
    }

    /**
     * Registra la entrada de un vehículo en una plaza de aparcamiento en concreto.
     * @param spotId Número de la plaza.
     */
    // Simular la entrada de un vehículo y ocupar una plaza
    public synchronized void registerEntry(int spotId) {
        if (spotId >= 0 && spotId < parkingSpots.length) {
            System.out.println("Registrando entrada en la plaza: " + spotId); // Debug
            parkingSpots[spotId].setStyle("-fx-background-color: " + toRgbString(COLOR_RED) + ";");
        } else {
            System.out.println("Índice de plaza inválido: " + spotId); // Debug
        }
    }

    /**
     * Registra la salida de un vehículo y libera la plaza de aparcamiento que usaba.
     * @param spotId Número de la plaza a liberar.
     */
    // Simular la salida de un vehículo y liberar la plaza
    public synchronized void registerExit(int spotId) {
        if (spotId >= 0 && spotId < parkingSpots.length) {
            System.out.println("Registrando salida en la plaza: " + spotId); // Debug
            parkingSpots[spotId].setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";");
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
        parkingStatus.setText("Parking Abierto");
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
        parkingStatus.setText("Parking Cerrado");
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
        int spot = parkingManager.unParkVehicle();
        if (spot != -1) {
            registerExit(spot);
        }

    }


    /**
     * Inicia la simulación automática de entradas y salidas de vehículos.
     */
    public void startSimulation() {
        openedParking = true;
        Random entryRandom = new Random();
        Random exitRandom = new Random();
        Thread entryThread = new Thread(() -> {
            try {
                while (openedParking) { // El hilo sigue corriendo mientras openedParking sea true
                    simulateRandomEntry();
                    Random random = new Random();
                    int delay = 10000 + entryRandom.nextInt(3000); // Tiempo entre 10 y 13 segundos
                    Thread.sleep(delay); // Esperar un tiempo aleatorio
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread exitThread = new Thread(() -> {
            try {
                Thread.sleep(30000); // Esperar 5 segundos antes de iniciar las salidas
                while (openedParking) {
                    // El hilo sigue corriendo mientras openedParking sea true
                    simulateRandomExit();
                    Random random = new Random();
                    int delay = 10000 + exitRandom.nextInt(10000); // Tiempo entre 10 y 20 segundos
                    Thread.sleep(delay); // Esperar un tiempo aleatorio
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Iniciar los hilos
        entryThread.start();
        exitThread.start();
    }
    public void stopSimulation() {
        openedParking = false;
        parkingManager.clearParking();
    }

    /**
     * Convierte un color en formato {@link Color} a su representación en RGB.
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