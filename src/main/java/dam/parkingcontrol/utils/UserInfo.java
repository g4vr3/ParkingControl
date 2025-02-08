package dam.parkingcontrol.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * La clase UserInfo proporciona información sobre el ordenador que está ejecutando el programa.
 *
 * @version 1.0
 */
public class UserInfo {

    /**
     * Devuelve la IP del usuario con formato String.
     *
     * @return la IP del Usuario si funciona o un String por defecto como "IP not available".
     */
    public static String getUserIP() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "IP not available";
        }
    }

    /**
     * Devuelve el sistema operativo donde se ejecuta el programa.
     *
     * @return un String con el sistema operativo en el que se esté ejecutando el programa.
     */
    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    /**
     * Devuelve la ubicación del usuario basada en la configuración regional.
     *
     * @return la ubicación del usuario en formato "País (Código de país)".
     */
    public static String getUserLocation() {
        Locale locale = Locale.getDefault();
        return locale.getDisplayCountry() + " (" + locale.getCountry() + ")";
    }

    /**
     * Devuelve la dirección MAC del usuario.
     *
     * @return la dirección MAC del usuario si funciona o un String por defecto como "MAC address not available".
     */
    public static String getMacAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            if (mac == null) {
                return "MAC address not available";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
            return "MAC address not available";
        }
    }

    /**
     * Devuelve el nombre del host del usuario.
     *
     * @return el nombre del host del usuario si funciona o un String por defecto como "Host name not available".
     */
    public static String getHostName() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Host name not available";
        }
    }
}