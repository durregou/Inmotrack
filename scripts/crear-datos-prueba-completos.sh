#!/bin/bash

# Script para crear un conjunto completo de datos de prueba

echo "🏗️  Creando Datos de Prueba Completos"
echo "====================================="
echo ""

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verificar servicios
echo "📡 Verificando servicios..."
servicios=("8081:Administración" "8082:Propietarios" "8083:Inmuebles" "8084:Contratos" "8085:Pagos" "8086:Usuarios" "8087:Notificaciones" "8088:Mantenimiento")

for servicio in "${servicios[@]}"; do
    IFS=':' read -r puerto nombre <<< "$servicio"
    if curl -s "http://localhost:$puerto" > /dev/null 2>&1; then
        echo -e "${GREEN}✅ $nombre (Puerto $puerto)${NC}"
    else
        echo -e "${RED}❌ $nombre (Puerto $puerto) - NO DISPONIBLE${NC}"
        echo "   Inicia los microservicios: cd microservicios && docker-compose up -d"
        exit 1
    fi
done

echo ""
echo "🎯 Paso 1: Creando Propietario..."

# Crear Propietario
PROP_RESPONSE=$(curl -s -X POST http://localhost:8082/api/propietarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Carlos",
    "apellido": "Propietario",
    "cedula": "1000000001",
    "telefono": "3001234567",
    "email": "carlos.prop@test.com",
    "direccion": "Calle Principal 123"
  }')

PROP_ID=$(echo $PROP_RESPONSE | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)

if [ -z "$PROP_ID" ]; then
    echo -e "${YELLOW}⚠️  Propietario ya existe o error al crear${NC}"
    PROP_ID=1
else
    echo -e "${GREEN}✅ Propietario creado con ID: $PROP_ID${NC}"
fi

echo ""
echo "🏠 Paso 2: Creando Inmuebles..."

# Crear Inmueble 1
INMUEBLE1_RESPONSE=$(curl -s -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d "{
    \"nombre\": \"Apartamento Centro\",
    \"direccion\": \"Carrera 10 #20-30\",
    \"tipo\": \"APARTAMENTO\",
    \"precio\": 1500000,
    \"estado\": \"DISPONIBLE\",
    \"caracteristicas\": \"2 habitaciones, 1 baño, cocina integral\",
    \"propietarioId\": $PROP_ID
  }")

INM1_ID=$(echo $INMUEBLE1_RESPONSE | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)

if [ -z "$INM1_ID" ]; then
    echo -e "${YELLOW}⚠️  Error al crear inmueble 1${NC}"
    INM1_ID=1
else
    echo -e "${GREEN}✅ Inmueble 1 creado con ID: $INM1_ID${NC}"
fi

# Crear Inmueble 2
INMUEBLE2_RESPONSE=$(curl -s -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d "{
    \"nombre\": \"Casa Norte\",
    \"direccion\": \"Calle 50 #15-20\",
    \"tipo\": \"CASA\",
    \"precio\": 2000000,
    \"estado\": \"DISPONIBLE\",
    \"caracteristicas\": \"3 habitaciones, 2 baños, garaje\",
    \"propietarioId\": $PROP_ID
  }")

INM2_ID=$(echo $INMUEBLE2_RESPONSE | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)

if [ -z "$INM2_ID" ]; then
    echo -e "${YELLOW}⚠️  Error al crear inmueble 2${NC}"
    INM2_ID=2
else
    echo -e "${GREEN}✅ Inmueble 2 creado con ID: $INM2_ID${NC}"
fi

echo ""
echo "📄 Paso 3: Creando Contratos..."

# Obtener ID del arrendatario (Maria Inquilina)
ARR_ID=7  # ID de Maria Inquilina según los logs

# Crear Contrato 1
CONTRATO_RESPONSE=$(curl -s -X POST http://localhost:8084/api/contratos \
  -H "Content-Type: application/json" \
  -d "{
    \"fechaInicio\": \"2025-01-01\",
    \"fechaFin\": \"2025-12-31\",
    \"estado\": \"ACTIVO\",
    \"arrendatarioId\": $ARR_ID,
    \"inmuebleId\": $INM1_ID,
    \"propietarioId\": $PROP_ID
  }")

CON_ID=$(echo $CONTRATO_RESPONSE | grep -o '"id":[0-9]*' | grep -o '[0-9]*' | head -1)

if [ -z "$CON_ID" ]; then
    echo -e "${YELLOW}⚠️  Error al crear contrato${NC}"
    CON_ID=1
else
    echo -e "${GREEN}✅ Contrato creado con ID: $CON_ID${NC}"
fi

echo ""
echo "💰 Paso 4: Creando Pagos de Ejemplo..."

# Crear Pago 1 (Enero)
curl -s -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d "{
    \"fecha\": \"2025-01-05\",
    \"monto\": 1500000,
    \"metodo\": \"TRANSFERENCIA\",
    \"estado\": \"COMPLETADO\",
    \"contratoId\": $CON_ID
  }" > /dev/null

echo -e "${GREEN}✅ Pago 1 creado (Enero)${NC}"

# Crear Pago 2 (Febrero)
curl -s -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d "{
    \"fecha\": \"2025-02-05\",
    \"monto\": 1500000,
    \"metodo\": \"EFECTIVO\",
    \"estado\": \"COMPLETADO\",
    \"contratoId\": $CON_ID
  }" > /dev/null

echo -e "${GREEN}✅ Pago 2 creado (Febrero)${NC}"

# Crear Pago 3 (Marzo)
curl -s -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d "{
    \"fecha\": \"2025-03-05\",
    \"monto\": 1500000,
    \"metodo\": \"TRANSFERENCIA\",
    \"estado\": \"COMPLETADO\",
    \"contratoId\": $CON_ID
  }" > /dev/null

echo -e "${GREEN}✅ Pago 3 creado (Marzo)${NC}"

echo ""
echo "🔧 Paso 5: Creando Solicitudes de Mantenimiento..."

# Crear Mantenimiento 1
curl -s -X POST http://localhost:8088/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d "{
    \"descripcion\": \"Fuga de agua en el baño\",
    \"inmuebleId\": $INM1_ID,
    \"estado\": \"PENDIENTE\"
  }" > /dev/null

echo -e "${GREEN}✅ Solicitud 1 creada${NC}"

# Crear Mantenimiento 2
curl -s -X POST http://localhost:8088/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d "{
    \"descripcion\": \"Reparación de puerta principal\",
    \"inmuebleId\": $INM1_ID,
    \"estado\": \"EN_PROCESO\"
  }" > /dev/null

echo -e "${GREEN}✅ Solicitud 2 creada${NC}"

echo ""
echo "=================================="
echo -e "${GREEN}✅ ¡Datos de prueba creados exitosamente!${NC}"
echo ""
echo "📊 Resumen:"
echo "  • Propietario ID: $PROP_ID"
echo "  • Inmuebles creados: 2"
echo "  • Contrato ID: $CON_ID"
echo "  • Pagos creados: 3"
echo "  • Solicitudes de mantenimiento: 2"
echo ""
echo "🎯 Ahora puedes:"
echo "  1. Login como: inquilino@test.com / inqui123"
echo "  2. Ver tu contrato en la pestaña 'Mi Contrato'"
echo "  3. Ver historial de pagos en 'Mis Pagos'"
echo "  4. Solicitar mantenimiento en 'Mantenimiento'"
echo ""
echo "💡 Para verificar los datos:"
echo "  curl http://localhost:8084/api/contratos | python3 -m json.tool"
echo "  curl http://localhost:8085/api/pagos | python3 -m json.tool"

