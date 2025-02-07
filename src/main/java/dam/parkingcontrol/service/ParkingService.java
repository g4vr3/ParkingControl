package dam.parkingcontrol.service;

import dam.parkingcontrol.model.DTOVehicle;

import java.util.*;

import static dam.parkingcontrol.model.DAOVehicle.getAllVehicles;

public class ParkingService {

    Map<Integer, DTOVehicle> parking = new HashMap<>();
    Random random = new Random();
    ArrayList<DTOVehicle> vehicles = new ArrayList<>();

    // Inicializar las plazas de aparcamiento con valores nulos (plazas vacías)
    public void initializeParking() {
        vehicles = getAllVehicles();
        for (int i = 0; i < 20; i++) {
            parking.put(i, null);
        }
    }

    // Méthod para encontrar una plaza de aparcamiento disponible y estacionar el vehículo
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

    // Method para sacar un coche de su plaza de parking
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

    // Méthod para buscar una plaza de aparcamiento disponible
    public boolean searchParkingSpot(int spot) {
        if (parking.get(spot) == null) {
            return true; // Plaza disponible
        }
        return false; // Plaza ocupada
    }
}