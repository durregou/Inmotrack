#!/bin/bash

# Script para crear notificaciones de demostración
# Usuario: Maria Inquilina (inquilino@test.com)

echo "================================================"
echo "🔔 CREANDO NOTIFICACIONES DE DEMOSTRACIÓN"
echo "================================================"
echo ""
echo "📧 Para: Maria Inquilina (inquilino@test.com)"
echo ""

# Email del arrendatario
DESTINATARIO="inquilino@test.com"

echo "🔌 Conectando a PostgreSQL via Docker..."
echo ""

# Ejecutar comandos SQL via Docker
docker exec -i microservicios-postgres-db-1 psql -U arrendamiento_user -d arrendamiento_db << EOF

-- Limpiar notificaciones antiguas del usuario (opcional)
DELETE FROM notificaciones WHERE destinatario = '$DESTINATARIO';

-- 1. Bienvenida al Sistema
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    '¡Bienvenido al Sistema de Arrendamiento!',
    'Hola Maria, tu cuenta ha sido activada exitosamente. Ahora puedes gestionar tu contrato, realizar pagos y solicitar mantenimientos desde el panel. ¡Bienvenida!',
    'ENVIADO',
    NOW() - INTERVAL '7 days',
    NOW() - INTERVAL '7 days',
    1
);

-- 2. Recordatorio de Pago
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    'Recordatorio: Pago de Arriendo - Octubre 2025',
    'Estimada Maria, te recordamos que el pago del arriendo correspondiente al mes de octubre vence el 05/10/2025. Monto: \$1,234.00. Por favor realiza el pago a tiempo para evitar cargos adicionales. Gracias.',
    'ENVIADO',
    NOW() - INTERVAL '3 days',
    NOW() - INTERVAL '3 days',
    1
);

-- 3. Confirmación de Pago
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    'Confirmación de Pago Recibido',
    '¡Gracias Maria! Hemos recibido tu pago de \$1,234.00 correspondiente al mes de octubre 2025. Tu recibo está disponible en el sistema. Comprobante #12345.',
    'ENVIADO',
    NOW() - INTERVAL '2 days',
    NOW() - INTERVAL '2 days',
    1
);

-- 4. Solicitud de Mantenimiento Recibida
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    'Solicitud de Mantenimiento Recibida',
    'Tu solicitud de mantenimiento ha sido recibida y registrada en el sistema. El propietario ha sido notificado y te contactará pronto. Número de solicitud: #001',
    'ENVIADO',
    NOW() - INTERVAL '1 day',
    NOW() - INTERVAL '1 day',
    1
);

-- 5. Actualización de Mantenimiento
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'SMS',
    'Actualización: Mantenimiento EN PROCESO',
    'Tu solicitud de mantenimiento #001 está EN PROCESO. Un técnico visitará el inmueble el viernes 10/10/2025 entre 9:00 AM y 12:00 PM. Por favor confirma tu disponibilidad.',
    'ENVIADO',
    NOW() - INTERVAL '12 hours',
    NOW() - INTERVAL '12 hours',
    1
);

-- 6. Mensaje del Propietario
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    'Mensaje del Propietario',
    'Estimada Maria, te informamos que se realizará mantenimiento preventivo del edificio el próximo sábado 12/10/2025 de 9:00 AM a 1:00 PM. Habrá corte de agua durante 2 horas. Disculpa las molestias. Saludos, Juan.',
    'ENVIADO',
    NOW() - INTERVAL '6 hours',
    NOW() - INTERVAL '6 hours',
    1
);

-- 7. Recordatorio de Inspección
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    'Inspección de Inmueble Programada',
    'Se ha programado una inspección de rutina del inmueble para el día 15/10/2025 a las 10:00 AM. Esta inspección es parte del mantenimiento regular. Por favor confirma tu disponibilidad respondiendo a este correo.',
    'ENVIADO',
    NOW() - INTERVAL '3 hours',
    NOW() - INTERVAL '3 hours',
    1
);

-- 8. Alerta de Vencimiento de Contrato
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'WHATSAPP',
    'IMPORTANTE: Contrato próximo a vencer',
    'Hola Maria, tu contrato de arrendamiento vencerá el 31/12/2025 (dentro de 3 meses). Si deseas renovarlo, por favor contacta al propietario con anticipación. Gracias.',
    'ENVIADO',
    NOW() - INTERVAL '2 hours',
    NOW() - INTERVAL '2 hours',
    1
);

-- 9. Promoción/Descuento
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, fecha_envio, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    '🎉 Promoción: Descuento por Pronto Pago',
    '¡Oferta especial! Paga tu próximo arriendo antes del día 1 del mes y obtén un 5% de descuento. Válido solo para el mes de noviembre 2025. ¡No pierdas esta oportunidad de ahorrar!',
    'ENVIADO',
    NOW() - INTERVAL '1 hour',
    NOW() - INTERVAL '1 hour',
    1
);

-- 10. Notificación Pendiente (Nueva)
INSERT INTO notificaciones (destinatario, tipo, asunto, mensaje, estado, fecha_creacion, intentos_envio)
VALUES (
    '$DESTINATARIO',
    'EMAIL',
    'Actualización: Nuevas Funcionalidades Disponibles',
    'Te informamos que el sistema ahora cuenta con notificaciones en tiempo real, historial de pagos mejorado y seguimiento de solicitudes de mantenimiento. Explora las nuevas funciones en tu panel.',
    'PENDIENTE',
    NOW(),
    0
);

-- Mostrar resumen
SELECT COUNT(*) as "Total Notificaciones Creadas" 
FROM notificaciones 
WHERE destinatario = '$DESTINATARIO';

EOF

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ ¡Notificaciones de demostración creadas exitosamente!"
    echo ""
    echo "📊 Ahora el usuario 'inquilino@test.com' tiene:"
    echo "   - 10 notificaciones de diferentes tipos"
    echo "   - 9 enviadas y 1 pendiente"
    echo "   - Fechas variadas (desde hace 7 días hasta ahora)"
    echo "   - Tipos: EMAIL, SMS, WHATSAPP"
    echo ""
    echo "🔄 Recarga el panel de notificaciones en la aplicación"
    echo "   (haz clic en el botón 'Actualizar')"
    echo ""
else
    echo ""
    echo "❌ Error al crear las notificaciones"
    echo ""
fi

echo "================================================"

