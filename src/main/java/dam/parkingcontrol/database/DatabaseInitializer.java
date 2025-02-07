package dam.parkingcontrol.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para inicializar la base de datos.
 * Encargada exclusivamente de crear las tablas y otras operaciones DDL.
 * Define las consultas SQL como constantes.
 *
 * @version 1.0
 */
public class DatabaseInitializer {

    /**
     * Consulta SQL que crea la tabla "Vehicles" cada vez que se inicia la aplicación.
     * La tabla almacena información sobre los vehículos, incluyendo su matrícula, modelo, marca, color,
     * fecha de registro y estado de aparcamiento que es 1 si está aparcado y 0 si no lo está.
     */
    // Creación tabla Vehicles
    private static final String CREATE_VEHICLES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Vehicles ("
            + "vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "license_plate TEXT NOT NULL UNIQUE, "
            + "model TEXT, "
            + "brand TEXT, "
            + "color TEXT, "
            + "registration_date DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + "is_parked INTEGER NOT NULL DEFAULT 1 " // 1 = true, 0 = false
            + ");";

    /**
     * Consulta SQL que crea la tabla "Entry_Exit_Records" cada vez que se inicia la aplicación.
     * La tabla almacena información sobre las entradas y las salidas de vehículos en el parking.
     * La tabla almacena la información sobre el id del vehículo, la entrada y la salida del parking.
     * Se relaciona con la tabla "Vehicles" a través de la foreign key "vehicle_id".
     */
    // Creación tabla Entry_Exit_Records
    private static final String CREATE_ENTRY_EXIT_RECORDS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS Entry_Exit_Records ("
            + "record_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "vehicle_id INTEGER, "
            + "entry_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "
            + "exit_time DATETIME, "
            + "FOREIGN KEY (vehicle_id) REFERENCES Vehicles(vehicle_id) ON DELETE CASCADE"
            + ");";

    /**
     * Crea las tablas de la base de datos
     * En caso de error, se captura la excepción y se muestra un mensaje en la consola.
     */
    public static void createTables() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_VEHICLES_TABLE_SQL);
            System.out.println("Tabla Vehicles OK");
            stmt.execute(CREATE_ENTRY_EXIT_RECORDS_TABLE_SQL);
            System.out.println("Tabla Entry_Exit_Records OK");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }
}