# ArrulloPark

ArrulloPark es un sistema de control de estacionamiento desarrollado en **Java 23**. Este proyecto incluye una aplicación de escritorio que permite gestionar el acceso y control de vehículos en un parqueo de manera eficiente y sencilla.

## 📌 Requisitos
- **Java 23**

## 🚀 Instalación
Para instalar **ArrulloPark**, puede descargar el instalador desatendido desde el siguiente enlace. Este instalador no requiere tener Java instalado previamente:

➡️ **[Descargar Instalador](https://drive.google.com/file/d/193kgolyDUKcshlOc0yXj6M1XeigsAyWm/view?usp=sharing)**

## 📂 Estructura del Proyecto
El proyecto tiene la siguiente estructura:
```
ArrulloPark/
├── index.html # Landing page de ayuda
├── login-help.html # Otras páginas de ayuda
├── panel-access-help.html # Otras páginas de ayuda
├── terms-policies.html # Otras páginas de ayuda
├── css/                 # Archivos CSS para el estilo de la web de ayuda
├── docs/                # Documentación del proyecto
│   ├── ArrulloPark.pdf  # Documento con detalles del proyecto
├── images/              # Imágenes utilizadas en la aplicación
├── src/                 # Código fuente del proyecto
│   ├── main/           
│   │   ├── java/        
│   │   │   ├── dam.parkingcontrol.controller/   # Controladores
│   │   │   │   ├── BrandModelColorReportController.java  # Controlador para informes filtrados
│   │   │   ├── dam.parkingcontrol.database/     # Gestión de base de datos
│   │   │   │   ├── DatabaseConnection.java      # Conexión a la base de datos
│   │   │   │   ├── DatabaseInitializer.java     # Inicialización de la base de datos
│   │   │   ├── dam.parkingcontrol.model/       # Modelos de datos
│   │   │   │   ├── DTOEntryExitRecord.java     # Registro de entrada y salida de vehículos
│   │   │   │   ├── DTOVehicle.java             # Inicialización de objetos de vehículo
│   │   │   ├── dam.parkingcontrol.service/     # Servicios de la aplicación
│   │   │   │   ├── LanguageManager.java        # Gestión de idiomas
│   │   │   │   ├── ParkingManager.java         # Gestión del parking
│   │   │   │   ├── ReportManager.java          # Creación de reportes
│   │   │   │   ├── ViewManager.java            # Gestión de vistas
│   │   │   ├── dam.parkingcontrol.utils/       # Utilidades
│   │   │   │   ├── PDFUtils.java               # Gestión de ficheros PDF
│   │   │   │   ├── UserInfo.java               # Información del sistema que está ejecutando la aplicación (Utilizado para reportes de auditoría)
├── resources/            # Recursos del proyecto
│   ├── reports/         # Plantillas de reportes
│   ├── styles/          # Archivos CSS
│   │   ├── styles.css   # Estilos de la aplicación
```

## ✨ Características
- **Control de acceso:** Gestión de entrada y salida de vehículos en el estacionamiento.
- **Reportes:** Genera informes en formato PDF sobre la actividad del estacionamiento.
- **Interfaz amigable:** Diseño intuitivo y fácil de usar.
- **Soporte multilenguaje**
- **Informes con filtros:** Generación de reportes filtrados por marca, modelo y color.

## 📖 Información Adicional
Para más detalles sobre el proyecto, consulte el [documento sobre el proyecto](https://drive.google.com/file/d/1bA5GZySgZA86t8Jo4llZQhKSc3dYl6qW/view?usp=sharing)

## 👥 Autores
- **[Izan (g4vr3)](https://github.com/g4vr3)**
- **[Rubén Velasco](https://github.com/rbeenv)**
- **[Javier Agudo](https://github.com/jagudo27)**
- **[Victor Almena](https://github.com/Viictor29)**

## 📜 Licencia
Este proyecto se distribuye bajo la licencia **MIT**.
