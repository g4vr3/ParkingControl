package dam.parkingcontrol.utils;

import dam.parkingcontrol.database.DatabaseConnection;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PDFUtils {

    private static final String PDF_RECURSO = "/user_manual/user_manual.pdf"; // Ruta dentro del JAR
    private static final String PDF_DESTINO = DatabaseConnection.getDbPath().replace("parking.db", "user_manual.pdf"); // Nombre del archivo copiado

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
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.err.println("Abrir archivos no es compatible en esta plataforma.");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
