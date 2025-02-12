package dam.parkingcontrol.service;

import java.awt.*;

/**
 * La clase HelpWebService proporciona métodos para abrir páginas web en el navegador predeterminado.
 * @version 1.0
 */
public class HelpWebService {
    /**
     * Abre una página web en el navegador predeterminado.
     *
     * @param url la URL de la página web
     */
    public static void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}