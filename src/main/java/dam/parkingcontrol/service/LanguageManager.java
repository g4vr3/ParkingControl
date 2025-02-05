package dam.parkingcontrol.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static ResourceBundle bundle;
    private static final String BUNDLE_PATH = "tagsBundle_";

    public static void setLanguageSupport() {
        loadLanguage(getSystemLanguage());
    }

    // Lista de idiomas soportados
    private static final ObservableList<String> supportedLanguages = FXCollections.observableArrayList(
            "Español", "English", "Francais"
    );

    public static ObservableList<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    // Obtener el idioma predeterminado del sistema
    public static String getSystemLanguage() {
        Locale systemLocale = Locale.getDefault();
        return switch (systemLocale.getLanguage()) {
            case "es" -> "Español";
            case "en" -> "English";
            case "fr" -> "Francais";
            default -> "Español"; // Español por defecto si no está soportado
        };
    }

    // Cargar el ResourceBundle basado en el idioma seleccionado
    public static void loadLanguage(String language) {
        // Verifica si el idioma seleccionado es soportado, si no carga Español por defecto
        String languageCode = switch (language) {
            case "Español" -> "es";
            case "English" -> "en";
            case "Francais" -> "fr";
            default -> "es"; // Español por defecto si el idioma no está soportado
        };

        try {
            // Se carga el ResourceBundle correspondiente
            bundle = ResourceBundle.getBundle("resource-bundle.tagsBundle_" + languageCode);
        } catch (Exception e) {
            // Si ocurre un error, carga el español por defecto y muestra un mensaje en consola
            System.err.println("Error al cargar el idioma: " + language + ". Cargando español por defecto.");
            bundle = ResourceBundle.getBundle("resource-bundle.tagsBundle_es");
        }
    }

    // Obtener el ResourceBundle cargado
    public static ResourceBundle getBundle() {
        return bundle;
    }
}

