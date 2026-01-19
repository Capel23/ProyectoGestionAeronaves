#  Sistema de Gestión de Mantenimiento de Aeronaves

Proyecto académico desarrollado en **Java 21** para gestionar el mantenimiento técnico de aeronaves, con soporte para registro de revisiones, control de piezas reemplazadas, generación de certificados de aeronavegabilidad y trazabilidad de responsables autorizados.

> Autor: Luis Capel Velázquez  
> Entorno: Windows 11 | Java 21.0.8 | MySQL (XAMPP) | Hibernate ORM 6.6.1.Final | JavaFX

---

##  Funcionalidades

- Registro y gestión de aeronaves (matrícula, modelo, horas de vuelo)
- Control de revisiones técnicas programadas y no programadas
- Gestión de inventario de piezas mediante archivos JSON
- Generación automática de **Certificados de Aeronavegabilidad** en formato XML
- Gestión de técnicos y responsables con firma digital implícita
- Informes detallados por aeronave, técnico o período
- Interfaz gráfica moderna con **JavaFX** (tema claro/oscuro, validación en tiempo real)

---

># Aeronautica Maintenance System

Sistema de gestión de aeronaves desarrollado en **Java** con **JavaFX** y **Hibernate**, conectado a **MySQL**.

---

### 2. Configurar la base de datos
1. Abrir MySQL Workbench y conectarte como `root` (si no tiene contraseña, se deja vacío).
2. Crear la base de datos:

```sql
CREATE DATABASE aeronautica_db;
Crear usuario y dar permisos:

sql
Copiar código
DROP USER IF EXISTS 'aero_user'@'localhost';

CREATE USER 'aero_user'@'localhost' IDENTIFIED BY 'aero1234';

GRANT ALL PRIVILEGES ON aeronautica_db.* TO 'aero_user'@'localhost';

FLUSH PRIVILEGES;
Verificar que los permisos se hayan aplicado:

sql
Copiar código
SHOW GRANTS FOR 'aero_user'@'localhost';
Debe aparecer algo como:

pgsql
Copiar código
GRANT ALL PRIVILEGES ON `aeronautica_db`.* TO 'aero_user'@'localhost'
3. Configurar Hibernate
En src/main/resources/hibernate.cfg.xml asegúrate de que las credenciales sean correctas:

xml
Copiar código
<property name="hibernate.connection.url">
    jdbc:mysql://localhost:3306/aeronautica_db?useSSL=false&serverTimezone=UTC
</property>
<property name="hibernate.connection.username">aero_user</property>
<property name="hibernate.connection.password">aero1234</property>
<property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
<property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
<property name="hibernate.show_sql">true</property>
<property name="hibernate.hbm2ddl.auto">update</property>
⚠ hbm2ddl.auto=update hará que Hibernate cree o actualice las tablas automáticamente según tus entidades.

4. Ejecutar el proyecto
Desde terminal dentro del proyecto:

bash
Copiar código
mvn clean javafx:run
Esto compilará el proyecto y abrirá la aplicación JavaFX.

Si usas un IDE, simplemente haz Run en la clase principal que lanza JavaFX.

📂 Estructura del proyecto
bash
Copiar código
ProyectoGestionAeronaves/
│
├─ src/main/java/com/aeronautica/
│   ├─ controller/        # Controladores de JavaFX
│   ├─ dao/               # Acceso a datos (Hibernate)
│   ├─ model/             # Entidades (Usuario, Aeronave, etc.)
│   ├─ service/           # Lógica de negocio
│   └─ config/            # Configuración de Hibernate, utilidades
│
├─ src/main/resources/
│   ├─ styles/            # CSS
│   ├─ fxml/              # Interfaces FXML
│   └─ hibernate.cfg.xml  # Configuración de Hibernate
│
├─ pom.xml                # Dependencias Maven
└─ README.md              # Documentación
🧩 Dependencias principales
En pom.xml:

JavaFX

Hibernate ORM 6

MySQL Connector/J

Maven Compiler Plugin
