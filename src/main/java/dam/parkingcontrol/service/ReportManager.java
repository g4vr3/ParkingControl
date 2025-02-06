package dam.parkingcontrol.service;

import dam.parkingcontrol.database.DatabaseConnection;
import dam.parkingcontrol.utils.UserInfo;
import net.sf.jasperreports.engine.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar la generación de reportes.
 * Proporciona métodos estáticos para generar reportes con parámetros específicos.
 *
 * @version 2.0
 * @author g4vr3
 */
public class ReportManager {

    /**
     * Genera un reporte diario de los registros del parking.
     */
    public static void generateEndOfDayReport() {
        try {
            String reportPath = "src/main/resources/reports/end_day_report/end_day_report.jasper";
            String outputPath = "src/main/resources/generated-reports/" + LocalDate.now() + "_end_day_report.pdf";

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
     * Genera un reporte de auditoría de inicio de sesión con información del usuario que está intentando acceder.
     */
    public static void generateLoginAuditReport() {
        try {
            String reportPath = "src/main/resources/reports/login_audit_report/login_audit_report.jasper";
            String outputPath = "src/main/resources/generated-reports/" + LocalDateTime.now() + "_IP-" + UserInfo.getUserIP() + "_login_audit_report.pdf";

            // Establecer los parámetros
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IP", UserInfo.getUserIP());
            parameters.put("OS", UserInfo.getOperatingSystem());
            parameters.put("HostName", UserInfo.getHostName());
            parameters.put("MAC", UserInfo.getMacAddress());
            parameters.put("Location", UserInfo.getUserLocation());
            parameters.put("Timestamp", Timestamp.valueOf(LocalDateTime.now()));

            // Llenar el reporte sin conexión a la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, new JREmptyDataSource());

            // Exportar el reporte a un archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}