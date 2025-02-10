package dam.parkingcontrol.model;

import java.sql.Timestamp;

/**
 * La clase DTOVehicle inicializa los objetos de vehicle para trabajar con ellos en las demás clases.
 *
 * @version 1.0
 */
public class DTOVehicle {

    // Variables básicas para usar los coches en el parking
    private int vehicle_id;
    private String license_plate, model, brand, colour;
    private boolean is_parked;
    private Timestamp registration_date;

    /**
     * Constructor con atributos de la clase DTOVehicle.
     *
     * @param vehicle_id el ID del vehículo
     * @param license_plate la matrícula del vehículo
     * @param model el modelo del vehículo
     * @param brand la marca del vehículo
     * @param colour el color del vehículo
     * @param is_parked indica si el vehículo está aparcado
     * @param registration_date la fecha de registro del vehículo
     */
    public DTOVehicle(int vehicle_id, String license_plate, String model, String brand, String colour, boolean is_parked, Timestamp registration_date) {
        this.vehicle_id = vehicle_id;
        this.license_plate = license_plate;
        this.model = model;
        this.brand = brand;
        this.colour = colour;
        this.is_parked = is_parked;
        this.registration_date = registration_date;
    }

    /**
     * Constructor vacío de la clase DTOVehicle.
     */
    public DTOVehicle() {}

    // Getters & Setters

    /**
     * Obtiene el ID del vehículo.
     *
     * @return el ID del vehículo
     */
    public int getId_vehicle() {
        return vehicle_id;
    }

    /**
     * Establece el ID del vehículo.
     *
     * @param vehicle_id el ID del vehículo
     */
    public void setId_vehicle(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    /**
     * Obtiene la matrícula del vehículo.
     *
     * @return la matrícula del vehículo
     */
    public String getLicensePlate() {
        return license_plate;
    }

    /**
     * Establece la matrícula del vehículo.
     *
     * @param license_plate la matrícula del vehículo
     */
    public void setLicensePlate(String license_plate) {
        this.license_plate = license_plate;
    }

    /**
     * Obtiene el modelo del vehículo.
     *
     * @return el modelo del vehículo
     */
    public String getModel() {
        return model;
    }

    /**
     * Establece el modelo del vehículo.
     *
     * @param model el modelo del vehículo
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Obtiene la marca del vehículo.
     *
     * @return la marca del vehículo
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Establece la marca del vehículo.
     *
     * @param brand la marca del vehículo
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Obtiene el color del vehículo.
     *
     * @return el color del vehículo
     */
    public String getColour() {
        return colour;
    }

    /**
     * Establece el color del vehículo.
     *
     * @param colour el color del vehículo
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Indica si el vehículo está aparcado.
     *
     * @return true si el vehículo está aparcado, false en caso contrario
     */
    public boolean isParked() {
        return is_parked;
    }

    /**
     * Establece si el vehículo está aparcado.
     *
     * @param is_parked true si el vehículo está aparcado, false en caso contrario
     */
    public void setParked(boolean is_parked) {
        this.is_parked = is_parked;
    }

    /**
     * Obtiene la fecha de registro del vehículo.
     *
     * @return la fecha de registro del vehículo
     */
    public Timestamp getRegistration_date() {
        return registration_date;
    }

    /**
     * Establece la fecha de registro del vehículo.
     *
     * @param registration_date la fecha de registro del vehículo
     */
    public void setRegistration_date(Timestamp registration_date) {
        this.registration_date = registration_date;
    }
}