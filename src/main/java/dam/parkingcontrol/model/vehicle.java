package dam.parkingcontrol.model;

import java.time.LocalDate;

public class vehicle {

    // Basic variables to use vehicles into the parking
    private int id_vehicle;
    private String licensePlate, model, brand, colour;
    private Boolean status;
    private LocalDate register_date;

    // Empty and Filled constructor to create vehicle objects
    public vehicle(int id_vehicle, String licensePlate, String model, String brand, String colour, Boolean status, LocalDate register_date) {
        this.id_vehicle = id_vehicle;
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
        this.colour = colour;
        this.status = status;
        this.register_date = register_date;
    }
    public vehicle() {}

    // Getters & Setters
    public int getId_vehicle() {
        return id_vehicle;
    }
    public void setId_vehicle(int id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getRegister_date() {
        return register_date;
    }
    public void setRegister_date(LocalDate register_date) {
        this.register_date = register_date;
    }
}
