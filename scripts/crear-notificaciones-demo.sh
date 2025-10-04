#!/bin/bash

# Script para crear notificaciones de demostración para el arrendatario
# Usuario: Maria Inquilina (ID: 7 en usuarios-service)
# inquilino@test.com / inqui123

echo "================================================"
echo "🔔 CREANDO NOTIFICACIONES DE DEMOSTRACIÓN"
echo "================================================"
echo ""
echo "📧 Para: Maria Inquilina (inquilino@test.com)"
echo "   Usuario ID: 7"
echo ""

# Configuración de PostgreSQL
DB_HOST="localhost"
DB_PORT="5432"
DB_NAME="notificaciones_db"
DB_USER="postgres"
DB_PASSWORD="postgres123"

# Usuario ID del arrendatario
USUARIO_ID=7

echo "🔌 Conectando a PostgreSQL..."
echo ""

# Crear notificaciones de demostración
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME << EOF

-- Limpiar notificaciones antiguas del usuario (opcional)
DELETE FROM notificaciones WHERE usuario_id = $USUARIO_ID;

-- 1. Notificación de Bienvenida
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'INFORMACION',
    '¡Bienvenido al Sistema!',
    'Hola Maria, tu cuenta ha sido activada exitosamente. Ahora puedes gestionar tu contrato, pagos y solicitudes de mantenimiento.',
    'SISTEMA',
    'ENVIADO',
    NOW() - INTERVAL '7 days',
    'MEDIA'
);

-- 2. Recordatorio de Pago
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'RECORDATORIO',
    'Recordatorio de Pago - Arriendo Octubre',
    'Te recordamos que el pago del arriendo correspondiente al mes de octubre vence el 05/10/2025. Monto: $1,234. Por favor realiza el pago a tiempo.',
    'EMAIL',
    'ENVIADO',
    NOW() - INTERVAL '3 days',
    'ALTA'
);

-- 3. Confirmación de Solicitud de Mantenimiento
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'CONFIRMACION',
    'Solicitud de Mantenimiento Recibida',
    'Tu solicitud de mantenimiento "Solicitud de mantenimiento" ha sido recibida y está siendo procesada. El propietario ha sido notificado.',
    'SISTEMA',
    'ENVIADO',
    NOW() - INTERVAL '2 days',
    'MEDIA'
);

-- 4. Actualización de Mantenimiento
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'ACTUALIZACION',
    'Actualización de Mantenimiento',
    'El estado de tu solicitud de mantenimiento ha cambiado a: EN_PROCESO. Un técnico visitará el inmueble en los próximos 2 días hábiles.',
    'SMS',
    'ENVIADO',
    NOW() - INTERVAL '1 day',
    'ALTA'
);

-- 5. Confirmación de Pago Recibido
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'CONFIRMACION',
    'Pago Recibido - Confirmación',
    '¡Gracias! Hemos recibido tu pago de $1,234 correspondiente al mes de octubre. Tu comprobante de pago está disponible en el sistema.',
    'EMAIL',
    'ENVIADO',
    NOW() - INTERVAL '12 hours',
    'MEDIA'
);

-- 6. Alerta de Vencimiento de Contrato
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'ALERTA',
    'Próximo Vencimiento de Contrato',
    'Tu contrato de arrendamiento vencerá el 31/12/2025. Por favor contacta al propietario si deseas renovarlo.',
    'PUSH',
    'ENVIADO',
    NOW() - INTERVAL '6 hours',
    'ALTA'
);

-- 7. Mensaje del Propietario
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'MENSAJE',
    'Mensaje del Propietario',
    'Estimada Maria, te informamos que se realizará mantenimiento preventivo del edificio el próximo sábado de 9:00 AM a 1:00 PM. Saludos.',
    'EMAIL',
    'ENVIADO',
    NOW() - INTERVAL '3 hours',
    'MEDIA'
);

-- 8. Recordatorio de Inspección
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'RECORDATORIO',
    'Inspección de Inmueble Programada',
    'Se ha programado una inspección de rutina del inmueble para el día 10/10/2025 a las 10:00 AM. Por favor confirma tu disponibilidad.',
    'SISTEMA',
    'PENDIENTE',
    NOW() - INTERVAL '1 hour',
    'ALTA'
);

-- 9. Notificación Nueva (Sin Leer)
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'INFORMACION',
    'Actualización de Servicios',
    'Te informamos que el sistema ahora cuenta con notificaciones en tiempo real. Recibirás alertas inmediatas sobre pagos, mantenimientos y mensajes.',
    'PUSH',
    'PENDIENTE',
    NOW(),
    'BAJA'
);

-- 10. Promoción/Descuento
INSERT INTO notificaciones (usuario_id, tipo, titulo, mensaje, canal, estado, fecha_envio, prioridad)
VALUES (
    $USUARIO_ID,
    'PROMOCION',
    '🎉 Descuento por Pronto Pago',
    'Paga tu próximo arriendo antes del día 1 del mes y obtén un 5% de descuento. ¡No pierdas esta oportunidad!',
    'EMAIL',
    'ENVIADO',
    NOW(),
    'BAJA'
);

-- Mostrar resumen
SELECT COUNT(*) as "Total Notificaciones Creadas" 
FROM notificaciones 
WHERE usuario_id = $USUARIO_ID;

EOF

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ ¡Notificaciones de demostración creadas exitosamente!"
    echo ""
    echo "📊 Ahora el usuario 'inquilino@test.com' tiene:"
    echo "   - 10 notificaciones de diferentes tipos"
    echo "   - Fechas variadas (desde hace 7 días hasta ahora)"
    echo "   - Diferentes prioridades y canales"
    echo ""
    echo "🔄 Recarga el panel de notificaciones en la aplicación"
    echo "   para ver las nuevas notificaciones."
    echo ""
else
    echo ""
    echo "❌ Error al crear las notificaciones"
    echo "   Verifica que PostgreSQL esté corriendo"
    echo "   y que la base de datos 'notificaciones_db' exista."
    echo ""
fi

echo "================================================"

