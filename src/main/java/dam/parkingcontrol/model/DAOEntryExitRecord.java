package dam.parkingcontrol.model;

import dam.parkingcontrol.database.DatabaseConnection;

import java.sql.*;

/**
 * La clase DAOEntryExitRecord proporciona métodos para registrar entradas y salidas de vehículos en la base de datos.
 *
 * @version 1.0
 * @author g4vr3
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
        String sql = "INSERT INTO EntryExitRecords(vehicle_id, entry_time) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Establecer parámetros
            stmt.setInt(1, entryToRegister.getVehicle_id());
            stmt.setDate(2, Date.valueOf(entryToRegister.getEntry_time()));

            // Ejecutar la consulta y obtener el número de filas afectadas
            int rowsAffected = stmt.executeUpdate();

            // Establecer al DTO el ID autogenerado si se insertaron filas
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1); // Obtener el ID generado
                        entryToRegister.setRecord_id(generatedId); // Establecer el ID en el DTO
                        return true; // La entrada se registró exitosamente
                    }
                }
            }
            return false; // Si no se logró guardar o generar el id
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
        String sql = "UPDATE EntryExitRecords SET exit_time = ? WHERE vehicle_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer parámetros
            stmt.setDate(1, Date.valueOf(exitToRegister.getExit_time()));
            stmt.setInt(2, exitToRegister.getVehicle_id());

            // Ejecutar la consulta y obtener el número de filas afectadas
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Si se actualizó al menos una fila
        }
    }
}