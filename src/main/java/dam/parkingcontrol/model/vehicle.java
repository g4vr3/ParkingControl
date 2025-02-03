package dam.parkingcontrol.model;

import java.time.LocalDate;

public class vehicle {

    // Variables básicas para usar los coches en el parking
    private int vehicle_id;
    private String license_plate, model, brand, colour;
    private Boolean is_parked;
    private LocalDate registration_date;

    // Constructor con Atributos y vacío de la clase vehicle.java
    public vehicle(int vehicle_id, String license_plate, String model, String brand, String colour, Boolean is_parked, LocalDate register_date) {
        this.vehicle_id = vehicle_id;
        this.license_plate = license_plate;
        this.model = model;
        this.brand = brand;
        this.colour = colour;
        this.is_parked = is_parked;
        this.registration_date = register_date;
    }
    public vehicle() {}

    // Getters & Setters
    public int getId_vehicle() {
        return vehicle_id;
    }
    public void setId_vehicle(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getLicensePlate() {
        return license_plate;
    }
    public void setLicensePlate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }

    public Boolean getStatus() {
        return is_parked;
    }
    public void setStatus(Boolean is_parked) {
        this.is_parked = is_parked;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }
    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
}
