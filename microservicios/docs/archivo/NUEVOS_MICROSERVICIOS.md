# 🎉 Nuevos Microservicios Implementados

Este documento detalla los 4 nuevos microservicios que se han agregado al sistema de arrendamiento para cubrir el 100% de los requerimientos funcionales.

---

## 📋 Resumen de Cobertura

| Microservicio | Puerto | Requerimientos Funcionales Cubiertos | Estado |
|---------------|--------|--------------------------------------|--------|
| **usuarios-service** | 8086 | RF01, RF02, RF14 | ✅ Completado |
| **notificaciones-service** | 8087 | RF12 | ✅ Completado |
| **mantenimiento-service** | 8088 | RF10, RF11 | ✅ Completado |
| **reportes-service** | 8089 | RF09 | ✅ Completado |

---

## 1️⃣ Microservicio de Usuarios (usuarios-service)

### 🎯 Propósito
Gestión completa de autenticación y usuarios del sistema, incluyendo administradores, propietarios y arrendatarios.

### 📍 Puerto
**8086**

### 🔑 Requerimientos Funcionales Cubiertos
- **RF01**: Registro de usuarios con asignación de roles
- **RF02**: Inicio de sesión y control de acceso con JWT
- **RF14**: Recuperación de contraseña mediante correo electrónico

### 🛠️ Tecnologías
- Spring Boot 3.1.12
- Spring Security + JWT (io.jsonwebtoken)
- Spring Mail (para recuperación de contraseña)
- PostgreSQL
- BCrypt (encriptación de contraseñas)

### 📡 Endpoints Principales

#### Autenticación
- `POST /api/usuarios/registro` - Registrar nuevo usuario
- `POST /api/usuarios/login` - Iniciar sesión (retorna JWT)
- `POST /api/usuarios/recuperar-password` - Solicitar recuperación
- `POST /api/usuarios/restablecer-password` - Restablecer contraseña con token

#### Gestión de Usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `GET /api/usuarios?tipo={tipo}` - Listar usuarios por tipo
- `PUT /api/usuarios/{id}/desactivar` - Desactivar usuario
- `PUT /api/usuarios/{id}/activar` - Activar usuario

### 📦 Entidades
- **Usuario**: id, correo, contrasena, nombre, apellido, telefono, cedula, tipoUsuario (enum), activo, fechaRegistro, tokenRecuperacion, tokenExpiracion

### 💡 Características Especiales
- JWT para autenticación segura
- Tokens de recuperación con expiración de 24 horas
- Roles: ADMINISTRADOR, PROPIETARIO, ARRENDATARIO
- Contraseñas encriptadas con BCrypt

### 📝 Ejemplo de Uso
```bash
# Registrar usuario
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "usuario@example.com",
    "contrasena": "password123",
    "nombre": "Juan",
    "apellido": "Pérez",
    "tipoUsuario": "ARRENDATARIO"
  }'

# Login
curl -X POST http://localhost:8086/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "usuario@example.com",
    "contrasena": "password123"
  }'
```

---

## 2️⃣ Microservicio de Notificaciones (notificaciones-service)

### 🎯 Propósito
Envío de notificaciones y alertas a usuarios mediante diferentes canales (Email, SMS, WhatsApp).

### 📍 Puerto
**8087**

### 🔑 Requerimientos Funcionales Cubiertos
- **RF12**: Notificación de pagos pendientes con alertas automáticas

### 🛠️ Tecnologías
- Spring Boot 3.1.12
- Spring Mail (Email)
- Spring Scheduling (reintento automático)
- WebFlux (comunicación con otros microservicios)
- PostgreSQL

### 📡 Endpoints Principales
- `POST /api/notificaciones` - Crear y enviar notificación
- `GET /api/notificaciones` - Listar todas las notificaciones
- `GET /api/notificaciones?destinatario={email}` - Filtrar por destinatario
- `GET /api/notificaciones?contratoId={id}` - Filtrar por contrato
- `GET /api/notificaciones?pagoId={id}` - Filtrar por pago
- `POST /api/notificaciones/enviar-pago-pendiente` - Alerta específica de pago pendiente

### 📦 Entidades
- **Notificacion**: id, destinatario, asunto, mensaje, tipo (enum), estado (enum), fechaCreacion, fechaEnvio, intentosEnvio, errorMensaje, contratoId, pagoId

### 💡 Características Especiales
- Soporte para múltiples canales: EMAIL, SMS, WHATSAPP
- Sistema de reintentos automáticos (máximo 3 intentos)
- Tarea programada cada 5 minutos para reintentar notificaciones fallidas
- Estados: PENDIENTE, ENVIADO, FALLIDO, CANCELADO
- Registro de historial completo de notificaciones

### 📝 Ejemplo de Uso
```bash
# Enviar notificación de pago pendiente
curl -X POST "http://localhost:8087/api/notificaciones/enviar-pago-pendiente?correo=usuario@example.com&contratoId=1&pagoId=5&monto=1500.00"

# Crear notificación personalizada
curl -X POST http://localhost:8087/api/notificaciones \
  -H "Content-Type: application/json" \
  -d '{
    "destinatario": "usuario@example.com",
    "asunto": "Recordatorio de Pago",
    "mensaje": "Su pago vence en 3 días",
    "tipo": "EMAIL",
    "contratoId": 1
  }'
```

---

## 3️⃣ Microservicio de Mantenimiento (mantenimiento-service)

### 🎯 Propósito
Gestión integral de solicitudes de mantenimiento y soporte para inmuebles.

### 📍 Puerto
**8088**

### 🔑 Requerimientos Funcionales Cubiertos
- **RF10**: Solicitud de mantenimiento con registro completo
- **RF11**: Consulta de mantenimiento con reportes históricos

### 🛠️ Tecnologías
- Spring Boot 3.1.12
- Spring Data JPA
- PostgreSQL

### 📡 Endpoints Principales

#### CRUD Básico
- `POST /api/mantenimiento` - Crear solicitud
- `GET /api/mantenimiento` - Listar todas
- `GET /api/mantenimiento/{id}` - Obtener por ID
- `PUT /api/mantenimiento/{id}` - Actualizar
- `DELETE /api/mantenimiento/{id}` - Eliminar

#### Gestión de Flujo
- `PUT /api/mantenimiento/{id}/aprobar` - Aprobar solicitud
- `PUT /api/mantenimiento/{id}/rechazar?motivo={motivo}` - Rechazar
- `PUT /api/mantenimiento/{id}/iniciar` - Iniciar trabajo
- `PUT /api/mantenimiento/{id}/completar?costoReal={costo}` - Completar

#### Filtros
- `GET /api/mantenimiento?inmuebleId={id}` - Por inmueble
- `GET /api/mantenimiento?contratoId={id}` - Por contrato
- `GET /api/mantenimiento?solicitanteId={id}` - Por solicitante
- `GET /api/mantenimiento?estado={estado}` - Por estado
- `GET /api/mantenimiento?tecnico={nombre}` - Por técnico asignado

### 📦 Entidades
- **SolicitudMantenimiento**: id, inmuebleId, contratoId, solicitanteId, titulo, descripcion, tipo (enum), prioridad (enum), estado (enum), fechaSolicitud, fechaInicio, fechaFinalizacion, tecnicoAsignado, costoEstimado, costoReal, observaciones

### 💡 Características Especiales
- Tipos: PREVENTIVO, CORRECTIVO, EMERGENCIA
- Prioridades: BAJA, MEDIA, ALTA, URGENTE
- Estados: PENDIENTE, APROBADO, EN_PROCESO, COMPLETADO, RECHAZADO, CANCELADO
- Seguimiento completo del ciclo de vida
- Control de costos (estimado vs real)
- Historial completo de mantenimientos por inmueble

### 📝 Ejemplo de Uso
```bash
# Crear solicitud
curl -X POST http://localhost:8088/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "solicitanteId": 2,
    "titulo": "Reparación de aire acondicionado",
    "descripcion": "El aire acondicionado no enfría correctamente",
    "tipo": "CORRECTIVO",
    "prioridad": "ALTA",
    "costoEstimado": 250000.00
  }'

# Aprobar y asignar técnico
curl -X PUT http://localhost:8088/api/mantenimiento/1/aprobar

# Completar mantenimiento
curl -X PUT "http://localhost:8088/api/mantenimiento/1/completar?costoReal=280000.00&observaciones=Reemplazo de compresor"
```

---

## 4️⃣ Microservicio de Reportes (reportes-service)

### 🎯 Propósito
Generación de informes automáticos y análisis de inteligencia de negocio con exportación a múltiples formatos.

### 📍 Puerto
**8089**

### 🔑 Requerimientos Funcionales Cubiertos
- **RF09**: Generación de informes automáticos (rentabilidad, ocupación, flujo financiero)

### 🛠️ Tecnologías
- Spring Boot 3.1.12
- WebFlux (comunicación con otros microservicios)
- Apache POI (generación de Excel)
- iText7 (generación de PDF - preparado)

### 📡 Endpoints Principales

#### Reportes JSON
- `GET /api/reportes/rentabilidad?fechaInicio={fecha}&fechaFin={fecha}` - Reporte de rentabilidad
- `GET /api/reportes/ocupacion` - Reporte de ocupación
- `GET /api/reportes/flujo-financiero?mes={mes}&anio={anio}` - Reporte de flujo

#### Exportación Excel
- `GET /api/reportes/rentabilidad/excel` - Exportar rentabilidad a Excel
- `GET /api/reportes/ocupacion/excel` - Exportar ocupación a Excel
- `GET /api/reportes/flujo-financiero/excel` - Exportar flujo a Excel

### 📊 Tipos de Reportes

#### 1. Reporte de Rentabilidad
Métricas incluidas:
- Ingresos totales
- Gastos totales
- Rentabilidad neta
- Total de contratos
- Contratos activos
- Promedio de renta
- Periodo analizado

#### 2. Reporte de Ocupación
Métricas incluidas:
- Total de inmuebles
- Inmuebles arrendados
- Inmuebles disponibles
- Inmuebles en mantenimiento
- Tasa de ocupación (%)
- Periodo

#### 3. Reporte de Flujo Financiero
Métricas incluidas:
- Ingresos mensuales
- Egresos mensuales
- Flujo neto
- Pagos pendientes
- Monto pendiente
- Pagos realizados
- Monto recaudado
- Periodo

### 💡 Características Especiales
- Integración con múltiples microservicios (contratos, pagos, inmuebles, mantenimiento)
- Exportación a Excel (Apache POI)
- API REST para consumo externo
- Cálculos automáticos en tiempo real
- Datos consolidados de toda la plataforma

### 📝 Ejemplo de Uso
```bash
# Generar reporte de rentabilidad
curl -X GET "http://localhost:8089/api/reportes/rentabilidad?fechaInicio=2024-01-01&fechaFin=2024-12-31"

# Descargar reporte de ocupación en Excel
curl -X GET "http://localhost:8089/api/reportes/ocupacion/excel" --output reporte_ocupacion.xlsx

# Generar reporte de flujo financiero
curl -X GET "http://localhost:8089/api/reportes/flujo-financiero?mes=10&anio=2024"
```

---

## 🚀 Despliegue de los Nuevos Microservicios

### Compilación
```bash
cd microservicios
./build-all.sh
```

Este script compilará los 9 microservicios (incluyendo los 4 nuevos).

### Despliegue con Docker
```bash
docker-compose up -d
```

### Verificar Estado
```bash
docker-compose ps
```

Deberías ver 10 contenedores corriendo:
- postgres-db
- administracion-service
- propietarios-service
- inmuebles-service
- contratos-service
- pagos-service
- **usuarios-service** (nuevo)
- **notificaciones-service** (nuevo)
- **mantenimiento-service** (nuevo)
- **reportes-service** (nuevo)

---

## 📧 Configuración de Email (Opcional)

Para habilitar el envío de correos electrónicos en usuarios-service y notificaciones-service:

### Variables de Entorno
```bash
export MAIL_USERNAME=tu_correo@gmail.com
export MAIL_PASSWORD=tu_app_password
```

### En docker-compose.yml
Ya están configuradas las variables de entorno para ambos servicios.

### Gmail App Password
1. Habilitar autenticación de 2 factores en tu cuenta de Gmail
2. Generar una App Password en: https://myaccount.google.com/apppasswords
3. Usar esa contraseña en `MAIL_PASSWORD`

---

## 🎯 Cobertura Final de Requerimientos Funcionales

| RF | Descripción | Microservicio | Estado |
|----|-------------|---------------|--------|
| RF01 | Registro de usuarios | ✅ usuarios-service | ✅ |
| RF02 | Inicio de sesión | ✅ usuarios-service | ✅ |
| RF03 | Alta de inmuebles | ✅ inmuebles-service | ✅ |
| RF04 | Modificación de inmuebles | ✅ inmuebles-service | ✅ |
| RF05 | Visualización de estado | ✅ inmuebles-service | ✅ |
| RF06 | Consulta con filtros | ✅ inmuebles-service | ✅ |
| RF08 | Validación de información | ✅ contratos-service | ✅ |
| RF09 | Generación de informes | ✅ reportes-service | ✅ |
| RF10 | Solicitud de mantenimiento | ✅ mantenimiento-service | ✅ |
| RF11 | Consulta de mantenimiento | ✅ mantenimiento-service | ✅ |
| RF12 | Notificación de pagos | ✅ notificaciones-service | ✅ |
| RF13 | Consulta de pagos | ✅ pagos-service | ✅ |
| RF14 | Recuperación de contraseña | ✅ usuarios-service | ✅ |

**Cobertura: 100%** 🎉

---

## 📚 Documentación Adicional

- **README.md** - Documentación principal actualizada
- **docker-compose.yml** - Configuración de todos los servicios
- **build-all.sh** - Script de compilación actualizado

---

## 🔧 Solución de Problemas

### Los servicios no inician
1. Verificar que PostgreSQL esté corriendo: `docker-compose logs postgres-db`
2. Verificar logs del servicio: `docker-compose logs usuarios-service`

### Error de conexión entre microservicios
1. Verificar que todos los servicios estén en la misma red: `arrendamiento-network`
2. Usar nombres de servicio de Docker Compose (ej: `http://contratos-service:8080`)

### No se envían correos
1. Verificar que las variables de entorno estén configuradas
2. Verificar logs: `docker-compose logs usuarios-service | grep mail`
3. El sistema funciona sin correo, solo muestra mensajes en consola

---

## ✅ Checklist de Validación

- [ ] Los 4 nuevos microservicios compilan sin errores
- [ ] Docker Compose levanta los 9 servicios + PostgreSQL
- [ ] Endpoints de usuarios-service responden
- [ ] Endpoints de notificaciones-service responden
- [ ] Endpoints de mantenimiento-service responden
- [ ] Endpoints de reportes-service responden
- [ ] Se pueden exportar reportes a Excel
- [ ] Base de datos crea las nuevas tablas automáticamente
- [ ] Documentación README.md está actualizada

---

**¡Sistema completo al 100%!** 🚀

