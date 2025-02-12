package dam.parkingcontrol.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para la conexión a la base de datos.
 * Encargada exclusivamente de gestionar la conexión a la base de datos.
 * Configura la ruta de la base de datos y crea el directorio si es necesario.
 *
 * @version 1.0
 */
public class DatabaseConnection {
    private static final String dbPath;

    static {
        String userHome = System.getProperty("user.home");

        // En Windows, se usa AppData para almacenar la base de datos
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            dbPath = userHome + "\\AppData\\Local\\ParkingControl\\parking.db";

            // En macOS, se usa Library/Application Support
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            dbPath = userHome + "/Library/Application Support/ParkingControl/parking.db";

            // En Linux, se usa .local/share
        } else {
            dbPath = userHome + "/.local/share/ParkingControl/parking.db";
        }

        // Crear directorio si no existe
        File dbDir = new File(dbPath).getParentFile();
        if (!dbDir.exists() && dbDir.mkdirs()) {
            System.out.println("Directorio creado: " + dbDir.getAbsolutePath());
        }

        // Log de la ruta de la base de datos
        System.out.println("Ruta de la base de datos: " + dbPath);
    }

    public static final String pathJasper = dbPath.replace("parking.db","");

    /**
     * Obtiene la URL de la base de datos.
     *
     * @return la URL de la base de datos
     */
    public static String getDbUrl() {
        return "jdbc:sqlite:" + dbPath;
    }

    /**
     * Establece una conexión con la base de datos SQLite.
     *
     * @return la conexión a la base de datos
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(getDbUrl());
    }
}