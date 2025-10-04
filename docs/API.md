# 📡 API Documentation - Versión Completa

**Última actualización**: Octubre 2025  
**Versión API**: 2.0.0  
**GitHub**: [Inmotrack](https://github.com/durregou/Inmotrack)

Documentación completa de todos los endpoints REST implementados.

---

## 📋 Índice

- [Convenciones](#-convenciones)
- [Usuarios Service](#-usuarios-service-8086)
- [Inmuebles Service](#-inmuebles-service-8083)
- [Contratos Service](#-contratos-service-8084)
- [Pagos Service](#-pagos-service-8085)
- [Mantenimiento Service](#-mantenimiento-service-8087)
- [Notificaciones Service](#-notificaciones-service-8088)
- [Propietarios Service](#-propietarios-service-8082)
- [Códigos de Estado](#-códigos-de-estado)

---

## 📌 Convenciones

### Base URL
```
http://localhost:{PORT}/api
```

### Headers
```http
Content-Type: application/json
Accept: application/json
```

### Response Format
**Success (200/201)**:
```json
{
  "id": 1,
  "campo": "valor",
  ...
}
```

**Error (400/404/500)**:
```json
{
  "error": "Mensaje de error",
  "mensaje": "Detalles adicionales"
}
```

---

## 👤 Usuarios Service (:8086)

### POST /api/usuarios/registro
Registrar nuevo usuario

**Request**:
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "contrasena": "password123",
  "telefono": "3001234567",
  "direccion": "Calle 123",
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
- Tipo: ADMINISTRADOR | PROPIETARIO | ARRENDATARIO

---

### POST /api/usuarios/login
Autenticar usuario

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

**Response 401 (Error)**:
```json
{
  "error": "Credenciales inválidas"
}
```

---

### GET /api/usuarios/{id}
Obtener usuario por ID

**Response 200**:
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "telefono": "3001234567",
  "direccion": "Calle 123",
  "cedula": "1234567890",
  "tipoUsuario": "PROPIETARIO",
  "activo": true,
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

---

### GET /api/usuarios?tipo={TIPO}
Filtrar usuarios por tipo

**Parámetros Query**:
- `tipo`: ADMINISTRADOR | PROPIETARIO | ARRENDATARIO

**Response 200**:
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "tipoUsuario": "PROPIETARIO"
  },
  ...
]
```

---

### PUT /api/usuarios/{id}/activar
Activar usuario

**Response 200**:
```json
{
  "mensaje": "Usuario activado exitosamente"
}
```

---

### PUT /api/usuarios/{id}/desactivar
Desactivar usuario

**Response 200**:
```json
{
  "mensaje": "Usuario desactivado exitosamente"
}
```

---

## 🏠 Inmuebles Service (:8083)

### POST /api/inmuebles
Crear nuevo inmueble

**Request**:
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
  "precioArriendo": 1500000,
  "precioAdministracion": 200000,
  "descripcion": "Apartamento amplio y luminoso",
  "amoblado": false
}
```

**Response 201**:
```json
{
  "id": 1,
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30",
  "ciudad": "Bogotá",
  "precioArriendo": 1500000,
  "disponible": true,
  "activo": true,
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

**Tipos válidos**: APARTAMENTO | CASA | LOCAL | OFICINA | BODEGA

---

### GET /api/inmuebles
Listar todos los inmuebles

**Response 200**:
```json
[
  {
    "id": 1,
    "tipo": "APARTAMENTO",
    "direccion": "Carrera 10 #20-30",
    "ciudad": "Bogotá",
    "precioArriendo": 1500000,
    "disponible": true
  },
  ...
]
```

---

### GET /api/inmuebles/{id}
Obtener inmueble por ID

**Response 200**:
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
  "precioArriendo": 1500000,
  "precioAdministracion": 200000,
  "descripcion": "Apartamento amplio",
  "amoblado": false,
  "disponible": true,
  "activo": true,
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

---

### GET /api/inmuebles?propietarioId={id}
Filtrar inmuebles por propietario

**Response 200**: Array de inmuebles

---

### PUT /api/inmuebles/{id}
**✨ NUEVO** - Actualizar inmueble completo

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
  "descripcion": "Descripción actualizada",
  "disponible": true
}
```

**Response 200**: Inmueble actualizado

---

### PUT /api/inmuebles/{id}/disponibilidad
Cambiar solo disponibilidad

**Request**:
```json
{
  "disponible": false
}
```

**Response 200**: Inmueble actualizado

---

### DELETE /api/inmuebles/{id}
**✨ NUEVO** - Eliminar inmueble

**Response 200**:
```json
{
  "mensaje": "Inmueble eliminado exitosamente"
}
```

**Validación**: No se puede eliminar si tiene contrato activo

---

## 📄 Contratos Service (:8084)

### POST /api/contratos
Crear nuevo contrato

**Request**:
```json
{
  "inmuebleId": 1,
  "propietarioId": 1,
  "arrendatarioId": 7,
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-12-31",
  "valorArriendo": 1500000,
  "valorAdministracion": 200000,
  "deposito": 1500000,
  "diaPago": 5,
  "observaciones": "Contrato de 1 año"
}
```

**Response 201**:
```json
{
  "id": 1,
  "inmuebleId": 1,
  "propietarioId": 1,
  "arrendatarioId": 7,
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-12-31",
  "valorArriendo": 1500000,
  "estado": "ACTIVO",
  "fechaCreacion": "2025-10-04T10:00:00"
}
```

**Lógica de Negocio**:
- Valida que inmueble esté disponible
- Marca inmueble como NO disponible
- Valida fechaFin > fechaInicio

---

### GET /api/contratos/{id}
Obtener contrato por ID

**Response 200**: Objeto contrato completo

---

### GET /api/contratos?propietarioId={id}
Filtrar contratos por propietario

---

### GET /api/contratos?arrendatarioId={id}
Filtrar contratos por arrendatario

---

### GET /api/contratos?inmuebleId={id}
Filtrar contratos por inmueble

---

### PUT /api/contratos/{id}
**✨ NUEVO** - Actualizar contrato

**Request**:
```json
{
  "fechaInicio": "2025-01-01",
  "fechaFin": "2026-01-01",
  "valorArriendo": 1600000,
  "valorAdministracion": 220000,
  "observaciones": "Extensión de contrato"
}
```

**Response 200**: Contrato actualizado

---

### PUT /api/contratos/{id}/finalizar
**✨ IMPLEMENTADO HOY** - Finalizar contrato

**Request**: Vacío `{}`

**Response 200**:
```json
{
  "id": 1,
  "estado": "FINALIZADO",
  "mensaje": "Contrato finalizado. Inmueble ahora disponible."
}
```

**Lógica de Negocio**:
1. Cambia estado a FINALIZADO
2. Marca inmueble como disponible
3. Valida que no esté ya finalizado

---

### DELETE /api/contratos/{id}
**✨ NUEVO** - Eliminar contrato

**Response 200**:
```json
{
  "mensaje": "Contrato eliminado exitosamente"
}
```

---

## 💰 Pagos Service (:8085)

### POST /api/pagos
Registrar nuevo pago

**Request**:
```json
{
  "contratoId": 1,
  "arrendatarioId": 7,
  "fecha": "2025-10-04",
  "valor": 1500000,
  "metodoPago": "TRANSFERENCIA",
  "mesCorrespondiente": "Octubre 2025",
  "comprobante": "TRF-001234",
  "observaciones": "Pago puntual"
}
```

**Response 201**:
```json
{
  "id": 1,
  "contratoId": 1,
  "arrendatarioId": 7,
  "fecha": "2025-10-04",
  "valor": 1500000,
  "metodoPago": "TRANSFERENCIA",
  "estado": "PENDIENTE",
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

**Métodos de Pago**: EFECTIVO | TRANSFERENCIA | TARJETA | CHEQUE

---

### GET /api/pagos
Listar todos los pagos

---

### GET /api/pagos?contrato={id}
Filtrar pagos por contrato

---

### GET /api/pagos?arrendatario={id}
Filtrar pagos por arrendatario

---

### PUT /api/pagos/{id}/estado?estado={ESTADO}
**✨ IMPLEMENTADO HOY** - Cambiar estado de pago

**Parámetros Query**:
- `estado`: PENDIENTE | PAGADO | VENCIDO | PARCIAL

**Response 200**:
```json
{
  "id": 1,
  "estado": "PAGADO",
  "mensaje": "Estado actualizado exitosamente"
}
```

**Validaciones**:
- No cambiar de PAGADO a otro estado
- Admin only (validación en UI)

---

## 🔧 Mantenimiento Service (:8087)

### POST /api/mantenimiento
Crear solicitud de mantenimiento

**Request**:
```json
{
  "inmuebleId": 1,
  "solicitanteId": 7,
  "titulo": "Fuga de agua en baño",
  "descripcion": "Hay una fuga en la tubería del baño principal",
  "tipo": "PLOMERIA",
  "prioridad": "ALTA"
}
```

**Response 201**:
```json
{
  "id": 1,
  "inmuebleId": 1,
  "solicitanteId": 7,
  "titulo": "Fuga de agua en baño",
  "tipo": "PLOMERIA",
  "prioridad": "ALTA",
  "estado": "PENDIENTE",
  "fechaSolicitud": "2025-10-04T10:00:00"
}
```

**Tipos**: PLOMERIA | ELECTRICO | PINTURA | LIMPIEZA | OTRO  
**Prioridad**: BAJA | MEDIA | ALTA | URGENTE

---

### GET /api/mantenimiento
Listar todas las solicitudes

---

### GET /api/mantenimiento?solicitante={id}
Filtrar por solicitante

---

### GET /api/mantenimiento?inmueble={id}
Filtrar por inmueble

---

### PUT /api/mantenimiento/{id}
**✨ NUEVO** - Actualizar solicitud

**Request**:
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

**Response 200**: Solicitud actualizada

---

### PUT /api/mantenimiento/{id}/aprobar
**✨ IMPLEMENTADO HOY** - Aprobar solicitud

**Request**: Vacío `{}`

**Response 200**:
```json
{
  "id": 1,
  "estado": "APROBADO",
  "mensaje": "Solicitud aprobada"
}
```

**Validación**: Solo si estado es PENDIENTE

---

### PUT /api/mantenimiento/{id}/iniciar
**✨ IMPLEMENTADO HOY** - Iniciar trabajo

**Request**: Vacío `{}`

**Response 200**:
```json
{
  "id": 1,
  "estado": "EN_PROCESO",
  "mensaje": "Trabajo iniciado"
}
```

**Validación**: Solo si estado es APROBADO

---

### PUT /api/mantenimiento/{id}/completar
**✨ IMPLEMENTADO HOY** - Completar trabajo

**Request**:
```json
{
  "costoReal": 180000,
  "observaciones": "Trabajo completado satisfactoriamente"
}
```

**Response 200**:
```json
{
  "id": 1,
  "estado": "COMPLETADO",
  "costoReal": 180000,
  "fechaCompletado": "2025-10-04T15:00:00",
  "mensaje": "Trabajo completado"
}
```

**Validación**: Solo si estado es EN_PROCESO

---

### PUT /api/mantenimiento/{id}/rechazar
**✨ IMPLEMENTADO HOY** - Rechazar solicitud

**Request**:
```json
{
  "motivo": "Solicitud fuera de cobertura del contrato"
}
```

**Response 200**:
```json
{
  "id": 1,
  "estado": "RECHAZADO",
  "observaciones": "Solicitud fuera de cobertura del contrato",
  "mensaje": "Solicitud rechazada"
}
```

**Validación**: Solo si estado es PENDIENTE o APROBADO

---

## 📬 Notificaciones Service (:8088)

### POST /api/notificaciones
Crear notificación

**Request**:
```json
{
  "destinatario": "inquilino@test.com",
  "asunto": "Recordatorio de Pago",
  "mensaje": "Su pago del mes de octubre vence el día 5",
  "tipo": "EMAIL",
  "contratoId": 1,
  "pagoId": null
}
```

**Response 201**:
```json
{
  "id": 1,
  "destinatario": "inquilino@test.com",
  "asunto": "Recordatorio de Pago",
  "tipo": "EMAIL",
  "estado": "PENDIENTE",
  "fechaCreacion": "2025-10-04T10:00:00"
}
```

**Tipos**: EMAIL | SMS | WHATSAPP  
**Estados**: PENDIENTE | ENVIADO | FALLIDO

---

### GET /api/notificaciones
Listar todas las notificaciones

---

### GET /api/notificaciones?destinatario={email}
Filtrar por destinatario

**Response 200**:
```json
[
  {
    "id": 1,
    "asunto": "Recordatorio de Pago",
    "mensaje": "Su pago...",
    "tipo": "EMAIL",
    "estado": "ENVIADO",
    "fechaCreacion": "2025-10-04T10:00:00",
    "fechaEnvio": "2025-10-04T10:01:00"
  },
  ...
]
```

---

### GET /api/notificaciones/{id}
Obtener notificación por ID

---

## 🏢 Propietarios Service (:8082)

### POST /api/propietarios
Crear propietario

**Request**:
```json
{
  "nombre": "Carlos",
  "apellido": "López",
  "cedula": "9876543210",
  "telefono": "3009876543",
  "email": "carlos@example.com"
}
```

---

### GET /api/propietarios/{id}
Obtener propietario por ID

---

## ⚠️ Códigos de Estado

| Código | Significado | Cuándo se usa |
|--------|-------------|---------------|
| 200 | OK | Operación exitosa (GET, PUT, DELETE) |
| 201 | Created | Recurso creado exitosamente (POST) |
| 400 | Bad Request | Datos inválidos o faltantes |
| 401 | Unauthorized | Credenciales incorrectas |
| 404 | Not Found | Recurso no encontrado |
| 405 | Method Not Allowed | Método HTTP no soportado |
| 409 | Conflict | Conflicto (ej: email ya existe) |
| 500 | Internal Server Error | Error del servidor |

---

## 📊 Ejemplos de Uso Completos

### Flujo Completo: Crear Contrato

```bash
# 1. Crear usuario arrendatario
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María",
    "apellido": "González",
    "correo": "maria@example.com",
    "contrasena": "maria123",
    "tipoUsuario": "ARRENDATARIO"
  }'

# 2. Crear inmueble (propietario)
curl -X POST http://localhost:8083/api/inmuebles \
  -H "Content-Type: application/json" \
  -d '{
    "propietarioId": 1,
    "tipo": "APARTAMENTO",
    "direccion": "Calle 50 #10-20",
    "ciudad": "Bogotá",
    "precioArriendo": 1200000,
    "habitaciones": 2,
    "banos": 1
  }'

# 3. Crear contrato
curl -X POST http://localhost:8084/api/contratos \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "propietarioId": 1,
    "arrendatarioId": 1,
    "fechaInicio": "2025-11-01",
    "fechaFin": "2026-11-01",
    "valorArriendo": 1200000,
    "diaPago": 5
  }'

# 4. Registrar primer pago
curl -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d '{
    "contratoId": 1,
    "arrendatarioId": 1,
    "fecha": "2025-11-05",
    "valor": 1200000,
    "metodoPago": "TRANSFERENCIA",
    "mesCorrespondiente": "Noviembre 2025"
  }'

# 5. Marcar pago como pagado (Admin)
curl -X PUT "http://localhost:8085/api/pagos/1/estado?estado=PAGADO" \
  -H "Content-Type: application/json"
```

---

## 🔗 Referencias

- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Documentación Técnica**: [../DOCUMENTACION.md](../DOCUMENTACION.md)
- **Arquitectura**: [./ARCHITECTURE.md](./ARCHITECTURE.md)
- **Postman Collection**: Importar desde `/microservicios/postman-config/`

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última actualización**: Octubre 2025  
**Total de Endpoints**: 52 endpoints implementados
