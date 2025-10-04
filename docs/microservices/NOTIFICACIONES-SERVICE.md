# 📬 Notificaciones Service

**Puerto**: 8088  
**Repositorio**: notificaciones-service  
**Base de Datos**: PostgreSQL (tabla `notificaciones`)

---

## 📋 Descripción

Microservicio responsable del sistema de notificaciones multicanal. Maneja el envío de alertas, recordatorios y mensajes a usuarios del sistema.

---

## 🎯 Responsabilidades

- ✅ **Crear notificaciones** individuales y masivas
- ✅ **Multicanal**: EMAIL, SMS, WHATSAPP (simulado)
- ✅ **Filtrado**: Por destinatario, tipo, estado
- ✅ **Historial**: Tracking completo de notificaciones enviadas
- ✅ **Estados**: PENDIENTE, ENVIADO, FALLIDO

---

## 🗄️ Modelo de Datos

### Tabla: `notificaciones`

| Campo | Tipo | Restricción | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `destinatario` | VARCHAR(255) | NOT NULL | Email o teléfono del destinatario |
| `asunto` | VARCHAR(255) | NOT NULL | Asunto de la notificación |
| `mensaje` | TEXT | NOT NULL | Contenido del mensaje |
| `tipo` | VARCHAR(50) | NOT NULL | EMAIL, SMS, WHATSAPP |
| `estado` | VARCHAR(50) | DEFAULT 'PENDIENTE' | PENDIENTE, ENVIADO, FALLIDO |
| `contrato_id` | BIGINT | | Referencia opcional al contrato |
| `pago_id` | BIGINT | | Referencia opcional al pago |
| `fecha_creacion` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de creación |
| `fecha_envio` | TIMESTAMP | | Fecha de envío exitoso |

---

## 🔌 Endpoints

### 1. **POST** `/api/notificaciones`
Crear notificación.

**Request Body**:
```json
{
  "destinatario": "inquilino@test.com",
  "asunto": "Recordatorio de Pago",
  "mensaje": "Su pago del mes de octubre vence el día 5. Por favor realizar el pago a tiempo para evitar cargos por mora.",
  "tipo": "EMAIL",
  "contratoId": 1,
  "pagoId": null
}
```

**Response 201 Created**:
```json
{
  "id": 1,
  "destinatario": "inquilino@test.com",
  "asunto": "Recordatorio de Pago",
  "mensaje": "Su pago del mes de octubre...",
  "tipo": "EMAIL",
  "estado": "PENDIENTE",
  "contratoId": 1,
  "fechaCreacion": "2025-10-04T10:00:00",
  "fechaEnvio": null
}
```

**Tipos**: EMAIL | SMS | WHATSAPP (simulado)

---

### 2. **GET** `/api/notificaciones`
Listar todas las notificaciones.

**Response 200 OK**:
```json
[
  {
    "id": 1,
    "destinatario": "inquilino@test.com",
    "asunto": "Recordatorio de Pago",
    "tipo": "EMAIL",
    "estado": "ENVIADO",
    "fechaCreacion": "2025-10-04T10:00:00",
    "fechaEnvio": "2025-10-04T10:01:00"
  },
  {
    "id": 2,
    "destinatario": "3001234567",
    "asunto": "Mantenimiento Programado",
    "tipo": "SMS",
    "estado": "ENVIADO",
    "fechaCreacion": "2025-10-04T11:00:00",
    "fechaEnvio": "2025-10-04T11:00:30"
  }
]
```

---

### 3. **GET** `/api/notificaciones?destinatario={email}`
Filtrar por destinatario.

**Ejemplo**:
```
GET /api/notificaciones?destinatario=inquilino@test.com
```

**Response 200 OK**: Notificaciones del usuario

---

### 4. **GET** `/api/notificaciones/{id}`
Obtener notificación por ID.

**Response 200 OK**:
```json
{
  "id": 1,
  "destinatario": "inquilino@test.com",
  "asunto": "Recordatorio de Pago",
  "mensaje": "Su pago del mes de octubre vence el día 5. Por favor realizar el pago a tiempo para evitar cargos por mora.",
  "tipo": "EMAIL",
  "estado": "ENVIADO",
  "contratoId": 1,
  "pagoId": null,
  "fechaCreacion": "2025-10-04T10:00:00",
  "fechaEnvio": "2025-10-04T10:01:00"
}
```

---

## 📊 Casos de Uso

### 1. Recordatorio de Pago

```bash
curl -X POST http://localhost:8088/api/notificaciones \
  -H "Content-Type: application/json" \
  -d '{
    "destinatario": "inquilino@test.com",
    "asunto": "Recordatorio: Pago de Arriendo",
    "mensaje": "Estimado arrendatario, le recordamos que el pago de su arriendo vence el 5 de octubre.",
    "tipo": "EMAIL",
    "contratoId": 1
  }'
```

### 2. Notificación de Mantenimiento

```bash
curl -X POST http://localhost:8088/api/notificaciones \
  -H "Content-Type: application/json" \
  -d '{
    "destinatario": "3001234567",
    "asunto": "Mantenimiento Aprobado",
    "mensaje": "Su solicitud de mantenimiento ha sido aprobada. El técnico llegará mañana entre 2-4 PM.",
    "tipo": "SMS"
  }'
```

### 3. Notificación Masiva (Admin)

```bash
# Obtener todos los arrendatarios
curl http://localhost:8086/api/usuarios?tipo=ARRENDATARIO

# Enviar notificación a cada uno
for email in $(curl -s http://localhost:8086/api/usuarios?tipo=ARRENDATARIO | jq -r '.[].correo'); do
  curl -X POST http://localhost:8088/api/notificaciones \
    -H "Content-Type: application/json" \
    -d "{
      \"destinatario\": \"$email\",
      \"asunto\": \"Aviso Importante\",
      \"mensaje\": \"Les informamos que habrá mantenimiento general el próximo sábado.\",
      \"tipo\": \"EMAIL\"
    }"
done
```

### 4. Ver Notificaciones de un Usuario

```bash
curl "http://localhost:8088/api/notificaciones?destinatario=inquilino@test.com"
```

---

## 💡 Funcionalidades Implementadas en UI

### Panel Admin
✅ **Envío Masivo**: 
- Seleccionar destinatarios (Todos, Propietarios, Arrendatarios)
- Seleccionar tipo (EMAIL, SMS, WHATSAPP)
- Ingresar asunto y mensaje
- Envío automático a múltiples usuarios

### Panel Arrendatario
✅ **Visualización**:
- Ver notificaciones recibidas
- Doble-clic para ver mensaje completo
- Marcar como leída (tracking local)
- Colores por tipo de notificación

---

## 🎨 Estados de Notificación

| Estado | Descripción | Uso |
|--------|-------------|-----|
| **PENDIENTE** | Notificación creada, esperando envío | Estado inicial |
| **ENVIADO** | Notificación enviada exitosamente | Confirmación de envío |
| **FALLIDO** | Error al enviar | Requiere revisión |

---

## 🐛 Errores Comunes

### Error: "Destinatario no válido"
**Causa**: Email o teléfono inválido  
**Solución**: Verificar formato del destinatario

### Error: "Tipo de notificación no soportado"
**Causa**: Tipo inválido  
**Solución**: Usar solo: EMAIL, SMS, WHATSAPP

---

## 🚀 Mejoras Futuras

### Fase 1 (Corto Plazo)
- [ ] **Marcar como Leída**: Endpoint PUT para tracking de lectura
- [ ] **Plantillas**: Templates predefinidos
- [ ] **Programación**: Envío diferido
- [ ] **Prioridad**: Notificaciones urgentes

### Fase 2 (Mediano Plazo)
- [ ] **Integración Real**: 
  - SendGrid/Mailgun para EMAIL
  - Twilio para SMS
  - WhatsApp Business API
- [ ] **Push Notifications**: Para app móvil
- [ ] **Estadísticas**: Tasa de apertura, clicks
- [ ] **Respuestas**: Notificaciones bidireccionales

---

## 📁 Estructura del Proyecto

```
notificaciones-service/
├── src/main/java/com/arrendamiento/notificaciones/
│   ├── controller/
│   │   └── NotificacionController.java       # Endpoints REST
│   ├── service/
│   │   └── NotificacionService.java          # Lógica de negocio
│   ├── repository/
│   │   └── NotificacionRepository.java       # Acceso a BD
│   ├── model/
│   │   └── Notificacion.java                 # Entidad JPA
│   └── dto/
│       ├── NotificacionRequest.java          # DTO de entrada
│       └── NotificacionResponse.java         # DTO de salida
├── src/main/resources/
│   └── application.properties                # Configuración
└── pom.xml                                   # Dependencias Maven
```

---

## 🔗 Referencias

- **API Completa**: [../API.md](../API.md)
- **Postman**: [../../microservicios/postman-collections/](../../microservicios/postman-collections/)
- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última actualización**: Octubre 2025  
**Endpoints**: 4 endpoints (Envío Masivo implementado en UI)

