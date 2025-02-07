package dam.parkingcontrol.service;

import dam.parkingcontrol.model.DTOVehicle;

import java.util.*;

import static dam.parkingcontrol.model.DAOVehicle.getAllVehicles;

/**
 * La clase ParkingService proporciona métodos para gestionar el parking.
 *
 * @version 1.0
 */
public class ParkingManager {

    Map<Integer, DTOVehicle> parking = new HashMap<>();
    Random random = new Random();
    ArrayList<DTOVehicle> vehicles = new ArrayList<>();

    /**
     * Inicializa las plazas de aparcamiento con valores nulos (plazas vacías).
     */
    public void initializeParking() {
        vehicles = getAllVehicles();
        for (int i = 0; i < 20; i++) {
            parking.put(i, null);
        }
    }

    /**
     * Encuentra una plaza de aparcamiento disponible y estaciona el vehículo.
     *
     * @param vehicle el vehículo a estacionar.
     */
    public void parkVehicle(DTOVehicle vehicle) {
        int randomSpot;
        if (!vehicle.isParked()) {
            do {
                randomSpot = random.nextInt(parking.size());
            } while (!searchParkingSpot(randomSpot));
            parking.put(randomSpot, vehicle);
            vehicle.setParked(true);
        }
    }

    /**
     * Saca un coche de su plaza de parking.
     *
     * @param vehicle el vehículo a retirar.
     */
    public void unParkVehicle(DTOVehicle vehicle) {
        int randomSpot;
        if (vehicle.isParked()) {
            do {
                randomSpot = random.nextInt(parking.size());
            } while (searchParkingSpot(randomSpot));
            parking.remove(randomSpot);
            vehicle.setParked(false);
        }
    }

    /**
     * Busca una plaza de aparcamiento disponible.
     *
     * @param spot la plaza a buscar.
     * @return true si la plaza está disponible, false si está ocupada.
     */
    public boolean searchParkingSpot(int spot) {
        if (parking.get(spot) == null) {
            return true; // Plaza disponible
        }
        return false; // Plaza ocupada
    }
}