package dam.parkingcontrol.service;

import dam.parkingcontrol.model.DAOEntryExitRecord;
import dam.parkingcontrol.model.DAOVehicle;
import dam.parkingcontrol.model.DTOEntryExitRecord;
import dam.parkingcontrol.model.DTOVehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static dam.parkingcontrol.model.DAOVehicle.getAllVehicles;

/**
 * La clase ParkingManager proporciona métodos para gestionar el parking.
 *
 * @version 1.2
 */
public class ParkingManager {
    private int totalSpots = 20; // Número total de plazas
    private int freeSpots = totalSpots; // Inicialmente todas las plazas están libres
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
    public synchronized int getFreeSpotsCount() {
        return freeSpots;
    }

    /**
     * Encuentra una plaza de aparcamiento disponible y estaciona un vehículo aleatoriamente.
     *
     */
    public synchronized int parkVehicle() {
        if (freeSpots > 0) {
            int randomVehicleIndex;
            int parkingSpot;
            DTOVehicle vehicle;
            do {
                randomVehicleIndex = random.nextInt(vehicles.size());
                vehicle = vehicles.get(randomVehicleIndex);
            } while (vehicle.isParked());

            do {
                parkingSpot = random.nextInt(parking.size());
            } while (!searchParkingSpot(parkingSpot));

            parking.put(parkingSpot, vehicle);
            vehicle.setParked(true);
            freeSpots--;
            try {
                DAOEntryExitRecord.registerEntry(new DTOEntryExitRecord(vehicle.getId_vehicle(), Timestamp.valueOf(LocalDateTime.now()), null));
                DAOVehicle.updateVehicleStatus(vehicle);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return parkingSpot;
        }
        return -1; // No hay plazas libres
    }


    /**
     * Saca un coche aleatorio de su plaza de parking.
     *
     */
    public synchronized int unParkVehicle() {
        if (freeSpots < totalSpots) {
            int randomSpot;
            do {
                randomSpot = random.nextInt(parking.size());
            } while (searchParkingSpot(randomSpot));

            DTOVehicle vehicleToUnPark = parking.get(randomSpot);
            if (vehicleToUnPark != null && vehicleToUnPark.isParked()) {
                vehicleToUnPark.setParked(false);
                freeSpots++;
                try {
                    DAOVehicle.updateVehicleStatus(vehicleToUnPark);
                    DAOEntryExitRecord.registerExit(new DTOEntryExitRecord(vehicleToUnPark.getId_vehicle(), null, Timestamp.valueOf(LocalDateTime.now())));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                parking.put(randomSpot, null);
            }
            return randomSpot;
        }
        return -1; // No hay vehículos para salir
    }
    public DTOVehicle getVehicleBySpot(int spot) {
        return parking.get(spot);
    }

    public synchronized void clearParking() {
        for (int i = 0; i < parking.size(); i++) {
            DTOVehicle vehicle = parking.get(i);
            if (vehicle != null) {
                vehicle.setParked(false); // Lo marcamos en memoria como no estacionado
                try {
                    DAOVehicle.updateVehicleStatus(vehicle); // Lo actualizamos en la BD
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            parking.put(i, null);
        }
        freeSpots = totalSpots;
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
