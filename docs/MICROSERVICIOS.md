# 🏗️ Documentación Completa de Microservicios

**Sistema de Gestión de Arrendamientos - Inmotrack**  
**Versión**: 2.0.0  
**Última actualización**: Octubre 2025  
**GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

## 📋 Tabla de Contenidos

1. [Visión General](#-visión-general)
2. [Arquitectura de Microservicios](#-arquitectura-de-microservicios)
3. [Usuarios Service](#1-usuarios-service-8086)
4. [Inmuebles Service](#2-inmuebles-service-8083)
5. [Contratos Service](#3-contratos-service-8084)
6. [Pagos Service](#4-pagos-service-8085)
7. [Mantenimiento Service](#5-mantenimiento-service-8087)
8. [Notificaciones Service](#6-notificaciones-service-8088)
9. [Comunicación Entre Servicios](#-comunicación-entre-servicios)
10. [Convenciones y Estándares](#-convenciones-y-estándares)

---

## 🎯 Visión General

Este documento unifica la documentación técnica completa de **6 microservicios principales** que componen el sistema Inmotrack. Cada servicio es independiente, con su propia base de datos y responsabilidades específicas.

### Servicios Documentados

| # | Servicio | Puerto | Endpoints | Estado | Responsabilidad |
|---|----------|--------|-----------|--------|-----------------|
| 1 | **Usuarios** | 8086 | 6 | ✅ | Autenticación y gestión de usuarios |
| 2 | **Inmuebles** | 8083 | 7 | ✅ | Catálogo de propiedades |
| 3 | **Contratos** | 8084 | 9 | ✅ | Gestión de contratos de arrendamiento |
| 4 | **Pagos** | 8085 | 6 | ✅ | Sistema de pagos y estados |
| 5 | **Mantenimiento** | 8087 | 9 | ✅ | Solicitudes y workflow de mantenimiento |
| 6 | **Notificaciones** | 8088 | 4 | ✅ | Sistema de notificaciones multicanal |

**Total**: **41 endpoints documentados**

---

## 🏗️ Arquitectura de Microservicios

### Stack Tecnológico

- **Backend**: Spring Boot 3.x
- **Base de Datos**: PostgreSQL 15
- **Comunicación**: REST API (HTTP/JSON)
- **Contenedores**: Docker + Docker Compose
- **Frontend**: Java Swing (Desktop)

### Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                 Java Swing Application                       │
│                     (ApiClient)                              │
└────────────────────┬────────────────────────────────────────┘
                     │ REST API (HTTP/JSON)
                     ▼
┌─────────────────────────────────────────────────────────────┐
│              MICROSERVICIOS (Spring Boot)                    │
├────────────────┬────────────────┬───────────────────────────┤
│ Usuarios       │ Inmuebles      │ Contratos                 │
│ :8086          │ :8083          │ :8084                     │
├────────────────┼────────────────┼───────────────────────────┤
│ Pagos          │ Mantenimiento  │ Notificaciones            │
│ :8085          │ :8087          │ :8088                     │
└────────────────┴────────────────┴───────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│              PostgreSQL Database :5432                       │
│                  arrendamiento_db                            │
└─────────────────────────────────────────────────────────────┘
```

---

# 1. 👤 Usuarios Service (:8086)

## Descripción
Microservicio responsable de la autenticación y gestión de usuarios del sistema. Maneja tres roles: ADMINISTRADOR, PROPIETARIO y ARRENDATARIO.

## Modelo de Datos

### Tabla: `usuarios`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | Identificador único |
| `nombre` | VARCHAR(100) | Nombre del usuario |
| `apellido` | VARCHAR(100) | Apellido del usuario |
| `correo` | VARCHAR(150) | Email (único, usado para login) |
| `contrasena` | VARCHAR(255) | Hash BCrypt de la contraseña |
| `telefono` | VARCHAR(20) | Teléfono de contacto |
| `direccion` | VARCHAR(255) | Dirección física |
| `cedula` | VARCHAR(20) | Número de identificación |
| `tipo_usuario` | VARCHAR(50) | ADMINISTRADOR, PROPIETARIO, ARRENDATARIO |
| `activo` | BOOLEAN | Estado de la cuenta (default: true) |
| `fecha_registro` | TIMESTAMP | Fecha de creación |

## Endpoints

### 1.1. POST `/api/usuarios/registro`
Registrar nuevo usuario.

**Request**:
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "contrasena": "password123",
  "telefono": "3001234567",
  "direccion": "Calle 123 #45-67",
  "cedula": "1234567890",
  "tipoUsuario": "PROPIETARIO"
}
```

**Response 201**:
```json
{
  "mensaje": "Usuario registrado exitosamente",
  "usuario": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "correo": "juan@example.com",
    "tipoUsuario": "PROPIETARIO",
    "activo": true
  }
}
```

**Validaciones**:
- Email único
- Contraseña mínimo 6 caracteres
- Hash BCrypt automático (10 rounds)

---

### 1.2. POST `/api/usuarios/login`
Autenticar usuario.

**Request**:
```json
{
  "correo": "admin@sistema.com",
  "contrasena": "admin123"
}
```

**Response 200**:
```json
{
  "id": 5,
  "nombre": "Admin",
  "apellido": "Sistema",
  "correo": "admin@sistema.com",
  "tipoUsuario": "ADMINISTRADOR",
  "activo": true
}
```

**Response 401**: `{ "error": "Credenciales inválidas" }`

---

### 1.3. GET `/api/usuarios/{id}`
Obtener usuario por ID.

---

### 1.4. GET `/api/usuarios?tipo={TIPO}`
Filtrar usuarios por tipo (ADMINISTRADOR | PROPIETARIO | ARRENDATARIO).

---

### 1.5. PUT `/api/usuarios/{id}/activar`
Activar cuenta de usuario.

---

### 1.6. PUT `/api/usuarios/{id}/desactivar`
Desactivar cuenta de usuario (soft delete).

## Usuarios de Prueba

| Email | Password | Rol | ID |
|-------|----------|-----|-----|
| `admin@sistema.com` | `admin123` | ADMINISTRADOR | 5 |
| `propietario@test.com` | `prop123` | PROPIETARIO | 6 |
| `inquilino@test.com` | `inqui123` | ARRENDATARIO | 7 |

---

# 2. 🏠 Inmuebles Service (:8083)

## Descripción
Microservicio responsable de la gestión del catálogo de propiedades inmobiliarias. Maneja el CRUD completo con coordinación automática de disponibilidad.

## Modelo de Datos

### Tabla: `inmuebles`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | Identificador único |
| `propietario_id` | BIGINT | ID del propietario |
| `tipo` | VARCHAR(50) | APARTAMENTO, CASA, LOCAL, OFICINA, BODEGA |
| `direccion` | VARCHAR(255) | Dirección completa |
| `ciudad` | VARCHAR(100) | Ciudad |
| `departamento` | VARCHAR(100) | Departamento/Estado |
| `area` | DECIMAL(10,2) | Área en m² |
| `habitaciones` | INT | Número de habitaciones |
| `banos` | INT | Número de baños |
| `parqueaderos` | INT | Número de parqueaderos |
| `estrato` | INT | Estrato socioeconómico |
| `precio_arriendo` | DECIMAL(15,2) | Valor mensual del arriendo |
| `precio_administracion` | DECIMAL(15,2) | Costo de administración |
| `descripcion` | TEXT | Descripción detallada |
| `amoblado` | BOOLEAN | Si está amoblado |
| `disponible` | BOOLEAN | Si está disponible (default: true) |
| `activo` | BOOLEAN | Si está activo (default: true) |
| `fecha_registro` | TIMESTAMP | Fecha de creación |

## Endpoints

### 2.1. POST `/api/inmuebles`
Crear nuevo inmueble.

**Request**:
```json
{
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30",
  "ciudad": "Bogotá",
  "area": 65.5,
  "habitaciones": 2,
  "banos": 1,
  "precioArriendo": 1500000,
  "descripcion": "Apartamento amplio y luminoso"
}
```

**Response 201**: Inmueble creado con `disponible: true`

---

### 2.2. GET `/api/inmuebles`
Listar todos los inmuebles.

---

### 2.3. GET `/api/inmuebles/{id}`
Obtener inmueble por ID.

---

### 2.4. GET `/api/inmuebles?propietarioId={id}`
Filtrar inmuebles por propietario.

---

### 2.5. PUT `/api/inmuebles/{id}`
**✨ NUEVO** - Actualizar inmueble completo.

**Request**:
```json
{
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30 ACTUALIZADO",
  "ciudad": "Bogotá",
  "area": 70.0,
  "habitaciones": 3,
  "banos": 2,
  "precioArriendo": 1800000,
  "disponible": true
}
```

---

### 2.6. PUT `/api/inmuebles/{id}/disponibilidad`
Cambiar solo disponibilidad.

**Request**: `{ "disponible": false }`

**Uso**: Automático al crear/finalizar contratos.

---

### 2.7. DELETE `/api/inmuebles/{id}`
**✨ NUEVO** - Eliminar inmueble.

**Validación**: No permite eliminar si tiene contrato ACTIVO.

---

# 3. 📄 Contratos Service (:8084)

## Descripción
Microservicio responsable de la gestión completa de contratos de arrendamiento. Coordina con Inmuebles Service para gestionar disponibilidad automáticamente.

## Modelo de Datos

### Tabla: `contratos`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | Identificador único |
| `inmueble_id` | BIGINT | ID del inmueble |
| `propietario_id` | BIGINT | ID del propietario |
| `arrendatario_id` | BIGINT | ID del arrendatario |
| `fecha_inicio` | DATE | Fecha de inicio |
| `fecha_fin` | DATE | Fecha de finalización |
| `valor_arriendo` | DECIMAL(15,2) | Valor mensual |
| `valor_administracion` | DECIMAL(15,2) | Costo de administración |
| `deposito` | DECIMAL(15,2) | Depósito de garantía |
| `dia_pago` | INT | Día del mes para pagar (1-31) |
| `observaciones` | TEXT | Notas adicionales |
| `estado` | VARCHAR(50) | ACTIVO, FINALIZADO, CANCELADO |
| `fecha_creacion` | TIMESTAMP | Fecha de creación |

## Endpoints

### 3.1. POST `/api/contratos`
Crear nuevo contrato.

**Request**:
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
  "diaPago": 5
}
```

**Lógica**:
1. Valida inmueble disponible
2. Crea contrato con estado ACTIVO
3. **Marca inmueble como NO disponible**

---

### 3.2. GET `/api/contratos`
Listar todos los contratos.

---

### 3.3. GET `/api/contratos/{id}`
Obtener contrato por ID.

---

### 3.4. GET `/api/contratos?propietarioId={id}`
Filtrar por propietario.

---

### 3.5. GET `/api/contratos?arrendatarioId={id}`
Filtrar por arrendatario.

---

### 3.6. GET `/api/contratos?inmuebleId={id}`
Filtrar por inmueble.

---

### 3.7. PUT `/api/contratos/{id}`
**✨ NUEVO** - Actualizar contrato.

---

### 3.8. PUT `/api/contratos/{id}/finalizar`
**✨ IMPLEMENTADO HOY** - Finalizar contrato.

**Request**: `{}`

**Lógica**:
1. Cambia estado a FINALIZADO
2. **Marca inmueble como DISPONIBLE**

**Response**:
```json
{
  "id": 1,
  "estado": "FINALIZADO",
  "mensaje": "Contrato finalizado exitosamente. Inmueble ahora disponible."
}
```

---

### 3.9. DELETE `/api/contratos/{id}`
**✨ NUEVO** - Eliminar contrato.

---

# 4. 💰 Pagos Service (:8085)

## Descripción
Microservicio responsable de la gestión del sistema de pagos de arriendos con seguimiento de estados.

## Modelo de Datos

### Tabla: `pagos`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | Identificador único |
| `contrato_id` | BIGINT | ID del contrato |
| `arrendatario_id` | BIGINT | ID del arrendatario |
| `fecha` | DATE | Fecha del pago |
| `valor` | DECIMAL(15,2) | Monto del pago |
| `metodo_pago` | VARCHAR(50) | EFECTIVO, TRANSFERENCIA, TARJETA, CHEQUE |
| `estado` | VARCHAR(50) | PENDIENTE, PAGADO, VENCIDO, PARCIAL |
| `comprobante` | VARCHAR(255) | Número de comprobante |
| `mes_correspondiente` | VARCHAR(50) | Mes del pago |
| `valor_mora` | DECIMAL(15,2) | Valor de mora si aplica |
| `observaciones` | TEXT | Notas adicionales |
| `fecha_registro` | TIMESTAMP | Fecha de creación |

## Endpoints

### 4.1. POST `/api/pagos`
Registrar nuevo pago.

**Request**:
```json
{
  "contratoId": 1,
  "arrendatarioId": 7,
  "fecha": "2025-10-05",
  "valor": 1500000,
  "metodoPago": "TRANSFERENCIA",
  "mesCorrespondiente": "Octubre 2025",
  "comprobante": "TRF-001234"
}
```

**Response 201**: Pago creado con estado PENDIENTE

---

### 4.2. GET `/api/pagos`
Listar todos los pagos.

---

### 4.3. GET `/api/pagos?contrato={id}`
Filtrar por contrato.

---

### 4.4. GET `/api/pagos?arrendatario={id}`
Filtrar por arrendatario.

---

### 4.5. PUT `/api/pagos/{id}/estado?estado={ESTADO}`
**✨ IMPLEMENTADO HOY** - Cambiar estado de pago.

**Estados**: PENDIENTE | PAGADO | VENCIDO | PARCIAL

**Ejemplo**: `PUT /api/pagos/1/estado?estado=PAGADO`

**Validación**: No se puede cambiar de PAGADO a otro estado.

---

### 4.6. Atajos Rápidos (implementados en UI)
- **Marcar como Pagado**: Admin confirma pago
- **Marcar como Vencido**: Pago fuera de fecha

---

# 5. 🔧 Mantenimiento Service (:8087)

## Descripción
Microservicio responsable de la gestión completa del sistema de mantenimiento con workflow de estados.

## Modelo de Datos

### Tabla: `solicitudes_mantenimiento`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | Identificador único |
| `inmueble_id` | BIGINT | ID del inmueble |
| `solicitante_id` | BIGINT | ID del solicitante |
| `titulo` | VARCHAR(255) | Título de la solicitud |
| `descripcion` | TEXT | Descripción detallada |
| `tipo` | VARCHAR(50) | PLOMERIA, ELECTRICO, PINTURA, LIMPIEZA, OTRO |
| `prioridad` | VARCHAR(50) | BAJA, MEDIA, ALTA, URGENTE |
| `estado` | VARCHAR(50) | PENDIENTE, APROBADO, EN_PROCESO, COMPLETADO, RECHAZADO |
| `tecnico` | VARCHAR(255) | Técnico asignado |
| `costo_estimado` | DECIMAL(15,2) | Costo estimado |
| `costo_real` | DECIMAL(15,2) | Costo real (al completar) |
| `fecha_completado` | TIMESTAMP | Fecha de completación |
| `observaciones` | TEXT | Notas adicionales |
| `fecha_solicitud` | TIMESTAMP | Fecha de creación |

## Workflow de Estados

```
PENDIENTE → APROBADO → EN_PROCESO → COMPLETADO ✅
    ↓           ↓
RECHAZADO   RECHAZADO ❌
```

## Endpoints

### 5.1. POST `/api/mantenimiento`
Crear solicitud.

**Request**:
```json
{
  "inmuebleId": 1,
  "solicitanteId": 7,
  "titulo": "Fuga de agua en baño",
  "descripcion": "Hay una fuga constante...",
  "tipo": "PLOMERIA",
  "prioridad": "ALTA"
}
```

**Response**: Estado inicial PENDIENTE

---

### 5.2. GET `/api/mantenimiento`
Listar todas las solicitudes.

---

### 5.3. GET `/api/mantenimiento?solicitante={id}`
Filtrar por solicitante.

---

### 5.4. GET `/api/mantenimiento?inmueble={id}`
Filtrar por inmueble.

---

### 5.5. PUT `/api/mantenimiento/{id}`
**✨ NUEVO** - Actualizar solicitud.

---

### 5.6. PUT `/api/mantenimiento/{id}/aprobar`
**✨ IMPLEMENTADO HOY** - Aprobar solicitud.

**Request**: `{}`  
**Transición**: PENDIENTE → APROBADO

---

### 5.7. PUT `/api/mantenimiento/{id}/iniciar`
**✨ IMPLEMENTADO HOY** - Iniciar trabajo.

**Request**: `{}`  
**Transición**: APROBADO → EN_PROCESO

---

### 5.8. PUT `/api/mantenimiento/{id}/completar`
**✨ IMPLEMENTADO HOY** - Completar trabajo.

**Request**:
```json
{
  "costoReal": 180000,
  "observaciones": "Trabajo completado satisfactoriamente"
}
```

**Transición**: EN_PROCESO → COMPLETADO

---

### 5.9. PUT `/api/mantenimiento/{id}/rechazar`
**✨ IMPLEMENTADO HOY** - Rechazar solicitud.

**Request**:
```json
{
  "motivo": "Solicitud fuera de cobertura"
}
```

**Transición**: PENDIENTE/APROBADO → RECHAZADO

---

# 6. 📬 Notificaciones Service (:8088)

## Descripción
Microservicio responsable del sistema de notificaciones multicanal.

## Modelo de Datos

### Tabla: `notificaciones`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGINT | Identificador único |
| `destinatario` | VARCHAR(255) | Email o teléfono |
| `asunto` | VARCHAR(255) | Asunto de la notificación |
| `mensaje` | TEXT | Contenido del mensaje |
| `tipo` | VARCHAR(50) | EMAIL, SMS, WHATSAPP |
| `estado` | VARCHAR(50) | PENDIENTE, ENVIADO, FALLIDO |
| `contrato_id` | BIGINT | Referencia opcional |
| `pago_id` | BIGINT | Referencia opcional |
| `fecha_creacion` | TIMESTAMP | Fecha de creación |
| `fecha_envio` | TIMESTAMP | Fecha de envío |

## Endpoints

### 6.1. POST `/api/notificaciones`
Crear notificación.

**Request**:
```json
{
  "destinatario": "inquilino@test.com",
  "asunto": "Recordatorio de Pago",
  "mensaje": "Su pago vence el día 5",
  "tipo": "EMAIL",
  "contratoId": 1
}
```

---

### 6.2. GET `/api/notificaciones`
Listar todas las notificaciones.

---

### 6.3. GET `/api/notificaciones?destinatario={email}`
Filtrar por destinatario.

---

### 6.4. GET `/api/notificaciones/{id}`
Obtener notificación por ID.

---

## Funcionalidad Especial: Envío Masivo (UI)

**Implementado en Panel Admin**:
1. Seleccionar destinatarios (Todos, Propietarios, Arrendatarios)
2. Seleccionar tipo (EMAIL, SMS, WHATSAPP)
3. Ingresar asunto y mensaje
4. Envío automático a múltiples usuarios

---

# 🔗 Comunicación Entre Servicios

## Patrones de Comunicación

### 1. Contratos ↔ Inmuebles

**Al Crear Contrato**:
```
Contratos Service → GET /api/inmuebles/{id}  (Verificar disponibilidad)
Contratos Service → PUT /api/inmuebles/{id}/disponibilidad { "disponible": false }
```

**Al Finalizar Contrato**:
```
Contratos Service → PUT /api/inmuebles/{id}/disponibilidad { "disponible": true }
```

### 2. Notificaciones ← Todos los Servicios

Cualquier servicio puede enviar notificaciones:
```
Any Service → POST /api/notificaciones
```

---

# 📏 Convenciones y Estándares

## Response Format

**Success (200/201)**:
```json
{
  "id": 1,
  "campo": "valor"
}
```

**Error (400/404/500)**:
```json
{
  "error": "Mensaje de error",
  "mensaje": "Detalles adicionales"
}
```

## Códigos de Estado HTTP

| Código | Significado | Uso |
|--------|-------------|-----|
| 200 | OK | GET, PUT, DELETE exitoso |
| 201 | Created | POST exitoso |
| 400 | Bad Request | Datos inválidos |
| 401 | Unauthorized | Credenciales incorrectas |
| 404 | Not Found | Recurso no encontrado |
| 409 | Conflict | Email duplicado, etc. |
| 500 | Internal Server Error | Error del servidor |

## Naming Conventions

### Endpoints
- **Plural**: `/api/usuarios`, `/api/contratos`
- **Verbos HTTP**: GET (leer), POST (crear), PUT (actualizar), DELETE (eliminar)
- **Query Params**: `?propietarioId=1`, `?tipo=ARRENDATARIO`

### Campos JSON
- **camelCase** en requests/responses: `propietarioId`, `valorArriendo`
- **snake_case** en base de datos: `propietario_id`, `valor_arriendo`

---

# 🧪 Testing con Postman

## Colección Completa

**Ubicación**: `microservicios/postman-collections/`

**Archivos**:
1. `Inmotrack-Complete.postman_collection.json` - 52 endpoints
2. `Inmotrack-Local.postman_environment.json` - Variables configuradas

**Importar en Postman**:
1. Import → Seleccionar ambos archivos
2. Seleccionar environment "Inmotrack - Local"
3. ¡Listo para probar!

## Flujo de Prueba Completo

```bash
# 1. Login
POST /api/usuarios/login

# 2. Crear Inmueble
POST /api/inmuebles → { id: 5 }

# 3. Crear Contrato
POST /api/contratos → { id: 3, inmuebleId: 5 }
# Automáticamente marca inmueble como NO disponible

# 4. Registrar Pago
POST /api/pagos → { id: 10, contratoId: 3 }

# 5. Marcar Pago como Pagado
PUT /api/pagos/10/estado?estado=PAGADO

# 6. Solicitar Mantenimiento
POST /api/mantenimiento → { id: 15, inmuebleId: 5 }

# 7. Workflow de Mantenimiento
PUT /api/mantenimiento/15/aprobar
PUT /api/mantenimiento/15/iniciar
PUT /api/mantenimiento/15/completar { "costoReal": 180000 }

# 8. Finalizar Contrato
PUT /api/contratos/3/finalizar
# Automáticamente marca inmueble como DISPONIBLE
```

---

# 📊 Estadísticas del Sistema

| Métrica | Valor |
|---------|-------|
| **Microservicios Documentados** | 6 |
| **Total de Endpoints** | 41 |
| **Tablas en Base de Datos** | 7 |
| **Líneas de Documentación** | 2,500+ |
| **Workflows Implementados** | 3 (Contratos, Pagos, Mantenimiento) |
| **Tipos de Notificaciones** | 3 (EMAIL, SMS, WHATSAPP) |

---

# 🚀 Próximos Pasos

## Fase 1 (Corto Plazo)
- [ ] Implementar `PUT /api/usuarios/{id}` para editar perfil
- [ ] Agregar `PUT /api/notificaciones/{id}/marcar-leida`
- [ ] Paginación en endpoints de listado
- [ ] Búsqueda avanzada de inmuebles

## Fase 2 (Mediano Plazo)
- [ ] JWT Tokens para autenticación
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Service Discovery (Eureka)
- [ ] Circuit Breaker (Resilience4j)

## Fase 3 (Largo Plazo)
- [ ] Event-Driven Architecture (Kafka)
- [ ] CQRS para reportes
- [ ] Cache distribuido (Redis)
- [ ] Base de datos por microservicio

---

# 🔗 Enlaces Útiles

### Documentación
- **API Completa**: [docs/API.md](./API.md)
- **Arquitectura**: [docs/ARCHITECTURE.md](./ARCHITECTURE.md)
- **Inicio Rápido**: [INICIO_RAPIDO.md](../INICIO_RAPIDO.md)
- **Postman Collections**: [microservicios/postman-collections/](../microservicios/postman-collections/)

### GitHub
- **Repositorio**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Issues**: [Reportar Bugs](https://github.com/durregou/Inmotrack/issues)
- **Pull Requests**: [Contribuir](https://github.com/durregou/Inmotrack/pulls)
- **Autor**: [David Urrego](https://github.com/durregou)

---

**Última actualización**: Octubre 2025  
**Versión**: 2.0.0  
**Estado**: ✅ Documentación Completa

---

> 💡 **Nota**: Este documento unifica toda la documentación de microservicios en un solo lugar. Para documentación más detallada de cada servicio, consulta los archivos individuales en `docs/microservices/`.

