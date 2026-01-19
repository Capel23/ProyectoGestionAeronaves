# 🚀 Guía de Ejecución Rápida - MainApp (GUI)

## ✅ Requisitos Completados

Todo está configurado y listo para ejecutar. El proyecto incluye:

- ✅ JavaFX 21 configurado
- ✅ Base de datos H2 persistente (archivo local)
- ✅ Usuarios por defecto creados automáticamente
- ✅ Interfaz gráfica funcional

## 🎮 Cómo Ejecutar la Aplicación GUI

### Opción 1: Script Directo (Recomendado)
```bash
run-gui.bat
```

### Opción 2: Línea de Comandos
```bash
mvn javafx:run
```

### Opción 3: Compilar y Ejecutar
```bash
mvn clean compile
mvn javafx:run
```

## 👤 Usuarios de Prueba

La aplicación crea automáticamente estos usuarios la primera vez que se ejecuta:

| Usuario   | Contraseña | Rol      |
|-----------|------------|----------|
| admin     | admin123   | ADMIN    |
| mecanico  | mec123     | MECANICO |
| piloto    | pilot123   | PILOTO   |

## 🎯 Funcionalidades de la GUI

### Pantalla de Login
- Autenticación de usuarios
- Validación de credenciales
- Gestión de roles

### Pantalla Principal
- **Gestión de Aeronaves**: Ver, crear, editar y eliminar aeronaves
- **Control por Roles**: Los permisos dependen del rol del usuario:
  - **ADMIN**: Acceso completo a todas las funciones
  - **MECANICO**: Puede editar aeronaves y realizar mantenimiento
  - **PILOTO**: Solo puede visualizar información

### Tablas Interactivas
- Visualización de aeronaves registradas
- Campos: Matrícula, Modelo, Estado
- Selección con doble clic para editar

## 📊 Características Técnicas

### Base de Datos
- **Tipo**: H2 Database (archivo local)
- **Ubicación**: `./aeronautica_db.mv.db`
- **Persistencia**: Los datos se mantienen entre ejecuciones
- **Modo**: `update` (no borra datos al reiniciar)

### Arquitectura
```
MainApp (JavaFX)
    ↓
LoginController → AuthService → UsuarioDAO
    ↓
MainController → AeronaveService → AeronaveDAO
                                       ↓
                                  Hibernate → H2 Database
```

## 🔧 Ejecutables Disponibles

### 1. DemoApp (Consola)
```bash
mvn exec:java -Dexec.mainClass="com.aeronautica.DemoApp"
```
- Demo completa de todas las funcionalidades
- Genera certificados XML
- Genera inventarios JSON
- Muestra reportes en consola

### 2. MainApp (GUI JavaFX)
```bash
mvn javafx:run
```
- Interfaz gráfica completa
- Login con usuarios
- Gestión visual de aeronaves

### 3. InitData (Utilidad)
```bash
mvn exec:java -Dexec.mainClass="com.aeronautica.InitData"
```
- Crea usuarios de prueba manualmente
- Útil si necesitas resetear usuarios

## 🐛 Solución de Problemas

### Error: "Could not find or load main class"
```bash
mvn clean compile
mvn javafx:run
```

### La ventana no aparece
- Verifica que no haya otras instancias ejecutándose
- Comprueba que Java 17+ esté instalado
- Intenta ejecutar con: `mvn javafx:run -X` para ver logs detallados

### Error de conexión a base de datos
- La base de datos se crea automáticamente
- Si hay problemas, elimina el archivo `aeronautica_db.mv.db` y reinicia

### No puedo hacer login
- Usuarios por defecto: admin/admin123, mecanico/mec123, piloto/pilot123
- Si no funcionan, ejecuta InitData para recrearlos

## 📁 Archivos Importantes

```
ProyectoGestionAeronaves/
├── run-gui.bat                      # Script para ejecutar GUI
├── run.bat                          # Script para ejecutar Demo
├── aeronautica_db.mv.db            # Base de datos H2 (se crea al ejecutar)
├── src/main/
│   ├── java/com/aeronautica/
│   │   ├── MainApp.java            # Aplicación JavaFX principal
│   │   ├── DemoApp.java            # Demo por consola
│   │   ├── InitData.java           # Inicialización de usuarios
│   │   └── controller/
│   │       ├── LoginController.java
│   │       └── MainController.java
│   └── resources/
│       ├── fxml/
│       │   ├── login.fxml          # UI Login
│       │   └── main.fxml           # UI Principal
│       └── styles/
│           └── app.css             # Estilos de la aplicación
└── pom.xml                         # Configuración Maven
```

## 🎨 Personalización

### Cambiar Estilos
Edita: `src/main/resources/styles/app.css`

### Modificar Interfaz
Edita: `src/main/resources/fxml/login.fxml` o `main.fxml`

### Agregar Funcionalidades
Edita: `src/main/java/com/aeronautica/controller/MainController.java`

## ✨ Próximos Pasos

1. **Ejecuta la GUI**: `run-gui.bat` o `mvn javafx:run`
2. **Haz Login**: Usa `admin` / `admin123`
3. **Explora**: Navega por las funcionalidades disponibles
4. **Crea Datos**: Agrega nuevas aeronaves desde la interfaz
5. **Prueba Roles**: Login con diferentes usuarios para ver los permisos

## 📞 Comandos Útiles

```bash
# Ver versión de Java
java -version

# Ver versión de Maven
mvn -version

# Limpiar proyecto
mvn clean

# Compilar
mvn compile

# Ejecutar tests
mvn test

# Ejecutar GUI
mvn javafx:run

# Ejecutar Demo
mvn exec:java -Dexec.mainClass="com.aeronautica.DemoApp"
```

---

🎉 **¡Listo para usar!** La aplicación está completamente funcional y lista para ejecutarse.
