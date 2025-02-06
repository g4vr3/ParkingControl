package dam.parkingcontrol.service;

import dam.parkingcontrol.database.DatabaseConnection;
import net.sf.jasperreports.engine.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar la generación de reportes.
 * Proporciona métodos estáticos para generar reportes con parámetros específicos.
 *
 * @version 1.0
 * @author g4vr3
 */
public class ReportManager {

    /**
     * Genera un reporte diario de los registros del parking.
     */
    public static void generateEndOfDayReport() {
        try {
            String reportPath = "src/main/resources/reports/end_day_report/end_day_report.jasper";
            String outputPath = "src/main/resources/reports/generated-reports/" + LocalDate.now() + "_end_day_report.pdf";

            // Establecer los parámetros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Date", Date.valueOf(LocalDate.now()));

            // Obtener la conexión a la base de datos
            try (Connection conn = DatabaseConnection.connect()) {
                // Llenar el reporte
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);

                // Exportar el reporte a un archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

                // TODO: Gestionar mensajes de éxito y error en la interfaz
                System.out.println("Reporte generado exitosamente: " + outputPath);
            }
        } catch (JRException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }

    /**
     * Genera un reporte con los parámetros de Marca, Modelo y Color.
     */
    public static void generateBrandModelColorReport(String brand, String model, String color) {
        try {
            //Ruta del archivo jasper
            String reportPath = "src/main/resources/reports/brand_model_color_report/brand_model_color_report.jasper";
            //Rutra donde se generará el PDF
            String outputPath = "src/main/resources/reports/generated-reports/" + LocalDate.now() + "_brand_model_color_report.pdf";

            // Establecer los parámetros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Brand", brand);   // Parámetro Marca
            parameters.put("Model", model);   // Parámetro Modelo
            parameters.put("Color", color);   // Parámetro Color

            // Obtener la conexión a la base de datos
            try (Connection conn = DatabaseConnection.connect()) {
                // Llenar el reporte
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);

                // Exportar el reporte a un archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

                // TODO: Gestionar mensajes de éxito y error en la interfaz
                System.out.println("Reporte generado exitosamente: " + outputPath);
            }
        } catch (JRException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}