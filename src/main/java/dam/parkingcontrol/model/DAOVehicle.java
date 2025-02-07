package dam.parkingcontrol.model;

import dam.parkingcontrol.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

/**
 * La clase DAOVehicle proporciona métodos para registrar, actualizar y eliminar registros de vehículos en la base de datos.
 *
 * @version 1.0
 */
public class DAOVehicle {
    // Constante para almacenar la URL de la base de datos
    private static final String DB_URL = DatabaseConnection.getDbUrl();

    /**
     * Registra un nuevo vehículo en la base de datos.
     *
     * @param vehicleToRegister el vehículo a registrar
     * @return true si el vehículo se registró exitosamente, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public static boolean registerVehicle(DTOVehicle vehicleToRegister) throws SQLException {
        String sql = "INSERT INTO Vehicles(license_plate, model, brand, colour, registration_date, is_parked) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Establecer parámetros
            stmt.setString(1, vehicleToRegister.getLicensePlate());
            stmt.setString(2, vehicleToRegister.getModel());
            stmt.setString(3, vehicleToRegister.getBrand());
            stmt.setString(4, vehicleToRegister.getColour());
            stmt.setDate(5, Date.valueOf(vehicleToRegister.getRegistration_date()));
            stmt.setBoolean(6, vehicleToRegister.isParked());

            // Ejecutar la consulta y obtener el número de filas afectadas
            int rowsAffected = stmt.executeUpdate();

            // Establecer al DTO el ID autogenerado si se insertaron filas
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Obtener el ID generado
                        vehicleToRegister.setId_vehicle(generatedId); // Establecer el ID en el DTO
                        return true; // El vehículo se registró exitosamente
                    }
                }
            }
            return false; // Si no se logró guardar o generar el id
        }
    }

    /**
     * Actualiza el estado de aparcado de un vehículo en la base de datos.
     *
     * @param vehicleToUpdate el vehículo a actualizar
     * @return true si el estado del vehículo se actualizó exitosamente, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public static boolean updateVehicleStatus(DTOVehicle vehicleToUpdate) throws SQLException {
        String sql = "UPDATE Vehicles SET is_parked = ? WHERE vehicle_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer parámetros
            stmt.setBoolean(1, vehicleToUpdate.isParked());
            stmt.setInt(2, vehicleToUpdate.getId_vehicle());

            // Ejecutar la consulta y obtener el número de filas afectadas
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Si se actualizó al menos una fila
        }
    }

    /**
     * Elimina el registro de un vehículo de la base de datos.
     *
     * @param vehicleToDelete el vehículo a eliminar
     * @return true si el vehículo se eliminó exitosamente, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public static boolean deleteVehicle(DTOVehicle vehicleToDelete) throws SQLException {
        String sql = "DELETE FROM Vehicles WHERE vehicle_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer parámetros
            stmt.setInt(1, vehicleToDelete.getId_vehicle());

            // Ejecutar la consulta y obtener el número de filas afectadas
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Si se eliminó al menos una fila
        }
    }

    public static ArrayList<DTOVehicle> getAllVehicles() {
        ArrayList<DTOVehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";
        try (Connection conn = DriverManager.getConnection(DB_URL)){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt("vehicle_id");
                String licensePlate = rs.getString("license_plate");
                String model = rs.getString("model");
                String brand = rs.getString("brand");
                String colour = rs.getString("color");
                boolean parked = rs.getBoolean("is_parked");
                LocalDate registration_date = rs.getDate("registration_date").toLocalDate();
                DTOVehicle vehicle = new DTOVehicle(id,licensePlate,model,brand,colour,parked,registration_date);
                vehicles.add(vehicle);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vehicles;
    }
}