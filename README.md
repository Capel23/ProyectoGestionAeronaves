#  Sistema de Gestión de Mantenimiento de Aeronaves

Sistema completo de gestión de mantenimiento aeronáutico desarrollado en **Java 21** con **JavaFX** y **Hibernate**, diseñado para gestionar aeronaves, revisiones técnicas, inventario de piezas y generación de certificados de aeronavegabilidad.

> **Autor:** Luis Capel Velázquez  
> **Tecnologías:** Java 21 | JavaFX | Hibernate 6.6.1 | SQLite | Maven

---

## 🚀 Características Principales

- ✈️ **Gestión de Aeronaves**: Registro, edición y seguimiento de flota
- 🔧 **Control de Revisiones**: Programadas (50h, 100h, Anuales) y correctivas
- ⚙️ **Inventario de Piezas**: Gestión de stock con alertas de bajo inventario
- 📄 **Certificados XML**: Generación automática de Certificados de Aeronavegabilidad
- 📊 **Informes JSON**: Exportación de inventario en formato JSON
- 👥 **Sistema de Roles**: Admin, Mecánico y Piloto con permisos diferenciados
- 🎨 **Interfaz Moderna**: JavaFX con diseño intuitivo y responsive

---

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java JDK 21** o superior ([Descargar](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Descargar](https://maven.apache.org/download.cgi))
- **Git** (opcional, para clonar el repositorio)

### Verificar instalación:

```bash
java -version
mvn -version
```

---

## 🛠️ Instalación y Ejecución

### 1. Clonar o Descargar el Proyecto

```bash
git clone https://github.com/Capel23/ProyectoGestionAeronaves.git
cd ProyectoGestionAeronaves
```

O descarga el ZIP desde GitHub y descomprímelo.

---

### 2. Compilar el Proyecto

```bash
mvn clean install
```

Este comando:
- Descarga todas las dependencias necesarias
- Compila el código fuente
- Genera el proyecto ejecutable

---

### 3. Ejecutar la Aplicación

```bash
mvn javafx:run
```

La aplicación se abrirá automáticamente con la pantalla de login.

---

## 👤 Usuarios de Prueba

Al iniciar la aplicación por primera vez, se crean automáticamente 3 usuarios:

| Usuario    | Contraseña | Rol       | Permisos                                      |
|------------|------------|-----------|-----------------------------------------------|
| `admin`    | `admin123` | Admin     | Acceso completo a todas las funciones         |
| `mecanico` | `mec123`   | Mecánico  | Ver/editar Piezas y Revisiones (sin eliminar) |
| `piloto`   | `pilot123` | Piloto    | Solo lectura (sin edición)                    |

---

## 📊 Datos de Demostración

La primera vez que ejecutas la aplicación, se cargan automáticamente:

- **10 Aeronaves** de Star Wars y películas famosas (Millennium Falcon, X-Wing, etc.)
- **4 Mecánicos** (Han Solo, Chewbacca, Anakin Skywalker, Tony Stark)
- **10 Piezas** (Motor Hiperpropulsor, Escudo Deflector, etc.)
- **3 Revisiones** de ejemplo con observaciones completas

Estos datos son totalmente editables y puedes crear, modificar o eliminar según necesites.

---

## 📂 Estructura del Proyecto

```
ProyectoGestionAeronaves/
│
├── src/main/java/com/aeronautica/
│   ├── MainApp.java              # Clase principal
│   ├── controller/               # Controladores JavaFX
│   │   ├── LoginController.java
│   │   └── MainController.java
│   ├── dao/                      # Acceso a datos (Hibernate)
│   │   ├── AeronaveDAO.java
│   │   ├── MecanicoDAO.java
│   │   ├── PiezaDAO.java
│   │   └── RevisionDAO.java
│   ├── model/                    # Entidades
│   │   ├── Aeronave.java
│   │   ├── Mecanico.java
│   │   ├── Pieza.java
│   │   ├── Revision.java
│   │   └── Usuario.java
│   ├── service/                  # Lógica de negocio
│   └── util/                     # Utilidades (XML, JSON)
│       ├── CertificadoAeronavegabilidadXMLGenerator.java
│       └── InventarioPiezasJSONGenerator.java
│
├── src/main/resources/
│   ├── fxml/                     # Interfaces FXML
│   │   ├── login.fxml
│   │   └── main.fxml
│   ├── styles/                   # CSS
│   │   └── app.css
│   └── hibernate.cfg.xml         # Configuración Hibernate
│
├── pom.xml                       # Dependencias Maven
├── aeronautica.db                # Base de datos SQLite (se crea automáticamente)
└── README.md                     # Este archivo
```

---

## 🎯 Funcionalidades por Módulo

### Aeronaves 🛩
- Crear, editar y eliminar aeronaves
- Registro de matrícula, modelo y horas de vuelo
- Control de estado (Operativa, En Mantenimiento, Fuera de Servicio)
- Búsqueda en tiempo real por matrícula

### Mecánicos 👨‍🔧
- Gestión de personal técnico
- Registro de certificaciones
- Asignación a revisiones

### Piezas ⚙️
- Control de inventario con stock
- Alertas de bajo stock (< 10 unidades)
- Exportación a JSON para reportes externos

### Revisiones 🔧
- Tipos: 50 horas, 100 horas, Anual, Pre-vuelo, Correctiva
- Asignación de mecánico responsable
- Registro de horas acumuladas y observaciones
- Generación de Certificados de Aeronavegabilidad en XML

---

## 📄 Generación de Archivos

### Certificado de Aeronavegabilidad (XML)
1. Ve a la pestaña **Revisiones**
2. Selecciona una revisión
3. Haz clic en **"Generar Certificado XML"**
4. El archivo se guardará en la raíz del proyecto

### Inventario de Piezas (JSON)
1. Ve a la pestaña **Piezas**
2. Haz clic en **"Generar JSON"**
3. El archivo `inventario_piezas.json` se creará en la raíz

---

## 🔧 Solución de Problemas

### La aplicación no inicia
```bash
# Limpiar y recompilar
mvn clean install
mvn javafx:run
```

### Error de base de datos
- Elimina el archivo `aeronautica.db`
- Vuelve a ejecutar la aplicación para regenerar la BD

### Datos de ejemplo no aparecen
- Cierra la aplicación completamente
- Elimina `aeronautica.db`
- Ejecuta nuevamente con `mvn javafx:run`

### Puerto o proceso en uso
- Cierra todas las instancias de la aplicación
- Verifica que no haya procesos Java en ejecución

---

## 🛡️ Sistema de Permisos

| Función              | Admin | Mecánico | Piloto |
|----------------------|:-----:|:--------:|:------:|
| Ver Aeronaves        |   ✅   |    ❌     |   ✅    |
| Crear/Editar Aeronaves|   ✅   |    ❌     |   ❌    |
| Ver Mecánicos        |   ✅   |    ❌     |   ✅    |
| Gestionar Mecánicos  |   ✅   |    ❌     |   ❌    |
| Ver Piezas           |   ✅   |    ✅     |   ✅    |
| Crear/Editar Piezas  |   ✅   |    ✅     |   ❌    |
| Eliminar Piezas      |   ✅   |    ❌     |   ❌    |
| Ver Revisiones       |   ✅   |    ✅     |   ✅    |
| Crear/Editar Revisiones|   ✅   |    ✅     |   ❌    |
| Generar Certificados |   ✅   |    ✅     |   ❌    |

---

## 🧩 Dependencias Principales

El proyecto utiliza las siguientes tecnologías:

- **JavaFX 21.0.5** - Interfaz gráfica
- **Hibernate 6.6.1.Final** - ORM para persistencia
- **SQLite JDBC 3.47.1.0** - Base de datos embebida
- **Jackson 2.18.2** - Procesamiento JSON
- **Maven** - Gestión de dependencias

---

## 📝 Notas Adicionales

- La base de datos SQLite (`aeronautica.db`) se crea automáticamente en la primera ejecución
- Los archivos XML y JSON se generan en la raíz del proyecto
- El sistema utiliza `hbm2ddl.auto=update` para crear/actualizar tablas automáticamente
- Todas las contraseñas se almacenan en texto plano (solo para fines educativos)

---

## 👨‍💻 Desarrollo

### Compilar sin ejecutar:
```bash
mvn clean compile
```

### Ejecutar tests (si existen):
```bash
mvn test
```

### Empaquetar como JAR:
```bash
mvn clean package
```

---

## 📧 Contacto

**Luis Capel Velázquez**  
GitHub: [@Capel23](https://github.com/Capel23)

---

## 📜 Licencia

Este proyecto es de uso académico y educativo.

---

**¡Disfruta gestionando tu flota de aeronaves! ✈️🚀**
