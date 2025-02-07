package dam.parkingcontrol.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * La clase LanguageManager proporciona métodos para cambiar los idiomas.
 *
 * @version 1.0
 */
public class LanguageManager {

    private static ResourceBundle bundle;
    private static final String BUNDLE_PATH = "resource-bundle.tagsBundle_";

    /**
     * Establece el soporte de idioma cargando el idioma predeterminado del sistema.
     */
    public static void setLanguageSupport() {
        loadLanguage(getSystemLanguage());
    }

    // Lista de idiomas soportados
    private static final ObservableList<String> supportedLanguages = FXCollections.observableArrayList(
            "Español", "English", "Francais"
    );

    /**
     * Obtiene la lista de idiomas soportados.
     *
     * @return una lista observable de idiomas soportados.
     */
    public static ObservableList<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    /**
     * Obtiene el idioma predeterminado del sistema.
     *
     * @return el código del idioma predeterminado del sistema.
     */
    public static String getSystemLanguage() {
        Locale systemLocale = Locale.getDefault();
        return systemLocale.getLanguage();
    }

    /**
     * Carga el ResourceBundle basado en el idioma seleccionado.
     *
     * @param languageCode el código del idioma a cargar.
     */
    public static void loadLanguage(String languageCode) {
        try {
            // Se carga el ResourceBundle correspondiente
            bundle = ResourceBundle.getBundle(BUNDLE_PATH + languageCode);
        } catch (Exception e) {
            // Carga el idioma español si no puede cargar el seleccionado
            bundle = ResourceBundle.getBundle(BUNDLE_PATH + "es");
        }
    }

    /**
     * Obtiene el ResourceBundle cargado.
     *
     * @return el ResourceBundle cargado.
     */
    public static ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Obtiene el nombre del idioma según el código.
     *
     * @param languageCode el código del idioma.
     * @return el nombre del idioma.
     */
    public static String getLanguageNameFromCode(String languageCode) {
        return switch (languageCode) {
            case "es" -> "Español";
            case "en" -> "English";
            case "fr" -> "Francais";
            default -> "Español"; // Valor por defecto si el código no coincide
        };
    }

    /**
     * Obtiene el código del idioma según el nombre.
     *
     * @param languageName el nombre del idioma.
     * @return el código del idioma.
     */
    public static String getLanguageCodeFromName(String languageName) {
        return switch (languageName) {
            case "Español" -> "es";
            case "English" -> "en";
            case "Francais" -> "fr";
            default -> "es"; // Valor por defecto si el código no coincide
        };
    }
}