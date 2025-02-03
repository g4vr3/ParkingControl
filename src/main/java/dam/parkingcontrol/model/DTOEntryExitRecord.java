package dam.parkingcontrol.model;

import java.time.LocalDate;

public class DTOEntryExitRecord {

    // Variables básicas para usar los el registro del parking
    private int record_id, vehicle_id;
    private LocalDate entry_time, exit_time;

    // Constructor con Atributos y vacío de la clase EntryExitRecord.java
    public DTOEntryExitRecord(int record_id, int vehicle_id, LocalDate entry_time, LocalDate exit_time) {
        this.record_id = record_id;
        this.vehicle_id = vehicle_id;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
    }

    public DTOEntryExitRecord() {
    }

    //Getters & Setters
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

    public LocalDate getEntry_time() {
        return entry_time;
    }
    public void setEntry_time(LocalDate entry_time) {
        this.entry_time = entry_time;
    }

    public LocalDate getExit_time() {
        return exit_time;
    }
    public void setExit_time(LocalDate exit_time) {
        this.exit_time = exit_time;
    }
}
