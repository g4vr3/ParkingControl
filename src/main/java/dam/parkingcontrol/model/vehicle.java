package dam.parkingcontrol.model;

import java.time.LocalDate;

public class vehicle {

    // Basic variables to use vehicles into the parking
    private int vehicle_id;
    private String license_Plate, model, brand, colour;
    private Boolean isParked;
    private LocalDate registration_date;

    // Empty and Filled constructor to create vehicle objects
    public vehicle(int vehicle_id, String license_Plate, String model, String brand, String colour, Boolean isParked, LocalDate register_date) {
        this.vehicle_id = vehicle_id;
        this.license_Plate = license_Plate;
        this.model = model;
        this.brand = brand;
        this.colour = colour;
        this.isParked = isParked;
        this.registration_date = register_date;
    }
    public vehicle() {}

    // Getters & Setters
    public int getId_vehicle() {
        return vehicle_id;
    }
    public void setId_vehicle(int id_vehicle) {
        this.vehicle_id = id_vehicle;
    }

    public String getLicensePlate() {
        return license_Plate;
    }
    public void setLicensePlate(String license_Plate) {
        this.license_Plate = license_Plate;
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
        return isParked;
    }
    public void setStatus(Boolean status) {
        this.isParked = status;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }
    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
}
