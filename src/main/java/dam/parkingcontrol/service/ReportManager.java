package dam.parkingcontrol.service;

import dam.parkingcontrol.database.DatabaseConnection;
import dam.parkingcontrol.utils.Notifier;
import dam.parkingcontrol.utils.UserInfo;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import static dam.parkingcontrol.utils.Notifier.showAlert;

/**
 * La clase ReportManager proporciona métodos para crear reportes sobre la aplicación.
 *
 * @version 1.1.1
 */
public class ReportManager {

    private static ResourceBundle bundle;
    private static final String dataPath = DatabaseConnection.getDbPath().replace("parking.db", "");

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
                InputStream reportStream = ReportManager.class.getResourceAsStream("/reports/end_day_report/end_day_report.jasper");

                if (reportStream == null) {
                    throw new IOException("No se encontró el archivo de reporte en el classpath");
                }

                // Cargar la imagen usando getResourceAsStream
                Image logoImage = getLogoImage();

                Map<String, Object> parameters = new HashMap<>();
                // Pasar la imagen al parámetro del reporte
                parameters.put("logoPath", logoImage);

                try (Connection conn = DatabaseConnection.connect()) {
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, conn);
                    JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
                    Notifier.showAlert(Alert.AlertType.INFORMATION, bundle.getString("generated_report_success_title"), bundle.getString("generated_report_success_header"), bundle.getString("generated_report_success_content"));
                }
            }
        } catch (JRException | SQLException e) {
            bundle = LanguageManager.getBundle();
            Notifier.showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("generating_report_error_header"), bundle.getString("generating_report_error_content"));
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            StringBuilder mensajeBuilder = new StringBuilder();
            fileChooser.setTitle(bundle.getString("save_report_title_text"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));

            String defaultFileName = "";
            // Establecer el nombre de archivo según parámetros introducidos
            if (!(brand == null)) {
                defaultFileName += brand.replace(" ", "_") + "_";
                mensajeBuilder.append(bundle.getString("brand_prompt")).append(" ").append(brand);
            }
            if (!(model == null)) {
                defaultFileName += model.replace(" ", "_") + "_";
                mensajeBuilder.append("\n").append(bundle.getString("model_prompt")).append(" ").append(model);
            }
            if (!(color == null)) {
                defaultFileName += color.replace(" ", "_") + "_";
                mensajeBuilder.append("\n").append(bundle.getString("color_prompt")).append(" ").append(color);
            }
            defaultFileName += LocalDate.now() + ".pdf";

            fileChooser.setInitialFileName(defaultFileName);

            // Mostrar el diálogo de guardar archivo
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                String filePath = file.getAbsolutePath();
                InputStream reportStream = ReportManager.class.getResourceAsStream("/reports/brand_model_color_report/brand_model_color_report.jasper");

                // Cargar la imagen usando getResourceAsStream
                Image logoImage = getLogoImage();

                Map<String, Object> parameters = new HashMap<>();
                // Pasar la imagen al parámetro del reporte
                parameters.put("logoPath", logoImage);
                parameters.put("Brand", brand);
                parameters.put("Model", model);
                parameters.put("Color", color);

                try (Connection conn = DatabaseConnection.connect()) {
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, conn);
                    JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
                    showAlert(Alert.AlertType.INFORMATION, bundle.getString("brand_model_color_alert_title"), bundle.getString("brand_model_color_header_text"), mensajeBuilder.toString());

                }
            }
        } catch (JRException | SQLException e) {
            bundle = LanguageManager.getBundle();
            Notifier.showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("generating_report_error_header"), bundle.getString("generating_report_error_content"));
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Genera un reporte de auditoría de login y lo guarda en formato PDF.
     */
    public static void generateLoginAuditReport() {
        try {

            InputStream reportStream = ReportManager.class.getResourceAsStream("/reports/login_audit_report/login_audit_report.jasper");
            if (reportStream == null) {
                throw new IOException("No se encontró el archivo de reporte en el classpath");
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String outputPath = dataPath + timestamp + "_IP-" + UserInfo.getUserIP() + "_login_audit_report.pdf";

            // Obtener la imagen del logo
            Image logoImage = getLogoImage();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("logoPath", logoImage);
            parameters.put("IP", UserInfo.getUserIP());
            parameters.put("OS", UserInfo.getOperatingSystem());
            parameters.put("HostName", UserInfo.getHostName());
            parameters.put("MAC", UserInfo.getMacAddress());
            parameters.put("Location", UserInfo.getUserLocation());
            parameters.put("Timestamp", Timestamp.valueOf(LocalDateTime.now()));

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
            System.out.println("Reporte de auditoría generado: " + outputPath);
        } catch (JRException e) {
            System.err.println("Error al generar el reporte de auditoría de login: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Image getLogoImage() throws IOException {
        // Cargar la imagen usando getResourceAsStream
        InputStream inputStream = ReportManager.class.getResourceAsStream("/images/parkingLogo.png");
        if (inputStream == null) {
            throw new IOException("No se encontró el archivo de imagen en el classpath");
        }

        // Usar ImageIO para leer la imagen directamente como un java.awt.Image
        return ImageIO.read(inputStream);
    }
}