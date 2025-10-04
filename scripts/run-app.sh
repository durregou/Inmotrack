#!/bin/bash

# Script para ejecutar la aplicación de arrendamiento
# Ejecutar desde la raíz del proyecto: ./scripts/run-app.sh

# Obtener directorio del proyecto (padre de scripts/)
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"

echo "🚀 Iniciando Aplicación de Arrendamiento..."
echo "📁 Directorio: $PROJECT_DIR"
echo ""

# Verificar que los microservicios estén corriendo
echo "📊 Verificando microservicios..."
if docker ps | grep -q "usuarios-service"; then
    echo "✅ Microservicios están corriendo"
else
    echo ""
    echo "⚠️  Algunos microservicios no están corriendo."
    echo "¿Deseas iniciarlos ahora? (s/n)"
    read -r response
    if [[ "$response" =~ ^([sS][iI]|[sS])$ ]]; then
        echo "🔄 Iniciando microservicios..."
        cd microservicios
        docker-compose up -d
        echo "⏳ Esperando a que los servicios estén listos (30s)..."
        sleep 30
        cd ..
    else
        echo "❌ No se puede continuar sin los microservicios. Saliendo..."
        exit 1
    fi
fi

# Compilar si es necesario
echo ""
echo "🔨 Compilando aplicación..."
javac -cp ".:sqlite-jdbc-3.7.2.jar:json-20231013.jar" -d build/classes src/Principal/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa"
    echo ""
    echo "🎨 Iniciando interfaz gráfica..."
    java -cp "build/classes:sqlite-jdbc-3.7.2.jar:json-20231013.jar" Principal.frmlogin
else
    echo "❌ Error en la compilación"
    exit 1
fi

