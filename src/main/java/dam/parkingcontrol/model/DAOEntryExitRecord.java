package dam.parkingcontrol.model;

import dam.parkingcontrol.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * La clase DAOEntryExitRecord proporciona métodos para registrar entradas y salidas de vehículos en la base de datos.
 *
 * @version 1.1
 */
public class DAOEntryExitRecord {
    // Constante para almacenar la URL de la base de datos
    private static final String DB_URL = DatabaseConnection.getDbUrl();

    /**
     * Registra una entrada de vehículo en la base de datos.
     *
     * @param entryToRegister el objeto DTOEntryExitRecord que contiene los datos de la entrada a registrar. Solo se utilizarán los campos vehicle_id y entry_time
     * @return true si la entrada se registró exitosamente, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public static boolean registerEntry(DTOEntryExitRecord entryToRegister) throws SQLException {
        String sql = "INSERT INTO Entry_Exit_Records(vehicle_id, entry_time) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, entryToRegister.getVehicle_id());
            stmt.setTimestamp(2, entryToRegister.getEntry_time());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        entryToRegister.setRecord_id(generatedId);
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Registra una salida de vehículo en la base de datos.
     *
     * @param exitToRegister el objeto DTOEntryExitRecord que contiene los datos de la salida a registrar. Solo utilizará el ID del vehículo y la hora de salida
     * @return true si la salida se registró exitosamente, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    public static boolean registerExit(DTOEntryExitRecord exitToRegister) throws SQLException {
        String sql = "UPDATE Entry_Exit_Records SET exit_time = ? WHERE vehicle_id = ? AND exit_time IS NULL";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, exitToRegister.getExit_time());
            stmt.setInt(2, exitToRegister.getVehicle_id());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }
    }

    /**
     * Actualiza todos los registros de salida en la tabla Entry_Exit_Records que aún no tienen una fecha de salida.
     *
     * <p> Busca todas las filas donde el campo {exit_time} es {NULL} y les asigna la fecha
     * y hora actuales. Se utiliza un {@link PreparedStatement} para realizar la actualización en la base de datos.</p>
     *
     * @return {true} si al menos un registro fue actualizado, {@code false} si no había registros pendientes de salida.
     * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
     */
    public static boolean updateAllExitsToCurrentDate() throws SQLException {
        String sql = "UPDATE Entry_Exit_Records SET exit_time = ? WHERE exit_time IS NULL";
        Timestamp currentDate = Timestamp.valueOf(LocalDateTime.now());

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, currentDate);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        }
    }
}