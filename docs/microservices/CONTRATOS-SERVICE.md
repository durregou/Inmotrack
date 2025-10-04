# 📄 Contratos Service

**Puerto**: 8084  
**Repositorio**: contratos-service  
**Base de Datos**: PostgreSQL (tabla `contratos`)

---

## 📋 Descripción

Microservicio responsable de la gestión completa de contratos de arrendamiento. Maneja el ciclo de vida de los contratos desde su creación hasta su finalización, coordinándose con inmuebles y pagos.

---

## 🎯 Responsabilidades

- ✅ **Crear contratos** con validación de inmuebles disponibles
- ✅ **CRUD completo**: Crear, Leer, Actualizar, Eliminar
- ✅ **Finalizar contratos**: Marcar como terminados y liberar inmueble
- ✅ **Filtrado**: Por propietario, arrendatario, inmueble
- ✅ **Coordinación**: Actualiza disponibilidad de inmuebles automáticamente

---

## 🗄️ Modelo de Datos

### Tabla: `contratos`

| Campo | Tipo | Restricción | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `inmueble_id` | BIGINT | NOT NULL | ID del inmueble (FK lógica) |
| `propietario_id` | BIGINT | NOT NULL | ID del propietario (FK lógica) |
| `arrendatario_id` | BIGINT | NOT NULL | ID del arrendatario (FK lógica) |
| `fecha_inicio` | DATE | NOT NULL | Fecha de inicio del contrato |
| `fecha_fin` | DATE | NOT NULL | Fecha de finalización del contrato |
| `valor_arriendo` | DECIMAL(15,2) | NOT NULL | Valor mensual del arriendo |
| `valor_administracion` | DECIMAL(15,2) | | Costo de administración |
| `deposito` | DECIMAL(15,2) | | Depósito de garantía |
| `dia_pago` | INT | | Día del mes para pagar (1-31) |
| `observaciones` | TEXT | | Notas adicionales |
| `estado` | VARCHAR(50) | DEFAULT 'ACTIVO' | ACTIVO, FINALIZADO, CANCELADO |
| `fecha_creacion` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de creación |

---

## 🔌 Endpoints

### 1. **POST** `/api/contratos`
Crear un nuevo contrato de arrendamiento.

**Request Body**:
```json
{
  "inmuebleId": 1,
  "propietarioId": 1,
  "arrendatarioId": 7,
  "fechaInicio": "2025-11-01",
  "fechaFin": "2026-11-01",
  "valorArriendo": 1500000,
  "valorAdministracion": 200000,
  "deposito": 1500000,
  "diaPago": 5,
  "observaciones": "Contrato de 1 año renovable"
}
```

**Response 201 Created**:
```json
{
  "id": 1,
  "inmuebleId": 1,
  "propietarioId": 1,
  "arrendatarioId": 7,
  "fechaInicio": "2025-11-01",
  "fechaFin": "2026-11-01",
  "valorArriendo": 1500000,
  "valorAdministracion": 200000,
  "deposito": 1500000,
  "diaPago": 5,
  "observaciones": "Contrato de 1 año renovable",
  "estado": "ACTIVO",
  "fechaCreacion": "2025-10-04T10:00:00"
}
```

**Validaciones**:
- ✅ `inmuebleId`, `propietarioId`, `arrendatarioId` requeridos
- ✅ `fechaInicio` y `fechaFin` requeridas
- ✅ `fechaFin` debe ser posterior a `fechaInicio`
- ✅ `valorArriendo` debe ser > 0
- ✅ Inmueble debe estar **disponible** (consulta a Inmuebles Service)

**Lógica de Negocio**:
1. Valida que el inmueble exista y esté disponible
2. Crea el contrato con estado ACTIVO
3. **Automáticamente marca el inmueble como NO disponible**

---

### 2. **GET** `/api/contratos`
Listar todos los contratos del sistema.

**Response 200 OK**:
```json
[
  {
    "id": 1,
    "inmuebleId": 1,
    "propietarioId": 1,
    "arrendatarioId": 7,
    "fechaInicio": "2025-11-01",
    "fechaFin": "2026-11-01",
    "valorArriendo": 1500000,
    "estado": "ACTIVO",
    "fechaCreacion": "2025-10-04T10:00:00"
  },
  {
    "id": 2,
    "inmuebleId": 2,
    "propietarioId": 1,
    "arrendatarioId": 8,
    "fechaInicio": "2025-10-01",
    "fechaFin": "2026-10-01",
    "valorArriendo": 2000000,
    "estado": "ACTIVO",
    "fechaCreacion": "2025-09-25T10:00:00"
  }
]
```

---

### 3. **GET** `/api/contratos/{id}`
Obtener detalles completos de un contrato específico.

**Response 200 OK**:
```json
{
  "id": 1,
  "inmuebleId": 1,
  "propietarioId": 1,
  "arrendatarioId": 7,
  "fechaInicio": "2025-11-01",
  "fechaFin": "2026-11-01",
  "valorArriendo": 1500000,
  "valorAdministracion": 200000,
  "deposito": 1500000,
  "diaPago": 5,
  "observaciones": "Contrato de 1 año renovable",
  "estado": "ACTIVO",
  "fechaCreacion": "2025-10-04T10:00:00"
}
```

**Response 404 Not Found**:
```json
{
  "error": "Contrato no encontrado"
}
```

---

### 4. **GET** `/api/contratos?propietarioId={id}`
Filtrar contratos por propietario.

**Query Parameters**:
- `propietarioId` (opcional): ID del propietario

**Ejemplo**:
```
GET /api/contratos?propietarioId=1
```

**Response 200 OK**: Array de contratos del propietario

---

### 5. **GET** `/api/contratos?arrendatarioId={id}`
Filtrar contratos por arrendatario.

**Query Parameters**:
- `arrendatarioId` (opcional): ID del arrendatario

**Ejemplo**:
```
GET /api/contratos?arrendatarioId=7
```

**Response 200 OK**: Array de contratos del arrendatario (generalmente 1 contrato activo)

---

### 6. **GET** `/api/contratos?inmuebleId={id}`
Filtrar contratos por inmueble.

**Query Parameters**:
- `inmuebleId` (opcional): ID del inmueble

**Ejemplo**:
```
GET /api/contratos?inmuebleId=1
```

**Response 200 OK**: Historial de contratos del inmueble

---

### 7. **PUT** `/api/contratos/{id}`
**✨ NUEVO** - Actualizar un contrato existente.

**Request Body**:
```json
{
  "fechaInicio": "2025-11-01",
  "fechaFin": "2026-11-01",
  "valorArriendo": 1600000,
  "valorAdministracion": 220000,
  "deposito": 1600000,
  "diaPago": 10,
  "observaciones": "Contrato extendido con incremento de precio"
}
```

**Response 200 OK**: Contrato actualizado

**Validaciones**:
- ✅ El contrato debe existir
- ✅ Solo se pueden actualizar: fechas, valores, día de pago, observaciones
- ✅ **NO se puede cambiar**: inmuebleId, propietarioId, arrendatarioId

---

### 8. **PUT** `/api/contratos/{id}/finalizar`
**✨ IMPLEMENTADO HOY** - Finalizar un contrato y liberar el inmueble.

**Request Body**: Vacío `{}`

**Response 200 OK**:
```json
{
  "id": 1,
  "estado": "FINALIZADO",
  "mensaje": "Contrato finalizado exitosamente. Inmueble ahora disponible."
}
```

**Lógica de Negocio**:
1. Valida que el contrato exista
2. Valida que NO esté ya finalizado
3. Cambia el estado a **FINALIZADO**
4. **Automáticamente marca el inmueble como DISPONIBLE**
5. Retorna confirmación

**Validaciones**:
- ✅ El contrato debe existir
- ✅ El contrato debe estar en estado ACTIVO
- ✅ No se puede finalizar un contrato ya finalizado

---

### 9. **DELETE** `/api/contratos/{id}`
**✨ NUEVO** - Eliminar un contrato del sistema.

**Response 200 OK**:
```json
{
  "mensaje": "Contrato eliminado exitosamente"
}
```

**Response 400 Bad Request**:
```json
{
  "error": "No se puede eliminar un contrato con pagos registrados"
}
```

**Validaciones**:
- ✅ El contrato debe existir
- ✅ **Advertencia**: Puede tener implicaciones en pagos registrados
- ✅ Se recomienda **finalizar** en lugar de eliminar

---

## 🔗 Dependencias

### Servicio de Inmuebles (:8083)

**Comunicación**:
- **Al crear contrato**: Consulta disponibilidad y marca como NO disponible
- **Al finalizar contrato**: Marca como disponible nuevamente

**Endpoints usados**:
```
GET  /api/inmuebles/{id}                    # Verificar existencia
PUT  /api/inmuebles/{id}/disponibilidad     # Cambiar disponibilidad
```

---

## 📊 Casos de Uso

### 1. Flujo Completo: Crear y Finalizar Contrato

```bash
# 1. Verificar que inmueble esté disponible
curl http://localhost:8083/api/inmuebles/1
# Response: { "id": 1, "disponible": true, ... }

# 2. Crear contrato
curl -X POST http://localhost:8084/api/contratos \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "propietarioId": 1,
    "arrendatarioId": 7,
    "fechaInicio": "2025-11-01",
    "fechaFin": "2026-11-01",
    "valorArriendo": 1500000,
    "diaPago": 5
  }'
# Response: { "id": 3, "estado": "ACTIVO", ... }

# 3. Verificar que inmueble ahora esté NO disponible
curl http://localhost:8083/api/inmuebles/1
# Response: { "id": 1, "disponible": false, ... }

# 4. Ver contrato del arrendatario
curl "http://localhost:8084/api/contratos?arrendatarioId=7"

# 5. Finalizar contrato (después de 1 año)
curl -X PUT http://localhost:8084/api/contratos/3/finalizar \
  -H "Content-Type: application/json" \
  -d '{}'
# Response: { "estado": "FINALIZADO", "mensaje": "Contrato finalizado..." }

# 6. Verificar que inmueble está disponible nuevamente
curl http://localhost:8083/api/inmuebles/1
# Response: { "id": 1, "disponible": true, ... }
```

### 2. Extender Contrato

```bash
# Actualizar fecha de finalización
curl -X PUT http://localhost:8084/api/contratos/3 \
  -H "Content-Type: application/json" \
  -d '{
    "fechaInicio": "2025-11-01",
    "fechaFin": "2027-11-01",
    "valorArriendo": 1600000,
    "observaciones": "Contrato extendido por 2 años con incremento"
  }'
```

### 3. Listar Contratos de un Propietario

```bash
curl "http://localhost:8084/api/contratos?propietarioId=1"
```

---

## 🧪 Testing

### Crear Contrato de Prueba

```bash
# Crear contrato con todos los campos
curl -X POST http://localhost:8084/api/contratos \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 2,
    "propietarioId": 1,
    "arrendatarioId": 8,
    "fechaInicio": "2025-12-01",
    "fechaFin": "2026-12-01",
    "valorArriendo": 2000000,
    "valorAdministracion": 250000,
    "deposito": 2000000,
    "diaPago": 10,
    "observaciones": "Contrato de prueba con todos los campos"
  }'
```

---

## 🐛 Errores Comunes

### Error: "Inmueble no disponible"
**Causa**: El inmueble ya tiene un contrato activo  
**Solución**: 
1. Finalizar el contrato anterior
2. O seleccionar otro inmueble

### Error: "fechaFin debe ser posterior a fechaInicio"
**Causa**: Fechas inválidas  
**Solución**: Asegurar que `fechaFin > fechaInicio`

### Error: "Contrato ya está finalizado"
**Causa**: Intento de finalizar un contrato que ya está en estado FINALIZADO  
**Solución**: Verificar el estado actual del contrato

### Error: "Contrato no encontrado"
**Causa**: ID no existe  
**Solución**: Verificar el ID o listar contratos disponibles

---

## 🚀 Mejoras Futuras

### Fase 1 (Corto Plazo)
- [ ] **Renovación Automática**: Endpoint para renovar contratos existentes
- [ ] **Historial de Cambios**: Tracking de modificaciones
- [ ] **Alertas de Vencimiento**: Notificaciones 30 días antes
- [ ] **Cláusulas**: Gestión de cláusulas especiales

### Fase 2 (Mediano Plazo)
- [ ] **Firma Digital**: Integración con servicios de firma electrónica
- [ ] **Documentos**: Generación automática de PDF
- [ ] **Indexación**: Incremento automático de arriendo por inflación
- [ ] **Penalidades**: Gestión de multas por incumplimiento

---

## 📁 Estructura del Proyecto

```
contratos-service/
├── src/main/java/com/arrendamiento/contratos/
│   ├── controller/
│   │   └── ContratoController.java       # Endpoints REST
│   ├── service/
│   │   └── ContratoService.java          # Lógica de negocio
│   ├── repository/
│   │   └── ContratoRepository.java       # Acceso a BD
│   ├── model/
│   │   └── Contrato.java                 # Entidad JPA
│   ├── dto/
│   │   ├── ContratoRequest.java          # DTO de entrada
│   │   └── ContratoResponse.java         # DTO de salida
│   └── client/
│       └── InmuebleClient.java           # Cliente para Inmuebles Service
├── src/main/resources/
│   └── application.properties            # Configuración
└── pom.xml                               # Dependencias Maven
```

---

## 🔗 Referencias

- **API Completa**: [../API.md](../API.md)
- **Arquitectura**: [../ARCHITECTURE.md](../ARCHITECTURE.md)
- **Postman**: [../../microservicios/postman-collections/](../../microservicios/postman-collections/)
- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última actualización**: Octubre 2025  
**Endpoints**: 9 endpoints completos (incluye Finalizar Contrato ✨)

