package dam.parkingcontrol.service;

import dam.parkingcontrol.model.DAOEntryExitRecord;
import dam.parkingcontrol.model.DAOVehicle;
import dam.parkingcontrol.model.DTOEntryExitRecord;
import dam.parkingcontrol.model.DTOVehicle;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static dam.parkingcontrol.model.DAOVehicle.getAllVehicles;

/**
 * La clase ParkingManager proporciona métodos para gestionar el parking.
 *
 * @version 1.2.1
 */
public class ParkingManager {
    private int totalSpots = 20; // Número total de plazas
    private int freeSpots = totalSpots; // Inicialmente todas las plazas están libres
    /**
     * The Parking.
     */
    Map<Integer, DTOVehicle> parking;
    /**
     * The Random.
     */
    Random random;
    /**
     * The Vehicles.
     */
    ArrayList<DTOVehicle> vehicles;

    /**
     * Instantiates a new Parking manager.
     */
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
     * Gets free spots count.
     *
     * @return the free spots count
     */
    public synchronized int getFreeSpotsCount() {
        return freeSpots;
    }

    /**
     * Encuentra una plaza de aparcamiento disponible y estaciona un vehículo aleatoriamente.
     *
     * @return the int
     */
    public synchronized int parkVehicle() {
        if (freeSpots > 0) {
            DTOVehicle vehicle = findRandomAvailableVehicle();
            int parkingSpot = findFreeSpot();

            if (vehicle != null && parkingSpot != -1) {
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
        }
        return -1; // No hay plazas libres
    }

    /**
     * Saca un coche aleatorio de su plaza de parking.
     *
     * @param spot the spot
     */
    public synchronized void unParkVehicle(int spot) {
        if (freeSpots < totalSpots) {
            DTOVehicle vehicleToUnPark = parking.get(spot);
            if (vehicleToUnPark != null && vehicleToUnPark.isParked()) {
                vehicleToUnPark.setParked(false);
                freeSpots++;
                try {
                    DAOVehicle.updateVehicleStatus(vehicleToUnPark);
                    DAOEntryExitRecord.registerExit(new DTOEntryExitRecord(vehicleToUnPark.getId_vehicle(), null, Timestamp.valueOf(LocalDateTime.now())));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                parking.put(spot, null);
            }
        }
    }

    /**
     * Gets vehicle by spot.
     *
     * @param spot the spot
     * @return the vehicle by spot
     */
    public DTOVehicle getVehicleBySpot(int spot) {
        return parking.get(spot);
    }

    /**
     * Clear parking.
     */
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

    private DTOVehicle findRandomAvailableVehicle() {
        int randomVehicleIndex;
        DTOVehicle vehicle;
        do {
            randomVehicleIndex = random.nextInt(vehicles.size());
            vehicle = vehicles.get(randomVehicleIndex);
        } while (vehicle.isParked());
        return vehicle;
    }

    private int findFreeSpot() {
        int parkingSpot;
        do {
            parkingSpot = random.nextInt(parking.size());
        } while (!searchParkingSpot(parkingSpot));
        return parkingSpot;
    }

    /**
     * Encuentra una plaza de aparcamiento ocupada.
     *
     * @return el índice de la plaza ocupada o -1 si no hay ninguna.
     */
    public int findOccupiedSpot() {
        int randomSpot;
        do {
            randomSpot = random.nextInt(parking.size());
        } while (searchParkingSpot(randomSpot));
        return randomSpot;
    }
}
