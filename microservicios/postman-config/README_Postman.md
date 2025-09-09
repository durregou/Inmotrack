# 📮 Configuración de Postman para Microservicios de Arrendamiento

Esta guía te ayudará a configurar Postman para probar todos los endpoints de los microservicios.

## 🛠️ Configuración Inicial

### 1. Crear Entorno (Environment)

1. Abre Postman
2. Haz clic en **"Environments"** en la barra lateral
3. Clic en **"Create Environment"**
4. Nombra el entorno: `Arrendamiento - Local`

### 2. Variables de Entorno

Agrega estas variables al entorno:

| Variable | Initial Value | Current Value | Description |
|----------|---------------|---------------|-------------|
| `base_url` | `http://localhost` | `http://localhost` | URL base de los servicios |
| `admin_port` | `8081` | `8081` | Puerto del servicio de administración |
| `propietarios_port` | `8082` | `8082` | Puerto del servicio de propietarios |
| `inmuebles_port` | `8083` | `8083` | Puerto del servicio de inmuebles |
| `contratos_port` | `8084` | `8084` | Puerto del servicio de contratos |
| `pagos_port` | `8085` | `8085` | Puerto del servicio de pagos |
| `jwt_token` |  |  | Token JWT para autenticación |

### 3. Activar el Entorno
- En la esquina superior derecha, selecciona **"Arrendamiento - Local"**

## 📁 Estructura de Colecciones Recomendada

Crea estas colecciones en Postman:

```
📁 Microservicios Arrendamiento
├── 🔐 1. Administración Service
├── 👤 2. Propietarios Service  
├── 🏠 3. Inmuebles Service
├── 📋 4. Contratos Service
└── 💰 5. Pagos Service
```

## 🔐 1. Administración Service

### Login de Administrador
- **Método**: POST
- **URL**: `{{base_url}}:{{admin_port}}/api/admin/login`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
```json
{
    "correo": "admin@sistema.com",
    "contrasena": "admin123"
}
```

### Consultar Administrador
- **Método**: GET
- **URL**: `{{base_url}}:{{admin_port}}/api/admin/1`
- **Headers**: `Authorization: Bearer {{jwt_token}}`

## 👤 2. Propietarios Service

### Registrar Propietario
- **Método**: POST
- **URL**: `{{base_url}}:{{propietarios_port}}/api/propietarios`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
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

### Obtener Propietario
- **Método**: GET
- **URL**: `{{base_url}}:{{propietarios_port}}/api/propietarios/1`

## 🏠 3. Inmuebles Service

### Registrar Inmueble
- **Método**: POST
- **URL**: `{{base_url}}:{{inmuebles_port}}/api/inmuebles`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
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

### Listar Inmuebles
- **Método**: GET
- **URL**: `{{base_url}}:{{inmuebles_port}}/api/inmuebles`

### Listar por Tipo
- **Método**: GET
- **URL**: `{{base_url}}:{{inmuebles_port}}/api/inmuebles?tipo=Apartamento`

### Actualizar Disponibilidad
- **Método**: PUT
- **URL**: `{{base_url}}:{{inmuebles_port}}/api/inmuebles/1/disponibilidad?disponible=false`

## 📋 4. Contratos Service

### Crear Contrato
- **Método**: POST
- **URL**: `{{base_url}}:{{contratos_port}}/api/contratos`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
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

### Consultar Contrato
- **Método**: GET
- **URL**: `{{base_url}}:{{contratos_port}}/api/contratos/1`

### Listar por Propietario
- **Método**: GET
- **URL**: `{{base_url}}:{{contratos_port}}/api/contratos?propietarioId=1`

### Finalizar Contrato
- **Método**: PUT
- **URL**: `{{base_url}}:{{contratos_port}}/api/contratos/1/finalizar`

## 💰 5. Pagos Service

### Registrar Pago
- **Método**: POST
- **URL**: `{{base_url}}:{{pagos_port}}/api/pagos`
- **Headers**: `Content-Type: application/json`
- **Body** (raw JSON):
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

### Historial de Pagos por Contrato
- **Método**: GET
- **URL**: `{{base_url}}:{{pagos_port}}/api/pagos?contrato=1`

### Actualizar Estado de Pago
- **Método**: PUT
- **URL**: `{{base_url}}:{{pagos_port}}/api/pagos/1/estado?estado=PAGADO`

## 🔧 Scripts de Postman Útiles

### Para Login (Tests tab):
```javascript
// Guardar token JWT automáticamente
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.environment.set("jwt_token", jsonData.token);
    console.log("Token guardado: " + jsonData.token);
}
```

### Para validar respuestas (Tests tab):
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Response time is less than 2000ms", function () {
    pm.expect(pm.response.responseTime).to.be.below(2000);
});
```

## 🚀 Flujo de Pruebas Recomendado

1. **Crear datos básicos**:
   - Registrar propietario
   - Registrar inmueble
   
2. **Crear contrato**:
   - Crear contrato (esto marca el inmueble como no disponible)
   
3. **Gestionar pagos**:
   - Registrar pagos del contrato
   - Consultar historial

4. **Finalizar proceso**:
   - Finalizar contrato (esto marca el inmueble como disponible nuevamente)

## 📊 Códigos de Respuesta Esperados

| Endpoint | Éxito | Error Validación | Error Servidor |
|----------|--------|------------------|----------------|
| POST | 201 Created | 400 Bad Request | 500 Internal Error |
| GET | 200 OK | 404 Not Found | 500 Internal Error |
| PUT | 200 OK | 400 Bad Request | 500 Internal Error |

## 🔍 Troubleshooting

### Error de conexión
- Verificar que los microservicios estén ejecutándose: `docker-compose ps`
- Verificar puertos correctos en las variables de entorno

### Error 401 (Unauthorized)
- Verificar que el token JWT esté configurado correctamente
- Re-ejecutar el login para obtener un nuevo token

### Error 400 (Bad Request)
- Revisar el formato JSON del body
- Verificar que todos los campos obligatorios estén presentes
