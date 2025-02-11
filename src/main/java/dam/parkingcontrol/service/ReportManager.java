package dam.parkingcontrol.service;

import dam.parkingcontrol.database.DatabaseConnection;
import dam.parkingcontrol.utils.Notifier;
import dam.parkingcontrol.utils.UserInfo;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * La clase ReportManager proporciona métodos para crear reportes sobre la aplicación.
 *
 * @version 1.1
 */
public class ReportManager {

    private static ResourceBundle bundle;
    private static final String GENERATED_REPORTS_DIR = "src/main/resources/generated-reports/";

    static {
        mkdir(GENERATED_REPORTS_DIR);
    }

    /**
     * Genera un reporte de fin de día y lo guarda en formato PDF en la ruta elegida por el usuario.
     */
    public static void generateEndOfDayReport() {
        try {
            bundle = LanguageManager.getBundle();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(bundle.getString("save_report_title_text"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

            // Establecer el nombre de archivo por defecto
            String defaultFileName = "end_day_report_" + LocalDate.now() + ".pdf";
            fileChooser.setInitialFileName(defaultFileName);

            // Mostrar el diálogo de guardar archivo
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                String filePath = file.getAbsolutePath();
                String reportPath = "src/main/resources/reports/end_day_report/end_day_report.jasper";

                try (Connection conn = DatabaseConnection.connect()) {
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap<>(), conn);
                    JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
                    Notifier.showAlert(Alert.AlertType.INFORMATION, bundle.getString("generated_report_success_title"), bundle.getString("generated_report_success_header"), bundle.getString("generated_report_success_content"));
                }
            }
        } catch (JRException | SQLException e) {
            bundle = LanguageManager.getBundle();
            Notifier.showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("generating_report_error_header"), bundle.getString("generating_report_error_content"));
            e.printStackTrace();
        }
    }

    /**
     * Genera un reporte basado en la marca, modelo y color de los vehículos y lo guarda en formato PDF.
     *
     * @param brand la marca del vehículo
     * @param model el modelo del vehículo
     * @param color el color del vehículo
     */
    public static void generateBrandModelColorReport(String brand, String model, String color) {
        try {
            bundle = LanguageManager.getBundle();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(bundle.getString("save_report_title_text"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

            // Establecer el nombre de archivo por defecto
            String defaultFileName = "brand_model_color_report_" + LocalDate.now() + ".pdf";
            fileChooser.setInitialFileName(defaultFileName);

            // Mostrar el diálogo de guardar archivo
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                String filePath = file.getAbsolutePath();
                String reportPath = "src/main/resources/reports/brand_model_color_report/brand_model_color_report.jasper";

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("Brand", brand);
                parameters.put("Model", model);
                parameters.put("Color", color);

                try (Connection conn = DatabaseConnection.connect()) {
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conn);
                    JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
                }
            }
        } catch (JRException | SQLException e) {
            bundle = LanguageManager.getBundle();
            Notifier.showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("generating_report_error_header"), bundle.getString("generating_report_error_content"));
            e.printStackTrace();
        }
    }

    /**
     * Genera un reporte de auditoría de login y lo guarda en formato PDF.
     */
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

    /**
     * Crea un directorio si no existe.
     *
     * @param dirPath la ruta del directorio a crear
     */
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