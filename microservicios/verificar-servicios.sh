#!/bin/bash

# Script para verificar el estado de todos los microservicios

echo "🔍 Verificando estado de los microservicios..."
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Función para verificar un servicio
check_service() {
    local name=$1
    local port=$2
    local endpoint=$3
    
    echo -n "Verificando $name (puerto $port)... "
    
    if curl -s "http://localhost:$port$endpoint" > /dev/null 2>&1; then
        echo -e "${GREEN}✅ OK${NC}"
        return 0
    else
        echo -e "${RED}❌ No responde${NC}"
        return 1
    fi
}

# Lista de servicios
echo "📊 Servicios Originales:"
check_service "Administración Service" 8081 "/api/admin/1"
check_service "Propietarios Service" 8082 "/api/propietarios"
check_service "Inmuebles Service" 8083 "/api/inmuebles"
check_service "Contratos Service" 8084 "/api/contratos"
check_service "Pagos Service" 8085 "/api/pagos"

echo ""
echo "🆕 Nuevos Servicios:"
check_service "Usuarios Service" 8086 "/api/usuarios"
check_service "Notificaciones Service" 8087 "/api/notificaciones"
check_service "Mantenimiento Service" 8088 "/api/mantenimiento"
check_service "Reportes Service" 8089 "/api/reportes/ocupacion"

echo ""
echo "💾 Base de Datos:"
if docker-compose ps | grep postgres-db | grep -q "Up"; then
    echo -e "${GREEN}✅ PostgreSQL está corriendo${NC}"
else
    echo -e "${RED}❌ PostgreSQL no está corriendo${NC}"
fi

echo ""
echo "📝 Resumen de Docker Compose:"
docker-compose ps

echo ""
echo -e "${YELLOW}💡 Tip: Si algún servicio no responde, verifica los logs con:${NC}"
echo "   docker-compose logs <nombre-servicio>"

