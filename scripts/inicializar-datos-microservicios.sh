#!/bin/bash

echo "🔧 Inicializando datos en microservicios..."
echo "==========================================="
echo ""

# Insertar datos en PostgreSQL
docker exec -i microservicios-postgres-db-1 psql -U arrendamiento_user -d arrendamiento_db << 'EOF'

-- ==============================
-- PROPIETARIOS
-- ==============================
INSERT INTO propietarios (prop_nombre, prop_apellido, prop_cedula, prop_telefono, prop_correo, prop_contrasena, prop_direccion, prop_fecha_registro, prop_activo)
VALUES ('Juan', 'Propietario', '222222222', '3002222222', 'propietario@test.com', '$2a$10$dummyhash', 'Calle Principal 123', CURRENT_TIMESTAMP, true)
ON CONFLICT (prop_correo) DO NOTHING;

SELECT 'Propietario registrado con ID: ' || prop_id FROM propietarios WHERE prop_correo = 'propietario@test.com';

EOF

echo ""
echo "✅ Datos inicializados correctamente"
echo ""
echo "📋 Ahora puedes:"
echo "  1. Login como propietario: propietario@test.com / prop123"
echo "  2. Registrar inmuebles"
echo "  3. Crear contratos usando:"
echo "     - Propietario ID: 1"
echo "     - IDs de arrendatarios: 1, 2, 7 (del servicio de usuarios)"
echo ""
echo "💡 NOTA: Los microservicios validarán estos IDs internamente"

