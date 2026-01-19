#!/bin/bash

echo "╔═══════════════════════════════════════════════════════════════╗"
echo "║   SISTEMA DE GESTIÓN DE MANTENIMIENTO AERONÁUTICO           ║"
echo "║   Script de Inicio Rápido                                    ║"
echo "╚═══════════════════════════════════════════════════════════════╝"
echo ""

echo "Verificando dependencias..."
echo ""

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado o no está en el PATH"
    echo "Por favor instala Java JDK 17 o superior"
    exit 1
fi
echo "✓ Java detectado"

# Verificar Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado o no está en el PATH"
    echo "Por favor instala Maven 3.6+ y agrégalo al PATH"
    exit 1
fi
echo "✓ Maven detectado"
echo ""

while true; do
    echo "═══════════════════════════════════════════════════════════════"
    echo "  OPCIONES DE EJECUCIÓN"
    echo "═══════════════════════════════════════════════════════════════"
    echo "  1. Compilar proyecto"
    echo "  2. Ejecutar Demo Completa"
    echo "  3. Ejecutar Tests"
    echo "  4. Limpiar y Compilar"
    echo "  5. Generar documentación JavaDoc"
    echo "  6. Salir"
    echo "═══════════════════════════════════════════════════════════════"
    echo ""

    read -p "Selecciona una opción (1-6): " option

    case $option in
        1)
            echo ""
            echo "Compilando proyecto..."
            mvn compile
            ;;
        2)
            echo ""
            echo "Ejecutando Demo Completa..."
            echo ""
            mvn exec:java -Dexec.mainClass="com.aeronautica.DemoApp"
            ;;
        3)
            echo ""
            echo "Ejecutando tests..."
            mvn test
            ;;
        4)
            echo ""
            echo "Limpiando y compilando..."
            mvn clean compile
            ;;
        5)
            echo ""
            echo "Generando JavaDoc..."
            mvn javadoc:javadoc
            echo "JavaDoc generado en: target/site/apidocs/index.html"
            ;;
        6)
            echo ""
            echo "¡Hasta luego!"
            exit 0
            ;;
        *)
            echo ""
            echo "❌ Opción inválida"
            ;;
    esac

    echo ""
    read -p "¿Deseas realizar otra acción? (S/N): " continue
    if [[ ! $continue =~ ^[SsYy]$ ]]; then
        echo ""
        echo "¡Hasta luego!"
        exit 0
    fi
    echo ""
done
