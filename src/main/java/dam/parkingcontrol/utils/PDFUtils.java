package dam.parkingcontrol.utils;

import dam.parkingcontrol.database.DatabaseConnection;
import dam.parkingcontrol.service.LanguageManager;
import javafx.scene.control.Alert;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

/**
 * Clase de utilidades para gestionar ficheros PDF.
 * @version 1.2
 */
public class PDFUtils {

    private static final String PDF_RECURSO = "/user_manual/user_manual.pdf"; // Ruta dentro del JAR
    private static final String PDF_DESTINO = DatabaseConnection.getDbPath().replace("parking.db", "user_manual.pdf"); // Ruta destino

    private static ResourceBundle bundle;

    /**
     * Copia el manual de usuario desde los recursos a la carpeta destino si no existe.
     * Se ejecuta una sola vez al inicio del programa.
     */
    public static void copyUserManualIfNeeded() {
        File pdfFile = new File(PDF_DESTINO);

        // Verifica si el archivo existe y no está vacío (para evitar archivos corruptos)
        if (pdfFile.exists() && pdfFile.length() > 0) {
            System.out.println("El manual de usuario ya está copiado.");
            return; // No copia de nuevo si ya existe y es válido
        }

        System.out.println("Copiando manual de usuario...");
        if (copyPdfFromJar(PDF_RECURSO, PDF_DESTINO)) {
            System.out.println("Manual de usuario copiado con éxito.");
        } else {
            System.err.println("Error al copiar el manual de usuario.");
        }
    }

    /**
     * Abre el manual de usuario en formato PDF.
     */
    public static void openUserManualPdf() {
        File pdfFile = new File(PDF_DESTINO);

        if (!pdfFile.exists() || pdfFile.length() == 0) {
            System.err.println("El archivo PDF no existe o está vacío.");
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                openPdfWithBrowser(pdfFile);
            }
        } catch (IOException e) {
            System.err.println("Error al abrir el PDF. Probando con el navegador...");
            openPdfWithBrowser(pdfFile);
        }
    }

    // Intenta abrir el PDF con el navegador
    private static void openPdfWithBrowser(File pdfFile) {
        try {
            String filePath = pdfFile.getAbsolutePath();

            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "start", filePath).start();
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                new ProcessBuilder("open", filePath).start();
            } else {
                new ProcessBuilder("xdg-open", filePath).start(); // Para Linux
            }
        } catch (IOException e) {
            e.printStackTrace();
            bundle = LanguageManager.getBundle();
            Notifier.showAlert(Alert.AlertType.ERROR, bundle.getString("error_title"), bundle.getString("error_opening_document"), bundle.getString("error_opening_document_content"));
        }
    }

    // Copia el PDF desde el JAR, asegurando que el archivo no esté en uso
    private static boolean copyPdfFromJar(String resourcePath, String destination) {
        File destinoFile = new File(destination);

        try (InputStream in = PDFUtils.class.getResourceAsStream(resourcePath)) {

            if (in == null) {
                System.err.println("No se encontró el recurso: " + resourcePath);
                return false;
            }

            // Asegurar que el archivo destino no esté en uso antes de copiar
            if (destinoFile.exists()) {
                try (OutputStream testOutput = new FileOutputStream(destinoFile, true)) {
                    // Si no lanza excepción, el archivo no está bloqueado
                } catch (IOException e) {
                    System.err.println("El archivo está en uso. No se puede sobrescribir.");
                    return false;
                }
            }

            // Copia el archivo
            Files.copy(in, destinoFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
