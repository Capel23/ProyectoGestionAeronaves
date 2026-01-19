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

## Tecnologías Utilizadas

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 21.0.8 |
| Persistencia | Hibernate ORM 6.6.1.Final |
| Base de Datos | MySQL (gestionada con XAMPP) |
| Interfaz de Usuario | JavaFX SDK + FXML + CSS |
| Formatos de Datos | JSON (inventario), XML (certificados) |
| Arquitectura | Capas: Vista, Controlador, Servicio, DAO, Modelo, Utilidades |
| Pruebas | JUnit (pruebas unitarias) |
| Gestión de Proyecto | Sin Maven (ejecución manual vía `java -cp`) |

---

## Estructura del Proyecto
