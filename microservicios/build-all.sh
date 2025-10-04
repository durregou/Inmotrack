#!/bin/bash

# Script para compilar todos los microservicios

echo "🚀 Iniciando compilación de todos los microservicios..."

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para compilar un microservicio
compile_service() {
    local service_name=$1
    echo -e "${YELLOW}📦 Compilando $service_name...${NC}"
    
    cd "$service_name" || exit 1
    
    if mvn clean package -DskipTests > "../build_${service_name}.log" 2>&1; then
        echo -e "${GREEN}✅ $service_name compilado exitosamente${NC}"
    else
        echo -e "${RED}❌ Error compilando $service_name. Ver build_${service_name}.log${NC}"
        return 1
    fi
    
    cd ..
}

# Lista de microservicios
services=("administracion-service" "propietarios-service" "inmuebles-service" "contratos-service" "pagos-service" "usuarios-service" "notificaciones-service" "mantenimiento-service" "reportes-service")

# Compilar cada servicio
for service in "${services[@]}"; do
    if ! compile_service "$service"; then
        echo -e "${RED}🛑 Falló la compilación. Abortando...${NC}"
        exit 1
    fi
done

echo -e "${GREEN}🎉 Todos los microservicios compilados exitosamente!${NC}"
echo -e "${YELLOW}💡 Ahora puedes ejecutar: docker-compose up -d${NC}"
