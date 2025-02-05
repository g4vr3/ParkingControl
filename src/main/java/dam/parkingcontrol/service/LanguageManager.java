package dam.parkingcontrol.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static ResourceBundle bundle;
    private static final String BUNDLE_PATH = "resource-bundle.tagsBundle_";

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
        return systemLocale.getLanguage();
    }

    // Cargar el ResourceBundle basado en el idioma seleccionado
    public static void loadLanguage(String languageCode) {
        try {
            // Se carga el ResourceBundle correspondiente
            bundle = ResourceBundle.getBundle(BUNDLE_PATH + languageCode);
        } catch (Exception e) {
            // Carga el idioma español si no puede cargar el seleccionado
            bundle = ResourceBundle.getBundle(BUNDLE_PATH + "es");
        }
    }

    // Obtener el ResourceBundle cargado
    public static ResourceBundle getBundle() {
        return bundle;
    }

    // Obtener el nombre del idioma según el código
    public static String getLanguageNameFromCode(String languageCode) {
        return switch (languageCode) {
            case "es" -> "Español";
            case "en" -> "English";
            case "fr" -> "Francais";
            default -> "Español"; // Valor por defecto si el código no coincide
        };
    }

    // Obtener el código del idioma según el nombre
    public static String getLanguageCodeFromName(String languageName) {
        return switch (languageName) {
            case "Español" -> "es";
            case "English" -> "en";
            case "Francais" -> "fr";
            default -> "es"; // Valor por defecto si el código no coincide
        };
    }

}

