// DTOEntryExitRecord.java
package dam.parkingcontrol.model;

import java.sql.Timestamp;

/**
 * La clase DTOEntryExitRecord representa un registro de entrada y salida de un vehículo en el estacionamiento.
 * Contiene información sobre el vehículo y los tiempos de entrada y salida.
 *
 * @version 1.0
 */
public class DTOEntryExitRecord {

    // Variables básicas para su uso en el registro de estacionamiento
    private int record_id, vehicle_id;
    private Timestamp entry_time, exit_time;

    /**
     * Constructor con atributos de la clase DTOEntryExitRecord.
     *
     * @param vehicle_id el ID del vehículo
     * @param entry_time el tiempo de entrada del vehículo
     * @param exit_time  el tiempo de salida del vehículo
     */
    public DTOEntryExitRecord(int vehicle_id, Timestamp entry_time, Timestamp exit_time) {
        this.vehicle_id = vehicle_id;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
    }

    /**
     * Constructor vacío de la clase DTOEntryExitRecord.
     */
    public DTOEntryExitRecord() {
    }

    // Getters & Setters

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
     * Obtiene el tiempo de entrada.
     *
     * @return el tiempo de entrada
     */
    public Timestamp getEntry_time() {
        return entry_time;
    }

    /**
     * Establece el tiempo de entrada.
     *
     * @param entry_time el tiempo de entrada
     */
    public void setEntry_time(Timestamp entry_time) {
        this.entry_time = entry_time;
    }

    /**
     * Obtiene el tiempo de salida.
     *
     * @return el tiempo de salida
     */
    public Timestamp getExit_time() {
        return exit_time;
    }

    /**
     * Establece el tiempo de salida.
     *
     * @param exit_time el tiempo de salida
     */
    public void setExit_time(Timestamp exit_time) {
        this.exit_time = exit_time;
    }
}