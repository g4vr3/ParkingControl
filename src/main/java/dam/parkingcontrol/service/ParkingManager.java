package dam.parkingcontrol.service;

import dam.parkingcontrol.model.DAOEntryExitRecord;
import dam.parkingcontrol.model.DAOVehicle;
import dam.parkingcontrol.model.DTOEntryExitRecord;
import dam.parkingcontrol.model.DTOVehicle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static dam.parkingcontrol.model.DAOVehicle.getAllVehicles;

/**
 * La clase ParkingManager proporciona métodos para gestionar el parking.
 *
 * @version 1.1
 */
public class ParkingManager {

    Map<Integer, DTOVehicle> parking;
    Random random;
    ArrayList<DTOVehicle> vehicles;

    public ParkingManager() {
        parking = new HashMap<>();
        random = new Random();
        vehicles = new ArrayList<>();

        initializeParking();
    }

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
     * Encuentra una plaza de aparcamiento disponible y estaciona un vehículo aleatoriamente.
     *
     */
    public int parkVehicle() {
        int randomVehicleIndex;
        int parkingSpot = -1;
        DTOVehicle vehicle;
        do {
            randomVehicleIndex = random.nextInt(vehicles.size());
            vehicle = vehicles.get(randomVehicleIndex);
        } while (vehicle.isParked());

        do {
            parkingSpot = random.nextInt(parking.size());
        } while (!searchParkingSpot(parkingSpot));

        // Estacionar el vehículo en la plaza aleatoria disponible
        parking.put(parkingSpot, vehicle);
        vehicle.setParked(true);
        try {
            DAOEntryExitRecord.registerEntry(new DTOEntryExitRecord(vehicle.getId_vehicle(), LocalDate.now(), null));
            DAOVehicle.updateVehicleStatus(vehicle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parkingSpot;
    }


    /**
     * Saca un coche aleatorio de su plaza de parking.
     *
     */
    public int unParkVehicle() {
        int randomSpot = -1;
        do {
            randomSpot = random.nextInt(parking.size());
        } while (searchParkingSpot(randomSpot));

        DTOVehicle vehicle = parking.get(randomSpot);
        if (vehicle != null && vehicle.isParked()) {
            vehicle.setParked(false);
            try {
                DAOVehicle.updateVehicleStatus(vehicle);
                DAOEntryExitRecord.registerExit(new DTOEntryExitRecord(vehicle.getId_vehicle(), null, LocalDate.now()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            parking.put(randomSpot, null);
        }
        return randomSpot;
    }

    public void clearParking() {
        for (int i = 0; i < parking.size(); i++) {
            parking.put(i, null);
        }
        try {
            DAOEntryExitRecord.updateAllExitsToCurrentDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca una plaza de aparcamiento disponible.
     *
     * @param spot la plaza a buscar.
     * @return true si la plaza está disponible, false si está ocupada.
     */
    public boolean searchParkingSpot(int spot) {
        return parking.get(spot) == null; // Plaza disponible
    }
}
