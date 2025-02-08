package dam.parkingcontrol.model;

import java.time.LocalDate;

/**
 * La clase DTOEntryExitRecord representa un registro de entrada y salida de un vehículo en el parking.
 * Contiene información sobre el vehículo y los tiempos de entrada y salida.
 *
 * @version 1.0
 */
public class DTOEntryExitRecord {

    // Variables básicas para usar en el registro del parking
    private int record_id, vehicle_id;
    private LocalDate entry_time, exit_time;

    /**
     * Constructor con atributos de la clase DTOEntryExitRecord.
     *
     * @param vehicle_id el ID del vehículo
     * @param entry_time la hora de entrada del vehículo
     * @param exit_time la hora de salida del vehículo
     */
    public DTOEntryExitRecord(int vehicle_id, LocalDate entry_time, LocalDate exit_time) {
        this.vehicle_id = vehicle_id;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
    }

    /**
     * Constructor vacío de la clase DTOEntryExitRecord.
     */
    public DTOEntryExitRecord() {
    }

    //Getters & Setters

    /**
     * Obtiene el ID del registro.
     *
     * @return el ID del registro
     */
    public int getRecord_id() {
        return record_id;
    }

    /**
     * Establece el ID del registro.
     *
     * @param record_id el ID del registro
     */
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    /**
     * Obtiene el ID del vehículo.
     *
     * @return el ID del vehículo
     */
    public int getVehicle_id() {
        return vehicle_id;
    }

    /**
     * Establece el ID del vehículo.
     *
     * @param vehicle_id el ID del vehículo
     */
    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    /**
     * Obtiene la hora de entrada del vehículo.
     *
     * @return la hora de entrada del vehículo
     */
    public LocalDate getEntry_time() {
        return entry_time;
    }

    /**
     * Establece la hora de entrada del vehículo.
     *
     * @param entry_time la hora de entrada del vehículo
     */
    public void setEntry_time(LocalDate entry_time) {
        this.entry_time = entry_time;
    }

    /**
     * Obtiene la hora de salida del vehículo.
     *
     * @return la hora de salida del vehículo
     */
    public LocalDate getExit_time() {
        return exit_time;
    }

    /**
     * Establece la hora de salida del vehículo.
     *
     * @param exit_time la hora de salida del vehículo
     */
    public void setExit_time(LocalDate exit_time) {
        this.exit_time = exit_time;
    }
}