package dam.parkingcontrol.controller;

    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.paint.Color;

    import java.util.Random;

    public class ParkingController {

        @FXML private Button spot0;
        @FXML private Button spot1;
        @FXML private Button spot2;
        @FXML private Button spot3;
        @FXML private Button spot4;
        @FXML private Button spot5;
        @FXML private Button spot6;
        @FXML private Button spot7;
        @FXML private Button spot8;
        @FXML private Button spot9;
        @FXML private Button spot10;
        @FXML private Button spot11;
        @FXML private Button spot12;
        @FXML private Button spot13;
        @FXML private Button spot14;
        @FXML private Button spot15;
        @FXML private Button spot16;
        @FXML private Button spot17;
        @FXML private Button spot18;
        @FXML private Button spot19;
        private Color COLOR_DEFAULT = Color.web("#60605B");
        private Color COLOR_RED = Color.web("#FF6347");
        private boolean openedParking = false;
        private Button[] parkingSpots;

        public void initialize() {
            // Asociar las plazas a un array para gestionarlas fácilmente
            parkingSpots = new Button[]{
                spot0, spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9,
                spot10, spot11, spot12, spot13, spot14, spot15, spot16, spot17, spot18, spot19
            };
        }

        // Método para simular la entrada de un vehículo y ocupar una plaza
        public synchronized void registerEntry(int spotId) {
            if (spotId >= 0 && spotId < parkingSpots.length) {
                parkingSpots[spotId].setStyle("-fx-background-color: " + toRgbString(COLOR_RED) + ";"); // Cambiar la plaza a ocupada
                // TODO: Implementar la lógica para modificar el estado del coche a parked (true) añadir el registro en la tabla de entradas salidas en bd conectando con service
            }
        }

        // Método para simular la salida de un vehículo y liberar la plaza
        public synchronized void registerExit(int spotId) {
            if (spotId >= 0 && spotId < parkingSpots.length) {
                parkingSpots[spotId].setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";"); // Cambiar la plaza a libre
                // TODO: Implementar la lógica para modificar el estado del coche a parked (false) y modificar el registro en la tabla de entradas salidas
                //  seteando el campo hora salida en bd conectando con service
            }
        }

        // Método para abrir el parking (inicia vacío)
        public void openParking() {
            for (Button spot : parkingSpots) {
                spot.setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";"); // Todas las plazas vacías
            }
        }

        // Método para cerrar el parking (vacía todas las plazas)
        public void closeParking() {
            for (Button spot : parkingSpots) {
                spot.setStyle("-fx-background-color: " + toRgbString(COLOR_DEFAULT) + ";"); // Vaciar todas las plazas
            }
        }

        // Método para simular un coche entrando al parking
        public void simulateRandomEntry() {
            for (int i = 0; i < parkingSpots.length; i++) {
                if (parkingSpots[i].getStyle().contains(toRgbString(COLOR_DEFAULT))) { // Si la plaza está libre
                    registerEntry(i);
                    break;
                }
            }
        }

        // Método para simular un coche saliendo del parking
        public void simulateRandomExit() {
            Random random = new Random();
            for (int i = 0; i < parkingSpots.length; i++) {
                int randomIndex = random.nextInt(20); // Genera un número aleatorio entre 0 y 19
                if (parkingSpots[randomIndex].getStyle().contains(toRgbString(COLOR_RED))) { // Si la plaza está ocupada
                    registerExit(randomIndex);
                    break; // Sale del bucle después de registrar la salida
                }
            }
        }

        public void startSimulation() {
            openedParking = true;
            Thread entryThread = new Thread(() -> {
                try {
                    while (openedParking) { // El hilo sigue corriendo mientras openedParking sea true
                        simulateRandomEntry();
                        Random random = new Random();
                        int delay = 10000 + random.nextInt(10000); // Tiempo entre 10 y 20 segundos
                        Thread.sleep(delay); // Esperar un tiempo aleatorio
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread exitThread = new Thread(() -> {
                try {
                    while (openedParking) { // El hilo sigue corriendo mientras openedParking sea true
                        simulateRandomExit();
                        Random random = new Random();
                        int delay = 10000 + random.nextInt(10000); // Tiempo entre 10 y 20 segundos
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

        // Método para convertir Color a String RGB
        private String toRgbString(Color color) {
            return String.format("#%02X%02X%02X",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255));
        }
    }