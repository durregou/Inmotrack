#!/bin/bash

# Script para crear usuarios de prueba en el sistema

echo "🔧 Creador de Usuarios de Prueba"
echo "================================"
echo ""

# Verificar que el servicio esté corriendo
echo "📡 Verificando servicio de usuarios..."
if ! curl -s http://localhost:8086/api/usuarios > /dev/null; then
    echo "❌ El servicio de usuarios no está disponible en el puerto 8086"
    echo "   Asegúrate de que los microservicios estén corriendo:"
    echo "   cd microservicios && docker-compose up -d"
    exit 1
fi

echo "✅ Servicio disponible"
echo ""

# Función para crear usuario
crear_usuario() {
    local nombre=$1
    local apellido=$2
    local correo=$3
    local contrasena=$4
    local telefono=$5
    local cedula=$6
    local tipo=$7
    
    echo "📝 Creando usuario: $correo ($tipo)"
    
    response=$(curl -s -X POST http://localhost:8086/api/usuarios/registro \
        -H "Content-Type: application/json" \
        -w "\n%{http_code}" \
        -d "{
            \"nombre\": \"$nombre\",
            \"apellido\": \"$apellido\",
            \"correo\": \"$correo\",
            \"contrasena\": \"$contrasena\",
            \"telefono\": \"$telefono\",
            \"cedula\": \"$cedula\",
            \"tipoUsuario\": \"$tipo\"
        }")
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n-1)
    
    if [ "$http_code" -eq 201 ] || [ "$http_code" -eq 200 ]; then
        echo "   ✅ Usuario creado exitosamente"
        echo "   📧 Correo: $correo"
        echo "   🔑 Contraseña: $contrasena"
        return 0
    else
        echo "   ❌ Error al crear usuario (HTTP $http_code)"
        echo "   $body"
        return 1
    fi
}

echo "🎯 Creando usuarios de prueba..."
echo ""

# Crear Administrador
crear_usuario "Admin" "Sistema" "admin@sistema.com" "admin123" "3001111111" "111111111" "ADMINISTRADOR"
echo ""

# Crear Propietario
crear_usuario "Juan" "Propietario" "propietario@test.com" "prop123" "3002222222" "222222222" "PROPIETARIO"
echo ""

# Crear Arrendatario
crear_usuario "Maria" "Inquilina" "inquilino@test.com" "inqui123" "3003333333" "333333333" "ARRENDATARIO"
echo ""

echo "================================"
echo "✅ Proceso completado"
echo ""
echo "📋 Usuarios creados:"
echo ""
echo "👔 ADMINISTRADOR"
echo "   📧 admin@sistema.com"
echo "   🔑 admin123"
echo ""
echo "🏠 PROPIETARIO"
echo "   📧 propietario@test.com"
echo "   🔑 prop123"
echo ""
echo "🔑 ARRENDATARIO"
echo "   📧 inquilino@test.com"
echo "   🔑 inqui123"
echo ""
echo "💡 Usa estas credenciales en la aplicación para hacer login"

