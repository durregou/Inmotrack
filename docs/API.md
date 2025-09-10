# 📋 Documentación de APIs

Esta documentación describe todos los endpoints disponibles en los microservicios del sistema de arrendamiento.

## 📖 Índice

- [🔐 Administración Service](#-administración-service)
- [👤 Propietarios Service](#-propietarios-service)
- [🏠 Inmuebles Service](#-inmuebles-service)
- [📋 Contratos Service](#-contratos-service)
- [💰 Pagos Service](#-pagos-service)

---

## 🔐 Administración Service

**Base URL:** `http://localhost:8081`

### Authentication

#### Login de Administrador

Autentica un administrador y retorna un token JWT.

**Endpoint:** `POST /api/admin/login`

**Request:**
```json
{
    "correo": "admin@sistema.com",
    "contrasena": "admin123"
}
```

**Response (200 OK):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tipo": "Bearer",
    "id": 1,
    "nombre": "Administrador",
    "correo": "admin@sistema.com"
}
```

**Errores:**
- `401 Unauthorized` - Credenciales incorrectas
- `400 Bad Request` - Datos de entrada inválidos

#### Consultar Administrador

Obtiene la información de un administrador específico.

**Endpoint:** `GET /api/admin/{id}`

**Headers:**
```
Authorization: Bearer {jwt_token}
```

**Response (200 OK):**
```json
{
    "id": 1,
    "nombre": "Administrador",
    "correo": "admin@sistema.com",
    "telefono": "3001234567",
    "activo": true
}
```

**Errores:**
- `404 Not Found` - Administrador no encontrado
- `401 Unauthorized` - Token inválido

---

## 👤 Propietarios Service

**Base URL:** `http://localhost:8082`

### Gestión de Propietarios

#### Registrar Propietario

Registra un nuevo propietario en el sistema.

**Endpoint:** `POST /api/propietarios`

**Request:**
```json
{
    "nombre": "Juan Carlos",
    "apellido": "Pérez López",
    "correo": "juan.perez@email.com",
    "contrasena": "password123",
    "telefono": "3001234567",
    "direccion": "Calle 123 #45-67, Bogotá",
    "cedula": "12345678"
}
```

**Response (201 Created):**
```json
{
    "id": 1,
    "nombre": "Juan Carlos",
    "apellido": "Pérez López",
    "correo": "juan.perez@email.com",
    "telefono": "3001234567",
    "direccion": "Calle 123 #45-67, Bogotá",
    "cedula": "12345678",
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
}
```

**Validaciones:**
- `nombre`: Obligatorio, máximo 100 caracteres
- `correo`: Obligatorio, formato email válido, único
- `contrasena`: Obligatorio, mínimo 6 caracteres
- `cedula`: Única (si se proporciona)

**Errores:**
- `400 Bad Request` - Email ya registrado o datos inválidos
- `500 Internal Server Error` - Error del servidor

#### Obtener Propietario

Obtiene la información de un propietario específico.

**Endpoint:** `GET /api/propietarios/{id}`

**Response (200 OK):**
```json
{
    "id": 1,
    "nombre": "Juan Carlos",
    "apellido": "Pérez López",
    "correo": "juan.perez@email.com",
    "telefono": "3001234567",
    "direccion": "Calle 123 #45-67, Bogotá",
    "cedula": "12345678",
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
}
```

**Errores:**
- `404 Not Found` - Propietario no encontrado

---

## 🏠 Inmuebles Service

**Base URL:** `http://localhost:8083`

### Gestión de Inmuebles

#### Registrar Inmueble

Registra un nuevo inmueble en el sistema.

**Endpoint:** `POST /api/inmuebles`

**Request:**
```json
{
    "propietarioId": 1,
    "tipo": "Apartamento",
    "direccion": "Carrera 15 #85-42, Apto 501",
    "ciudad": "Bogotá",
    "departamento": "Cundinamarca",
    "area": 85.5,
    "habitaciones": 3,
    "banos": 2,
    "parqueaderos": 1,
    "precioArriendo": 1500000,
    "precioAdministracion": 180000,
    "descripcion": "Apartamento moderno con excelente ubicación",
    "amoblado": false,
    "disponible": true
}
```

**Response (201 Created):**
```json
{
    "id": 1,
    "propietarioId": 1,
    "tipo": "Apartamento",
    "direccion": "Carrera 15 #85-42, Apto 501",
    "ciudad": "Bogotá",
    "departamento": "Cundinamarca",
    "area": 85.5,
    "habitaciones": 3,
    "banos": 2,
    "parqueaderos": 1,
    "precioArriendo": 1500000.00,
    "precioAdministracion": 180000.00,
    "descripcion": "Apartamento moderno con excelente ubicación",
    "amoblado": false,
    "disponible": true,
    "fechaRegistro": "2024-01-15T10:30:00",
    "activo": true
}
```

#### Listar Inmuebles

Lista inmuebles con filtros opcionales.

**Endpoint:** `GET /api/inmuebles`

**Query Parameters:**
- `tipo` - Filtrar por tipo de inmueble
- `ciudad` - Filtrar por ciudad
- `propietarioId` - Filtrar por propietario
- `todos=true` - Incluir inmuebles no disponibles

**Ejemplos:**
```bash
GET /api/inmuebles                           # Solo disponibles y activos
GET /api/inmuebles?tipo=Apartamento          # Apartamentos disponibles
GET /api/inmuebles?ciudad=Bogotá            # Inmuebles en Bogotá
GET /api/inmuebles?propietarioId=1          # Inmuebles de un propietario
GET /api/inmuebles?todos=true               # Todos los inmuebles
```

**Response (200 OK):**
```json
[
    {
        "id": 1,
        "propietarioId": 1,
        "tipo": "Apartamento",
        "direccion": "Carrera 15 #85-42, Apto 501",
        "ciudad": "Bogotá",
        "precioArriendo": 1500000.00,
        "disponible": true,
        // ... más campos
    }
]
```

#### Obtener Inmueble

Obtiene un inmueble específico.

**Endpoint:** `GET /api/inmuebles/{id}`

#### Actualizar Disponibilidad

Actualiza la disponibilidad de un inmueble.

**Endpoint:** `PUT /api/inmuebles/{id}/disponibilidad`

**Query Parameters:**
- `disponible` - true/false

**Ejemplo:**
```bash
PUT /api/inmuebles/1/disponibilidad?disponible=false
```

---

## 📋 Contratos Service

**Base URL:** `http://localhost:8084`

### Gestión de Contratos

#### Crear Contrato

Crea un nuevo contrato de arrendamiento.

**Endpoint:** `POST /api/contratos`

**Request:**
```json
{
    "inmuebleId": 1,
    "propietarioId": 1,
    "arrendatarioId": 1,
    "fechaInicio": "2024-01-01",
    "fechaFin": "2024-12-31",
    "valorArriendo": 1500000,
    "valorAdministracion": 180000,
    "deposito": 1500000,
    "diaPago": 5,
    "observaciones": "Contrato de arrendamiento estándar"
}
```

**Response (201 Created):**
```json
{
    "id": 1,
    "inmuebleId": 1,
    "propietarioId": 1,
    "arrendatarioId": 1,
    "fechaInicio": "2024-01-01",
    "fechaFin": "2024-12-31",
    "valorArriendo": 1500000.00,
    "valorAdministracion": 180000.00,
    "deposito": 1500000.00,
    "diaPago": 5,
    "estado": "ACTIVO",
    "observaciones": "Contrato de arrendamiento estándar",
    "fechaCreacion": "2024-01-15T10:30:00",
    "activo": true
}
```

**Validaciones:**
- El inmueble debe estar disponible
- El propietario debe existir
- No puede haber otro contrato activo para el mismo inmueble
- `fechaFin` debe ser posterior a `fechaInicio`

#### Consultar Contrato

**Endpoint:** `GET /api/contratos/{id}`

#### Listar Contratos

**Endpoint:** `GET /api/contratos`

**Query Parameters:**
- `propietarioId` - Contratos de un propietario
- `arrendatarioId` - Contratos de un arrendatario
- `inmuebleId` - Contratos de un inmueble
- `soloActivos=true` - Solo contratos activos

#### Finalizar Contrato

Finaliza un contrato y marca el inmueble como disponible.

**Endpoint:** `PUT /api/contratos/{id}/finalizar`

**Response (200 OK):**
```json
{
    "id": 1,
    "estado": "FINALIZADO",
    // ... otros campos
}
```

---

## 💰 Pagos Service

**Base URL:** `http://localhost:8085`

### Gestión de Pagos

#### Registrar Pago

Registra un nuevo pago.

**Endpoint:** `POST /api/pagos`

**Request:**
```json
{
    "contratoId": 1,
    "arrendatarioId": 1,
    "valor": 1680000,
    "fecha": "2024-01-05",
    "mesCorrespondiente": "2024-01-01",
    "tipo": "ARRIENDO",
    "estado": "PAGADO",
    "metodoPago": "TRANSFERENCIA_BANCARIA",
    "referenciaTransaccion": "TXN123456789",
    "observaciones": "Pago completo enero 2024"
}
```

**Tipos de Pago:**
- `ARRIENDO`, `ADMINISTRACION`, `DEPOSITO`, `MORA`, `SERVICIOS_PUBLICOS`, `OTROS`

**Estados de Pago:**
- `PENDIENTE`, `PAGADO`, `VENCIDO`, `PARCIAL`

**Métodos de Pago:**
- `EFECTIVO`, `TRANSFERENCIA_BANCARIA`, `CHEQUE`, `TARJETA_CREDITO`, `TARJETA_DEBITO`, `PSE`, `NEQUI`, `DAVIPLATA`

#### Historial de Pagos

**Endpoint:** `GET /api/pagos`

**Query Parameters:**
- `contrato={id}` - Historial de pagos por contrato
- `arrendatario={id}` - Pagos de un arrendatario
- `estado={estado}` - Filtrar por estado
- `tipo={tipo}` - Filtrar por tipo
- `year={año}&month={mes}` - Pagos de un mes específico
- `vencidos=true` - Pagos vencidos
- `porVencerEnDias={días}` - Pagos que vencen en X días

**Ejemplos:**
```bash
GET /api/pagos?contrato=1                    # Historial por contrato
GET /api/pagos?arrendatario=1               # Pagos de arrendatario
GET /api/pagos?estado=PENDIENTE             # Pagos pendientes
GET /api/pagos?vencidos=true                # Pagos vencidos
GET /api/pagos?year=2024&month=1            # Pagos de enero 2024
```

#### Actualizar Estado de Pago

**Endpoint:** `PUT /api/pagos/{id}/estado`

**Query Parameters:**
- `estado` - Nuevo estado del pago

**Ejemplo:**
```bash
PUT /api/pagos/1/estado?estado=PAGADO
```

#### Contar Pagos Pendientes

**Endpoint:** `GET /api/pagos/contrato/{contratoId}/pendientes/count`

**Response:**
```json
{
    "count": 3
}
```

---

## 🔧 Códigos de Respuesta HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| 200 OK | Éxito | GET, PUT exitosos |
| 201 Created | Creado | POST exitosos |
| 400 Bad Request | Datos inválidos | Validación fallida |
| 401 Unauthorized | No autorizado | Token inválido/faltante |
| 404 Not Found | No encontrado | Recurso inexistente |
| 500 Internal Server Error | Error servidor | Error inesperado |

## 🔍 Headers Comunes

### Request Headers
```
Content-Type: application/json
Authorization: Bearer {jwt_token}  # Solo para endpoints protegidos
```

### Response Headers
```
Content-Type: application/json
X-Request-ID: {unique_id}  # Para rastreo de requests
```

## 📱 Ejemplos de Uso con curl

### Flujo Completo

```bash
# 1. Registrar propietario
curl -X POST http://localhost:8082/api/propietarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Pérez","correo":"juan@email.com","contrasena":"123456"}'

# 2. Crear inmueble
curl -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d '{"propietarioId":1,"tipo":"Casa","direccion":"Calle 123","precioArriendo":1000000}'

# 3. Crear contrato
curl -X POST http://localhost:8084/api/contratos \
  -H "Content-Type: application/json" \
  -d '{"inmuebleId":1,"propietarioId":1,"arrendatarioId":1,"fechaInicio":"2024-01-01","fechaFin":"2024-12-31","valorArriendo":1000000}'

# 4. Registrar pago
curl -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d '{"contratoId":1,"arrendatarioId":1,"valor":1000000,"fecha":"2024-01-05","mesCorrespondiente":"2024-01-01"}'
```

## ⚠️ Limitaciones y Consideraciones

### Rate Limiting
- Máximo 100 requests por minuto por IP
- Máximo 1000 requests por hora por token JWT

### Tamaño de Requests
- Máximo 1MB por request
- Máximo 50 items en arrays

### Paginación
- Límite por defecto: 20 items
- Máximo: 100 items por página

### Cache
- Responses GET cacheadas por 5 minutos
- Headers: `Cache-Control: public, max-age=300`

---

Para más información, ver la [colección de Postman](../microservicios/postman-config/) con ejemplos completos.
