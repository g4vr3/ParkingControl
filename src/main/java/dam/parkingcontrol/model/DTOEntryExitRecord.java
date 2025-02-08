// DTOEntryExitRecord.java
package dam.parkingcontrol.model;

import java.sql.Timestamp;

/**
 * The DTOEntryExitRecord class represents an entry and exit record of a vehicle in the parking lot.
 * It contains information about the vehicle and the entry and exit times.
 *
 * @version 1.0
 */
public class DTOEntryExitRecord {

    // Basic variables for use in the parking record
    private int record_id, vehicle_id;
    private Timestamp entry_time, exit_time;

    /**
     * Constructor with attributes of the DTOEntryExitRecord class.
     *
     * @param vehicle_id the ID of the vehicle
     * @param entry_time the entry time of the vehicle
     * @param exit_time the exit time of the vehicle
     */
    public DTOEntryExitRecord(int vehicle_id, Timestamp entry_time, Timestamp exit_time) {
        this.vehicle_id = vehicle_id;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
    }

    /**
     * Empty constructor of the DTOEntryExitRecord class.
     */
    public DTOEntryExitRecord() {
    }

    // Getters & Setters

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public Timestamp getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(Timestamp entry_time) {
        this.entry_time = entry_time;
    }

    public Timestamp getExit_time() {
        return exit_time;
    }

    public void setExit_time(Timestamp exit_time) {
        this.exit_time = exit_time;
    }
}