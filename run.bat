@echo off
echo ╔═══════════════════════════════════════════════════════════════╗
echo ║   SISTEMA DE GESTIÓN DE MANTENIMIENTO AERONÁUTICO           ║
echo ║   Script de Inicio Rápido                                    ║
echo ╚═══════════════════════════════════════════════════════════════╝
echo.

echo Verificando dependencias...
echo.

REM Verificar Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Java no está instalado o no está en el PATH
    echo Por favor instala Java JDK 17 o superior
    pause
    exit /b 1
)
echo ✓ Java detectado

REM Verificar Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Maven no está instalado o no está en el PATH
    echo Por favor instala Maven 3.6+ y agrégalo al PATH
    pause
    exit /b 1
)
echo ✓ Maven detectado
echo.

echo ═══════════════════════════════════════════════════════════════
echo   OPCIONES DE EJECUCIÓN
echo ═══════════════════════════════════════════════════════════════
echo   1. Compilar proyecto
echo   2. Ejecutar Demo Completa
echo   3. Ejecutar Tests
echo   4. Limpiar y Compilar
echo   5. Generar documentación JavaDoc
echo   6. Salir
echo ═══════════════════════════════════════════════════════════════
echo.

set /p option="Selecciona una opción (1-6): "

if "%option%"=="1" goto compile
if "%option%"=="2" goto demo
if "%option%"=="3" goto test
if "%option%"=="4" goto clean
if "%option%"=="5" goto javadoc
if "%option%"=="6" goto end

:compile
echo.
echo Compilando proyecto...
mvn compile
goto menu

:demo
echo.
echo Ejecutando Demo Completa...
echo.
mvn exec:java -Dexec.mainClass="com.aeronautica.DemoApp"
goto menu

:test
echo.
echo Ejecutando tests...
mvn test
goto menu

:clean
echo.
echo Limpiando y compilando...
mvn clean compile
goto menu

:javadoc
echo.
echo Generando JavaDoc...
mvn javadoc:javadoc
echo JavaDoc generado en: target\site\apidocs\index.html
goto menu

:menu
echo.
echo ═══════════════════════════════════════════════════════════════
set /p continue="¿Deseas realizar otra acción? (S/N): "
if /i "%continue%"=="S" goto start
goto end

:start
cls
goto :eof

:end
echo.
echo ¡Hasta luego!
pause
