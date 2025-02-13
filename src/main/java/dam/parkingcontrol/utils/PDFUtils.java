package dam.parkingcontrol.utils;

import dam.parkingcontrol.database.DatabaseConnection;
import dam.parkingcontrol.service.LanguageManager;
import javafx.scene.control.Alert;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

/**
 * Clase de utilidades para abrir ficheros en formato PDF.
 * @version 1.0.1
 */
public class PDFUtils {

    private static final String PDF_RECURSO = "/user_manual/user_manual.pdf"; // Ruta dentro del JAR
    private static final String PDF_DESTINO = DatabaseConnection.getDbPath().replace("parking.db", "user_manual.pdf"); // Nombre del archivo copiado

    private static ResourceBundle bundle;

    /**
     * Abre el manual de usuario en formato PDF.
     */
    public static void openUserManualPdf() {
        File pdfFile = new File(PDF_DESTINO);

        // Copiar el PDF si no existe
        if (!pdfFile.exists()) {
            if (!copyPdfFromJar(PDF_RECURSO, PDF_DESTINO)) {
                System.err.println("No se pudo copiar el PDF.");
                return;
            }
        }

        // Intentar abrir el PDF
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile); // Intentar abrir con el predeterminado
            } else {
                System.out.println("Desktop no es compatible. Probando con el navegador...");
                openPdfWithBrowser(pdfFile);
            }
        } catch (IOException e) {
            System.err.println("Error al abrir con Desktop. Probando con el navegador...");
            openPdfWithBrowser(pdfFile);
        }
    }

    // Abrir el navegador manualmente
    private static void openPdfWithBrowser(File pdfFile) {
        try {
            String filePath = pdfFile.getAbsolutePath();

            // Verifica el sistema operativo
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "start", filePath).start();
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                new ProcessBuilder("open", filePath).start();
            } else {
                // Linux (usa xdg-open que suele estar disponible)
                new ProcessBuilder("xdg-open", filePath).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("No se pudo abrir el PDF.");

            bundle = LanguageManager.getBundle();
            Notifier.showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("error_opening_document"), bundle.getString("error_opening_document_content"));
        }
    }

    private static boolean copyPdfFromJar(String resourcePath, String destination) {
        try (InputStream in = PDFUtils.class.getResourceAsStream(resourcePath);
             OutputStream out = new FileOutputStream(destination)) {

            if (in == null) {
                System.err.println("No se encontró el recurso: " + resourcePath);
                return false;
            }

            Files.copy(in, new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
