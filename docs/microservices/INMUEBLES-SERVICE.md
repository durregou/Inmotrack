# 🏠 Inmuebles Service

**Puerto**: 8083  
**Repositorio**: inmuebles-service  
**Base de Datos**: PostgreSQL (tabla `inmuebles`)

---

## 📋 Descripción

Microservicio responsable de la gestión del catálogo de propiedades inmobiliarias. Maneja el CRUD completo de inmuebles con sus características y disponibilidad.

---

## 🎯 Responsabilidades

- ✅ **Registro de inmuebles** con todas sus características
- ✅ **CRUD completo**: Crear, Leer, Actualizar, Eliminar
- ✅ **Gestión de disponibilidad**: Cambiar estado disponible/arrendado
- ✅ **Filtrado**: Por propietario, tipo, disponibilidad
- ✅ **Validaciones**: Datos obligatorios, valores positivos

---

## 🗄️ Modelo de Datos

### Tabla: `inmuebles`

| Campo | Tipo | Restricción | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `propietario_id` | BIGINT | NOT NULL | ID del propietario (FK lógica) |
| `tipo` | VARCHAR(50) | NOT NULL | APARTAMENTO, CASA, LOCAL, OFICINA, BODEGA |
| `direccion` | VARCHAR(255) | NOT NULL | Dirección completa |
| `ciudad` | VARCHAR(100) | NOT NULL | Ciudad |
| `departamento` | VARCHAR(100) | | Departamento/Estado |
| `area` | DECIMAL(10,2) | | Área en m² |
| `habitaciones` | INT | | Número de habitaciones |
| `banos` | INT | | Número de baños |
| `parqueaderos` | INT | | Número de parqueaderos |
| `estrato` | INT | | Estrato socioeconómico |
| `precio_arriendo` | DECIMAL(15,2) | NOT NULL | Valor mensual del arriendo |
| `precio_administracion` | DECIMAL(15,2) | | Costo de administración |
| `descripcion` | TEXT | | Descripción detallada |
| `amoblado` | BOOLEAN | DEFAULT FALSE | Si está amoblado |
| `disponible` | BOOLEAN | DEFAULT TRUE | Si está disponible |
| `activo` | BOOLEAN | DEFAULT TRUE | Si está activo en el sistema |
| `fecha_registro` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de creación |

---

## 🔌 Endpoints

### 1. **POST** `/api/inmuebles`
Crear un nuevo inmueble en el sistema.

**Request Body**:
```json
{
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30",
  "ciudad": "Bogotá",
  "departamento": "Cundinamarca",
  "area": 65.5,
  "habitaciones": 2,
  "banos": 1,
  "parqueaderos": 1,
  "estrato": 3,
  "precioArriendo": 1500000,
  "precioAdministracion": 200000,
  "descripcion": "Apartamento amplio y luminoso con excelente ubicación",
  "amoblado": false
}
```

**Response 201 Created**:
```json
{
  "id": 1,
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30",
  "ciudad": "Bogotá",
  "departamento": "Cundinamarca",
  "area": 65.5,
  "habitaciones": 2,
  "banos": 1,
  "parqueaderos": 1,
  "estrato": 3,
  "precioArriendo": 1500000,
  "precioAdministracion": 200000,
  "descripcion": "Apartamento amplio y luminoso con excelente ubicación",
  "amoblado": false,
  "disponible": true,
  "activo": true,
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

**Validaciones**:
- ✅ `propietarioId` requerido
- ✅ `tipo` debe ser: APARTAMENTO | CASA | LOCAL | OFICINA | BODEGA
- ✅ `direccion`, `ciudad`, `precioArriendo` requeridos
- ✅ `precioArriendo` debe ser > 0
- ✅ `area`, `habitaciones`, `banos` deben ser >= 0

---

### 2. **GET** `/api/inmuebles`
Listar todos los inmuebles del sistema.

**Response 200 OK**:
```json
[
  {
    "id": 1,
    "propietarioId": 1,
    "tipo": "APARTAMENTO",
    "direccion": "Carrera 10 #20-30",
    "ciudad": "Bogotá",
    "precioArriendo": 1500000,
    "habitaciones": 2,
    "banos": 1,
    "disponible": true,
    "activo": true
  },
  {
    "id": 2,
    "propietarioId": 1,
    "tipo": "CASA",
    "direccion": "Calle 50 #10-20",
    "ciudad": "Medellín",
    "precioArriendo": 2000000,
    "habitaciones": 3,
    "banos": 2,
    "disponible": false,
    "activo": true
  }
]
```

---

### 3. **GET** `/api/inmuebles/{id}`
Obtener detalles completos de un inmueble específico.

**Response 200 OK**:
```json
{
  "id": 1,
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30",
  "ciudad": "Bogotá",
  "departamento": "Cundinamarca",
  "area": 65.5,
  "habitaciones": 2,
  "banos": 1,
  "parqueaderos": 1,
  "estrato": 3,
  "precioArriendo": 1500000,
  "precioAdministracion": 200000,
  "descripcion": "Apartamento amplio y luminoso",
  "amoblado": false,
  "disponible": true,
  "activo": true,
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

**Response 404 Not Found**:
```json
{
  "error": "Inmueble no encontrado"
}
```

---

### 4. **GET** `/api/inmuebles?propietarioId={id}`
Filtrar inmuebles por propietario.

**Query Parameters**:
- `propietarioId` (opcional): ID del propietario

**Ejemplo**:
```
GET /api/inmuebles?propietarioId=1
```

**Response 200 OK**: Array de inmuebles del propietario especificado

---

### 5. **PUT** `/api/inmuebles/{id}`
**✨ NUEVO** - Actualizar completamente un inmueble.

**Request Body**:
```json
{
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30 ACTUALIZADO",
  "ciudad": "Bogotá",
  "departamento": "Cundinamarca",
  "area": 70.0,
  "habitaciones": 3,
  "banos": 2,
  "parqueaderos": 1,
  "estrato": 3,
  "precioArriendo": 1800000,
  "precioAdministracion": 220000,
  "descripcion": "Descripción actualizada",
  "amoblado": false,
  "disponible": true
}
```

**Response 200 OK**: Inmueble actualizado con todos los campos

**Lógica de Negocio**:
- Busca el inmueble por ID
- Actualiza TODOS los campos proporcionados
- Valida que el inmueble exista
- Retorna el inmueble actualizado

---

### 6. **PUT** `/api/inmuebles/{id}/disponibilidad`
Cambiar solo el estado de disponibilidad del inmueble.

**Request Body**:
```json
{
  "disponible": false
}
```

**Response 200 OK**:
```json
{
  "id": 1,
  "disponible": false,
  "mensaje": "Disponibilidad actualizada"
}
```

**Lógica**:
- Cambia el campo `disponible` a `true` o `false`
- Usado automáticamente al crear/finalizar contratos

---

### 7. **DELETE** `/api/inmuebles/{id}`
**✨ NUEVO** - Eliminar un inmueble del sistema.

**Response 200 OK**:
```json
{
  "mensaje": "Inmueble eliminado exitosamente"
}
```

**Response 400 Bad Request** (si tiene contrato activo):
```json
{
  "error": "No se puede eliminar un inmueble con contrato activo"
}
```

**Validaciones**:
- ✅ Verifica que el inmueble exista
- ✅ **No permite eliminar** si tiene un contrato ACTIVO
- ✅ Soft delete: Marca `activo = false` en lugar de eliminar el registro

---

## 🔗 Dependencias

### Otros Servicios

**Servicio de Contratos** (:8084):
- Consulta si el inmueble tiene contratos activos antes de eliminar
- Se comunica al crear contratos para marcar como NO disponible
- Se comunica al finalizar contratos para marcar como disponible

---

## 📊 Casos de Uso

### 1. Flujo Completo: Registrar y Arrendar Inmueble

```bash
# 1. Crear inmueble (Propietario)
curl -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d '{
    "propietarioId": 1,
    "tipo": "APARTAMENTO",
    "direccion": "Carrera 10 #20-30",
    "ciudad": "Bogotá",
    "precioArriendo": 1500000,
    "habitaciones": 2,
    "banos": 1
  }'
# Response: { "id": 5, "disponible": true, ... }

# 2. Listar inmuebles disponibles
curl "http://localhost:8083/api/inmuebles"

# 3. Crear contrato (marca como NO disponible automáticamente)
curl -X POST http://localhost:8084/api/contratos \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 5,
    "propietarioId": 1,
    "arrendatarioId": 7,
    "fechaInicio": "2025-11-01",
    "fechaFin": "2026-11-01",
    "valorArriendo": 1500000
  }'

# 4. Verificar que inmueble está NO disponible
curl http://localhost:8083/api/inmuebles/5
# Response: { "id": 5, "disponible": false, ... }
```

### 2. Actualizar Inmueble

```bash
# Actualizar precio y características
curl -X PUT http://localhost:8083/api/inmuebles/5 \
  -H "Content-Type: application/json" \
  -d '{
    "propietarioId": 1,
    "tipo": "APARTAMENTO",
    "direccion": "Carrera 10 #20-30",
    "ciudad": "Bogotá",
    "precioArriendo": 1600000,
    "habitaciones": 2,
    "banos": 1,
    "disponible": true
  }'
```

### 3. Gestión de Disponibilidad

```bash
# Marcar como NO disponible
curl -X PUT http://localhost:8083/api/inmuebles/5/disponibilidad \
  -H "Content-Type: application/json" \
  -d '{ "disponible": false }'

# Marcar como disponible nuevamente
curl -X PUT http://localhost:8083/api/inmuebles/5/disponibilidad \
  -H "Content-Type: application/json" \
  -d '{ "disponible": true }'
```

### 4. Eliminar Inmueble

```bash
# Intentar eliminar (falla si tiene contrato activo)
curl -X DELETE http://localhost:8083/api/inmuebles/5

# Si tiene contrato activo:
# Response 400: { "error": "No se puede eliminar un inmueble con contrato activo" }

# Si NO tiene contrato:
# Response 200: { "mensaje": "Inmueble eliminado exitosamente" }
```

---

## 🧪 Testing

### Datos de Prueba

```bash
# Crear varios inmuebles de prueba
curl -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d '{
    "propietarioId": 1,
    "tipo": "CASA",
    "direccion": "Calle 50 #10-20",
    "ciudad": "Medellín",
    "precioArriendo": 2000000,
    "habitaciones": 3,
    "banos": 2,
    "descripcion": "Casa espaciosa con jardín"
  }'

curl -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d '{
    "propietarioId": 1,
    "tipo": "LOCAL",
    "direccion": "Avenida 80 #45-10",
    "ciudad": "Cali",
    "precioArriendo": 3000000,
    "area": 120.0,
    "descripcion": "Local comercial en zona comercial"
  }'
```

---

## 🐛 Errores Comunes

### Error: "Inmueble no encontrado"
**Causa**: ID no existe en la base de datos  
**Solución**: Verificar el ID o listar inmuebles disponibles

### Error: "No se puede eliminar un inmueble con contrato activo"
**Causa**: Intento de eliminar un inmueble que tiene un contrato ACTIVO  
**Solución**: 
1. Finalizar el contrato primero
2. Luego eliminar el inmueble

### Error: "Precio de arriendo debe ser mayor a 0"
**Causa**: Precio inválido  
**Solución**: Proporcionar un valor positivo

---

## 🚀 Mejoras Futuras

### Fase 1 (Corto Plazo)
- [ ] **Imágenes**: Subir y gestionar fotos del inmueble
- [ ] **Búsqueda Avanzada**: Filtros por rango de precio, habitaciones, ciudad
- [ ] **Geolocalización**: Coordenadas GPS y mapas
- [ ] **Amenidades**: Lista de características adicionales

### Fase 2 (Mediano Plazo)
- [ ] **Historial de Precios**: Tracking de cambios de precio
- [ ] **Valoración**: Estrellaje y comentarios
- [ ] **Tour Virtual**: Enlaces a tours 360°
- [ ] **Documentos**: PDFs de certificados, planos

---

## 📁 Estructura del Proyecto

```
inmuebles-service/
├── src/main/java/com/arrendamiento/inmuebles/
│   ├── controller/
│   │   └── InmuebleController.java       # Endpoints REST
│   ├── service/
│   │   └── InmuebleService.java          # Lógica de negocio
│   ├── repository/
│   │   └── InmuebleRepository.java       # Acceso a BD
│   ├── model/
│   │   └── Inmueble.java                 # Entidad JPA
│   └── dto/
│       ├── InmuebleRequest.java          # DTO de entrada
│       └── InmuebleResponse.java         # DTO de salida
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
**Endpoints**: 7 endpoints completos

