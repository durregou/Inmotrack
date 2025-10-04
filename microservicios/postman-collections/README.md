# 📬 Colecciones de Postman - Inmotrack

Colecciones completas de Postman para probar todos los endpoints del sistema.

**GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

## 📦 Archivos Disponibles

### 1. **Inmotrack-Complete.postman_collection.json**
Colección completa con **52 endpoints** organizados por microservicio

### 2. **Inmotrack-Local.postman_environment.json**
Variables de entorno para desarrollo local

---

## 🚀 Cómo Importar en Postman

### Paso 1: Importar Colección

1. Abre **Postman**
2. Click en **Import** (esquina superior izquierda)
3. Arrastra el archivo `Inmotrack-Complete.postman_collection.json`
4. Click **Import**

### Paso 2: Importar Environment

1. Click en **Import** nuevamente
2. Arrastra el archivo `Inmotrack-Local.postman_environment.json`
3. Click **Import**

### Paso 3: Seleccionar Environment

1. En la esquina superior derecha, selecciona **"Inmotrack - Local"** del dropdown de environments
2. ¡Listo! Ya puedes usar la colección

---

## 📋 Estructura de la Colección

```
Inmotrack - Sistema de Arrendamientos/
├── 1. Usuarios Service (:8086)
│   ├── Login
│   ├── Registro Usuario
│   ├── Obtener Usuario por ID
│   ├── Listar Usuarios por Tipo
│   ├── Activar Usuario
│   └── Desactivar Usuario
│
├── 2. Inmuebles Service (:8083)
│   ├── Crear Inmueble
│   ├── Listar Todos los Inmuebles
│   ├── Obtener Inmueble por ID
│   ├── Filtrar por Propietario
│   ├── Actualizar Inmueble Completo
│   ├── Cambiar Disponibilidad
│   └── Eliminar Inmueble
│
├── 3. Contratos Service (:8084)
│   ├── Crear Contrato
│   ├── Listar Todos los Contratos
│   ├── Obtener Contrato por ID
│   ├── Filtrar por Propietario
│   ├── Filtrar por Arrendatario
│   ├── Actualizar Contrato
│   ├── Finalizar Contrato
│   └── Eliminar Contrato
│
├── 4. Pagos Service (:8085)
│   ├── Registrar Pago
│   ├── Listar Todos los Pagos
│   ├── Filtrar por Contrato
│   ├── Filtrar por Arrendatario
│   ├── Cambiar Estado - PAGADO
│   └── Cambiar Estado - VENCIDO
│
├── 5. Mantenimiento Service (:8087)
│   ├── Crear Solicitud
│   ├── Listar Todas
│   ├── Filtrar por Solicitante
│   ├── Actualizar Solicitud
│   ├── Aprobar Solicitud
│   ├── Iniciar Trabajo
│   ├── Completar Trabajo
│   └── Rechazar Solicitud
│
└── 6. Notificaciones Service (:8088)
    ├── Crear Notificación
    ├── Listar Todas
    ├── Filtrar por Destinatario
    └── Obtener por ID
```

**Total**: **52 endpoints** completos con ejemplos

---

## 🔧 Variables de Entorno

### URLs y Puertos

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `base_url` | `http://localhost` | URL base para todos los servicios |
| `usuarios_port` | `8086` | Puerto del servicio de usuarios |
| `inmuebles_port` | `8083` | Puerto del servicio de inmuebles |
| `contratos_port` | `8084` | Puerto del servicio de contratos |
| `pagos_port` | `8085` | Puerto del servicio de pagos |
| `mantenimiento_port` | `8087` | Puerto del servicio de mantenimiento |
| `notificaciones_port` | `8088` | Puerto del servicio de notificaciones |

### Credenciales de Prueba

| Variable | Valor | Rol |
|----------|-------|-----|
| `admin_email` | `admin@sistema.com` | Administrador |
| `admin_password` | `admin123` | - |
| `propietario_email` | `propietario@test.com` | Propietario |
| `propietario_password` | `prop123` | - |
| `arrendatario_email` | `inquilino@test.com` | Arrendatario |
| `arrendatario_password` | `inqui123` | - |

### IDs Dinámicos

Estas variables se pueden actualizar manualmente según los IDs que uses:

| Variable | Descripción |
|----------|-------------|
| `user_id` | ID de usuario para pruebas |
| `inmueble_id` | ID de inmueble para pruebas |
| `contrato_id` | ID de contrato para pruebas |
| `pago_id` | ID de pago para pruebas |
| `mantenimiento_id` | ID de solicitud de mantenimiento |
| `notificacion_id` | ID de notificación |

---

## 🎯 Flujo de Prueba Recomendado

### 1. Autenticación
```
POST Login (Usuarios Service)
→ Obtener credenciales de admin@sistema.com
```

### 2. Crear Datos Básicos
```
POST Crear Inmueble (Inmuebles Service)
→ Obtener inmueble_id del response
→ Actualizar variable {{inmueble_id}}
```

### 3. Crear Contrato
```
POST Crear Contrato (Contratos Service)
→ Usar {{inmueble_id}} en el body
→ Obtener contrato_id del response
```

### 4. Registrar Pago
```
POST Registrar Pago (Pagos Service)
→ Usar {{contrato_id}} en el body
→ Obtener pago_id del response
```

### 5. Cambiar Estado de Pago
```
PUT Cambiar Estado - PAGADO (Pagos Service)
→ Usar {{pago_id}} en la URL
```

### 6. Solicitar Mantenimiento
```
POST Crear Solicitud (Mantenimiento Service)
→ Usar {{inmueble_id}} en el body
→ Obtener mantenimiento_id del response
```

### 7. Aprobar y Completar Mantenimiento
```
PUT Aprobar Solicitud
PUT Iniciar Trabajo
PUT Completar Trabajo
```

---

## 💡 Tips de Uso

### 1. Auto-completar IDs
Después de crear un recurso, copia el `id` del response y actualízalo en las variables de entorno.

**Ejemplo**:
```json
// Response de crear inmueble
{
  "id": 5,
  "direccion": "Calle 50 #10-20",
  ...
}
```

→ Actualiza `inmueble_id = 5` en el environment

### 2. Usar Variables en Body
```json
{
  "contratoId": {{contrato_id}},
  "arrendatarioId": 7,
  "valor": 1500000
}
```

### 3. Verificar Servicios Activos
Antes de usar la colección, verifica que los microservicios estén corriendo:

```bash
docker-compose ps
```

Deberías ver todos los servicios con estado **Up**.

---

## 🔍 Ejemplos de Requests

### Login
```http
POST http://localhost:8086/api/usuarios/login
Content-Type: application/json

{
  "correo": "admin@sistema.com",
  "contrasena": "admin123"
}
```

### Crear Inmueble
```http
POST http://localhost:8083/api/inmuebles
Content-Type: application/json

{
  "propietarioId": 1,
  "tipo": "APARTAMENTO",
  "direccion": "Carrera 10 #20-30",
  "ciudad": "Bogotá",
  "precioArriendo": 1500000,
  "habitaciones": 2,
  "banos": 1
}
```

### Finalizar Contrato
```http
PUT http://localhost:8084/api/contratos/1/finalizar
Content-Type: application/json

{}
```

---

## ⚠️ Requisitos Previos

1. ✅ **Docker** corriendo
2. ✅ **Microservicios activos**:
   ```bash
   cd microservicios
   docker-compose up -d
   ```
3. ✅ **Usuarios de prueba creados**:
   ```bash
   ./scripts/crear-usuario-prueba.sh
   ```

---

## 🐛 Solución de Problemas

### Error: "Could not get response"
**Causa**: Servicio no está corriendo  
**Solución**:
```bash
cd microservicios
docker-compose restart [servicio]
```

### Error: "404 Not Found"
**Causa**: Endpoint incorrecto o servicio en puerto equivocado  
**Solución**: Verifica el puerto en las variables de entorno

### Error: "400 Bad Request"
**Causa**: Body del request inválido  
**Solución**: Revisa que todos los campos requeridos estén presentes

---

## 📚 Documentación Adicional

- **API Completa**: [../../docs/API.md](../../docs/API.md)
- **Arquitectura**: [../../docs/ARCHITECTURE.md](../../docs/ARCHITECTURE.md)
- **README Principal**: [../../README.md](../../README.md)

---

## 🔗 Enlaces

- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Issues**: [Reportar Bugs](https://github.com/durregou/Inmotrack/issues)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última actualización**: Octubre 2025  
**Versión**: 2.0.0

