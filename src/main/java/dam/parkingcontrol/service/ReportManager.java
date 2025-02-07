package dam.parkingcontrol.service;

import dam.parkingcontrol.database.DatabaseConnection;
import dam.parkingcontrol.utils.UserInfo;
import net.sf.jasperreports.engine.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {
    private static final String GENERATED_REPORTS_DIR = "src/main/resources/generated-reports/";

    static {
        mkdir(GENERATED_REPORTS_DIR);
    }

    public static void generateEndOfDayReport() {
        try {
            String reportPath = "src/main/resources/reports/end_day_report/end_day_report.jasper";
            String outputPath = GENERATED_REPORTS_DIR + LocalDate.now() + "_end_day_report.pdf";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Date", Date.valueOf(LocalDate.now()));

            try (Connection conn = DatabaseConnection.connect()) {
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                System.out.println("Reporte generado exitosamente: " + outputPath);
            }
        } catch (JRException | SQLException e) {
            System.err.println("Error al generar el reporte de fin de día: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void generateBrandModelColorReport(String brand, String model, String color) {
        try {
            String reportPath = "src/main/resources/reports/brand_model_color_report/brand_model_color_report.jasper";
            String outputPath = GENERATED_REPORTS_DIR + LocalDate.now() + "_brand_model_color_report.pdf";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Brand", brand);
            parameters.put("Model", model);
            parameters.put("Color", color);

            try (Connection conn = DatabaseConnection.connect()) {
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                System.out.println("Reporte generado exitosamente: " + outputPath);
            }
        } catch (JRException | SQLException e) {
            System.err.println("Error al generar el reporte de vehículos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void generateLoginAuditReport() {
        try {
            String reportDir = "src/main/resources/reports/login_audit_report/";
            mkdir(reportDir);

            String reportPath = reportDir + "login_audit_report.jasper";
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String outputPath = GENERATED_REPORTS_DIR + timestamp + "_IP-" + UserInfo.getUserIP() + "_login_audit_report.pdf";

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IP", UserInfo.getUserIP());
            parameters.put("OS", UserInfo.getOperatingSystem());
            parameters.put("HostName", UserInfo.getHostName());
            parameters.put("MAC", UserInfo.getMacAddress());
            parameters.put("Location", UserInfo.getUserLocation());
            parameters.put("Timestamp", Timestamp.valueOf(LocalDateTime.now()));

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            System.out.println("Reporte de auditoría generado: " + outputPath);
        } catch (JRException e) {
            System.err.println("Error al generar el reporte de auditoría de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mkdir(String dirPath) {
        try {
            Path path = Paths.get(dirPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Error al crear el directorio: " + e.getMessage());
        }
    }
}
