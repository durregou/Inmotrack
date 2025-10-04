# 🔧 Mantenimiento Service

**Puerto**: 8087  
**Repositorio**: mantenimiento-service  
**Base de Datos**: PostgreSQL (tabla `solicitudes_mantenimiento`)

---

## 📋 Descripción

Microservicio responsable de la gestión completa del sistema de mantenimiento. Implementa un workflow completo desde la solicitud hasta la finalización del trabajo.

---

## 🎯 Responsabilidades

- ✅ **Crear solicitudes** de mantenimiento
- ✅ **Workflow completo**: PENDIENTE → APROBADO → EN_PROCESO → COMPLETADO
- ✅ **Aprobar solicitudes** (Admin)
- ✅ **Iniciar trabajos** (Admin)
- ✅ **Completar trabajos** con costo real (Admin)
- ✅ **Rechazar solicitudes** con motivo (Admin)
- ✅ **Filtrado**: Por solicitante, inmueble, estado

---

## 🗄️ Modelo de Datos

### Tabla: `solicitudes_mantenimiento`

| Campo | Tipo | Restricción | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `inmueble_id` | BIGINT | NOT NULL | ID del inmueble (FK lógica) |
| `solicitante_id` | BIGINT | NOT NULL | ID del solicitante (FK lógica) |
| `titulo` | VARCHAR(255) | NOT NULL | Título de la solicitud |
| `descripcion` | TEXT | NOT NULL | Descripción detallada |
| `tipo` | VARCHAR(50) | NOT NULL | PLOMERIA, ELECTRICO, PINTURA, LIMPIEZA, OTRO |
| `prioridad` | VARCHAR(50) | NOT NULL | BAJA, MEDIA, ALTA, URGENTE |
| `estado` | VARCHAR(50) | DEFAULT 'PENDIENTE' | PENDIENTE, APROBADO, EN_PROCESO, COMPLETADO, RECHAZADO |
| `tecnico` | VARCHAR(255) | | Nombre del técnico asignado |
| `costo_estimado` | DECIMAL(15,2) | | Costo estimado |
| `costo_real` | DECIMAL(15,2) | | Costo real (al completar) |
| `fecha_completado` | TIMESTAMP | | Fecha de completación |
| `observaciones` | TEXT | | Notas adicionales |
| `fecha_solicitud` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de creación |

---

## 🔌 Endpoints

### 1. **POST** `/api/mantenimiento`
Crear solicitud de mantenimiento.

**Request Body**:
```json
{
  "inmuebleId": 1,
  "solicitanteId": 7,
  "titulo": "Fuga de agua en baño principal",
  "descripcion": "Hay una fuga constante en la tubería del baño principal que está afectando el techo del apartamento de abajo",
  "tipo": "PLOMERIA",
  "prioridad": "ALTA"
}
```

**Response 201 Created**:
```json
{
  "id": 1,
  "inmuebleId": 1,
  "solicitanteId": 7,
  "titulo": "Fuga de agua en baño principal",
  "descripcion": "Hay una fuga constante...",
  "tipo": "PLOMERIA",
  "prioridad": "ALTA",
  "estado": "PENDIENTE",
  "fechaSolicitud": "2025-10-04T10:00:00"
}
```

**Tipos**: PLOMERIA | ELECTRICO | PINTURA | LIMPIEZA | OTRO  
**Prioridad**: BAJA | MEDIA | ALTA | URGENTE

---

### 2. **GET** `/api/mantenimiento`
Listar todas las solicitudes.

---

### 3. **GET** `/api/mantenimiento?solicitante={id}`
Filtrar por solicitante.

---

### 4. **GET** `/api/mantenimiento?inmueble={id}`
Filtrar por inmueble.

---

### 5. **PUT** `/api/mantenimiento/{id}`
**✨ NUEVO** - Actualizar solicitud.

**Request Body**:
```json
{
  "titulo": "Título actualizado",
  "descripcion": "Descripción actualizada",
  "tipo": "ELECTRICO",
  "prioridad": "URGENTE",
  "tecnico": "Juan Técnico",
  "costoEstimado": 150000,
  "observaciones": "Requiere materiales especiales"
}
```

---

### 6. **PUT** `/api/mantenimiento/{id}/aprobar`
**✨ IMPLEMENTADO HOY** - Aprobar solicitud.

**Request**: Vacío `{}`

**Response 200 OK**:
```json
{
  "id": 1,
  "estado": "APROBADO",
  "mensaje": "Solicitud aprobada exitosamente"
}
```

**Validación**: Solo si estado es PENDIENTE

**Flujo**:
```
PENDIENTE → APROBADO
```

---

### 7. **PUT** `/api/mantenimiento/{id}/iniciar`
**✨ IMPLEMENTADO HOY** - Iniciar trabajo.

**Request**: Vacío `{}`

**Response 200 OK**:
```json
{
  "id": 1,
  "estado": "EN_PROCESO",
  "mensaje": "Trabajo iniciado exitosamente"
}
```

**Validación**: Solo si estado es APROBADO

**Flujo**:
```
APROBADO → EN_PROCESO
```

---

### 8. **PUT** `/api/mantenimiento/{id}/completar`
**✨ IMPLEMENTADO HOY** - Completar trabajo.

**Request Body**:
```json
{
  "costoReal": 180000,
  "observaciones": "Trabajo completado satisfactoriamente. Se reemplazó tubería principal."
}
```

**Response 200 OK**:
```json
{
  "id": 1,
  "estado": "COMPLETADO",
  "costoReal": 180000,
  "fechaCompletado": "2025-10-04T15:00:00",
  "mensaje": "Trabajo completado exitosamente"
}
```

**Validación**: 
- Solo si estado es EN_PROCESO
- `costoReal` es requerido

**Flujo**:
```
EN_PROCESO → COMPLETADO
```

---

### 9. **PUT** `/api/mantenimiento/{id}/rechazar`
**✨ IMPLEMENTADO HOY** - Rechazar solicitud.

**Request Body**:
```json
{
  "motivo": "Solicitud fuera de cobertura del contrato de arrendamiento"
}
```

**Response 200 OK**:
```json
{
  "id": 1,
  "estado": "RECHAZADO",
  "observaciones": "Solicitud fuera de cobertura del contrato de arrendamiento",
  "mensaje": "Solicitud rechazada"
}
```

**Validación**: Solo si estado es PENDIENTE o APROBADO

**Flujo**:
```
PENDIENTE → RECHAZADO
APROBADO → RECHAZADO
```

---

## 🔄 Workflow Completo

```
┌──────────┐
│PENDIENTE │ ← Arrendatario crea solicitud
└────┬─────┘
     │
     ├─→ Aprobar ─→ ┌─────────┐
     │              │APROBADO │ ← Admin aprueba
     │              └────┬────┘
     │                   │
     │                   ├─→ Iniciar ─→ ┌───────────┐
     │                   │               │EN_PROCESO│ ← Admin inicia trabajo
     │                   │               └─────┬─────┘
     │                   │                     │
     │                   │                     └─→ Completar ─→ ┌───────────┐
     │                   │                                       │COMPLETADO │ ✅
     │                   │                                       └───────────┘
     │                   │
     │                   └─→ Rechazar ─→ ┌──────────┐
     │                                    │RECHAZADO │ ❌
     └─→ Rechazar ─────────────────────→ └──────────┘
```

---

## 📊 Casos de Uso

### Workflow Completo

```bash
# 1. Arrendatario crea solicitud
curl -X POST http://localhost:8087/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "solicitanteId": 7,
    "titulo": "Fuga de agua",
    "descripcion": "Fuga en baño principal",
    "tipo": "PLOMERIA",
    "prioridad": "ALTA"
  }'
# Response: { "id": 10, "estado": "PENDIENTE" }

# 2. Admin aprueba
curl -X PUT http://localhost:8087/api/mantenimiento/10/aprobar
# Response: { "estado": "APROBADO" }

# 3. Admin inicia trabajo
curl -X PUT http://localhost:8087/api/mantenimiento/10/iniciar
# Response: { "estado": "EN_PROCESO" }

# 4. Admin completa trabajo
curl -X PUT http://localhost:8087/api/mantenimiento/10/completar \
  -H "Content-Type: application/json" \
  -d '{
    "costoReal": 180000,
    "observaciones": "Trabajo completado correctamente"
  }'
# Response: { "estado": "COMPLETADO", "fechaCompletado": "..." }
```

### Rechazar Solicitud

```bash
# Rechazar desde PENDIENTE
curl -X PUT http://localhost:8087/api/mantenimiento/11/rechazar \
  -H "Content-Type: application/json" \
  -d '{
    "motivo": "Solicitud fuera de cobertura"
  }'
```

---

## 🐛 Errores Comunes

### Error: "Solo se puede aprobar solicitudes en estado PENDIENTE"
**Causa**: Intento de aprobar una solicitud ya aprobada/rechazada  
**Solución**: Verificar el estado actual

### Error: "Solo se puede iniciar trabajos en estado APROBADO"
**Causa**: Intento de iniciar sin aprobar primero  
**Solución**: Aprobar la solicitud primero

### Error: "El campo costoReal es requerido"
**Causa**: Intento de completar sin proporcionar el costo real  
**Solución**: Incluir `costoReal` en el body

---

## 🚀 Mejoras Futuras

- [ ] **Asignación Automática**: Técnicos disponibles por zona
- [ ] **Seguimiento GPS**: Ubicación del técnico
- [ ] **Fotos**: Evidencia antes/después
- [ ] **Calificación**: Rating del trabajo realizado

---

## 🔗 Referencias

- **API Completa**: [../API.md](../API.md)
- **Postman**: [../../microservicios/postman-collections/](../../microservicios/postman-collections/)
- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Endpoints**: 9 endpoints (incluye Workflow completo ✨)

