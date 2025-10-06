# Sistema de Microservicios de Arrendamiento

Este proyecto implementa un sistema completo de gestión de arrendamientos usando arquitectura de microservicios con Spring Boot.

---

## 📘 **DOCUMENTACIÓN - Elige Tu Ruta**

### ⚡ Quiero empezar YA (5 minutos)
👉 **[../INICIO_RAPIDO.md](../INICIO_RAPIDO.md)** - Compilar, desplegar y probar

### 📚 Quiero la documentación completa
👉 **[../docs/MICROSERVICIOS.md](../docs/MICROSERVICIOS.md)** ⭐ **RECOMENDADO**

**Este documento único contiene TODO:**
- ✅ Arquitectura con diagramas
- ✅ Guía de despliegue completa
- ✅ Los 6 microservicios principales explicados
- ✅ Todos los 41+ endpoints documentados
- ✅ Comandos y ejemplos
- ✅ Solución de problemas
- ✅ Configuración avanzada

**💡 Un solo archivo. Toda la información. Fácil de buscar con Ctrl+F.**

### 📖 Documentación por Microservicio
👉 **[../docs/microservices/](../docs/microservices/)** - Documentación individual de cada servicio

### 🧪 Colecciones de Postman
👉 **[postman-collections/](./postman-collections/)** - 52 endpoints listos para probar

---

## 📊 Métricas del Sistema

- **Cobertura**: 100% de requerimientos funcionales (RF01-RF14)
- **Microservicios**: 9 servicios funcionales
- **Endpoints REST**: 57+ APIs documentadas
- **Estado**: ✅ Sistema completo y funcional

## 🏗️ Arquitectura

El sistema está compuesto por 9 microservicios independientes:

1. **Administración Service** (Puerto 8081) - Autenticación de administradores
2. **Propietarios Service** (Puerto 8082) - Gestión de propietarios de inmuebles
3. **Inmuebles Service** (Puerto 8083) - Gestión del catálogo de inmuebles
4. **Contratos Service** (Puerto 8084) - Gestión de contratos de arrendamiento
5. **Pagos Service** (Puerto 8085) - Gestión de pagos y facturación
6. **Usuarios Service** (Puerto 8086) - Autenticación y gestión de usuarios (RF01, RF02, RF14)
7. **Notificaciones Service** (Puerto 8087) - Envío de notificaciones por email, SMS y WhatsApp (RF12)
8. **Mantenimiento Service** (Puerto 8088) - Gestión de solicitudes de mantenimiento (RF10, RF11)
9. **Reportes Service** (Puerto 8089) - Generación de reportes e inteligencia de negocio (RF09)

## 🚀 APIs Implementadas

### Microservicio de Administración
- `POST /api/admin/login` - Autenticación de administradores
- `GET /api/admin/{id}` - Consulta datos del administrador

### Microservicio de Propietarios
- `POST /api/propietarios` - Registrar propietario
- `GET /api/propietarios/{id}` - Obtener información del propietario

### Microservicio de Inmuebles
- `POST /api/inmuebles` - Registrar inmueble
- `GET /api/inmuebles` - Listar inmuebles (con filtros)
- `GET /api/inmuebles/{id}` - Obtener inmueble específico
- `PUT /api/inmuebles/{id}/disponibilidad` - Actualizar disponibilidad

### Microservicio de Contratos
- `POST /api/contratos` - Crear contrato
- `GET /api/contratos/{id}` - Consultar contrato
- `GET /api/contratos` - Listar contratos (con filtros)
- `PUT /api/contratos/{id}/finalizar` - Finalizar contrato

### Microservicio de Pagos
- `POST /api/pagos` - Registrar pago
- `GET /api/pagos` - Listar pagos (con filtros)
- `GET /api/pagos?contrato={id}` - Historial de pagos por contrato
- `PUT /api/pagos/{id}/estado` - Actualizar estado del pago

### Microservicio de Usuarios
- `POST /api/usuarios/registro` - Registrar nuevo usuario (administrador, propietario, arrendatario)
- `POST /api/usuarios/login` - Iniciar sesión con JWT
- `POST /api/usuarios/recuperar-password` - Solicitar recuperación de contraseña
- `POST /api/usuarios/restablecer-password` - Restablecer contraseña con token
- `GET /api/usuarios/{id}` - Obtener información del usuario
- `GET /api/usuarios` - Listar usuarios (con filtros por tipo)
- `PUT /api/usuarios/{id}/desactivar` - Desactivar usuario
- `PUT /api/usuarios/{id}/activar` - Activar usuario

### Microservicio de Notificaciones
- `POST /api/notificaciones` - Crear notificación (email, SMS, WhatsApp)
- `GET /api/notificaciones` - Listar notificaciones (con filtros)
- `GET /api/notificaciones/{id}` - Obtener notificación específica
- `POST /api/notificaciones/enviar-pago-pendiente` - Enviar alerta de pago pendiente

### Microservicio de Mantenimiento
- `POST /api/mantenimiento` - Crear solicitud de mantenimiento
- `GET /api/mantenimiento` - Listar solicitudes (con filtros)
- `GET /api/mantenimiento/{id}` - Obtener solicitud específica
- `PUT /api/mantenimiento/{id}` - Actualizar solicitud
- `PUT /api/mantenimiento/{id}/aprobar` - Aprobar solicitud
- `PUT /api/mantenimiento/{id}/rechazar` - Rechazar solicitud
- `PUT /api/mantenimiento/{id}/iniciar` - Iniciar mantenimiento
- `PUT /api/mantenimiento/{id}/completar` - Completar mantenimiento
- `DELETE /api/mantenimiento/{id}` - Eliminar solicitud

### Microservicio de Reportes
- `GET /api/reportes/rentabilidad` - Generar reporte de rentabilidad
- `GET /api/reportes/ocupacion` - Generar reporte de ocupación
- `GET /api/reportes/flujo-financiero` - Generar reporte de flujo financiero
- `GET /api/reportes/rentabilidad/excel` - Exportar reporte de rentabilidad a Excel
- `GET /api/reportes/ocupacion/excel` - Exportar reporte de ocupación a Excel
- `GET /api/reportes/flujo-financiero/excel` - Exportar reporte de flujo a Excel

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Maven**
- **JWT para autenticación**

## 📦 Despliegue con Docker

### Prerrequisitos

- Docker
- Docker Compose
- Maven (para compilar los proyectos)

### Pasos para desplegar

1. **Clonar el repositorio y navegar al directorio de microservicios:**
   ```bash
   cd microservicios
   ```

2. **Compilar todos los microservicios:**
   ```bash
   ./build-all.sh
   ```

3. **Levantar todos los servicios:**
   ```bash
   docker-compose up -d
   ```

4. **Verificar que los servicios están ejecutándose:**
   ```bash
   docker-compose ps
   ```

## 🔧 Comandos Útiles

### Compilar un microservicio específico
```bash
cd administracion-service
mvn clean package -DskipTests
```

### Ver logs de un servicio específico
```bash
docker-compose logs -f administracion-service
```

### Reiniciar un servicio
```bash
docker-compose restart propietarios-service
```

### Parar todos los servicios
```bash
docker-compose down
```

### Parar y eliminar volúmenes (⚠️ Esto borrará la base de datos)
```bash
docker-compose down -v
```

## 🔍 URLs de los Servicios

Una vez desplegado, los servicios estarán disponibles en:

- **Base de datos PostgreSQL**: `localhost:5432`
- **Administración Service**: `http://localhost:8081`
- **Propietarios Service**: `http://localhost:8082`
- **Inmuebles Service**: `http://localhost:8083`
- **Contratos Service**: `http://localhost:8084`
- **Pagos Service**: `http://localhost:8085`
- **Usuarios Service**: `http://localhost:8086`
- **Notificaciones Service**: `http://localhost:8087`
- **Mantenimiento Service**: `http://localhost:8088`
- **Reportes Service**: `http://localhost:8089`

## 📊 Base de Datos

El sistema utiliza una base de datos PostgreSQL compartida con las siguientes tablas principales:

- `administradores` - Datos de administradores del sistema
- `propietarios` - Información de propietarios de inmuebles
- `inmuebles` - Catálogo de inmuebles disponibles
- `contratos` - Contratos de arrendamiento activos e inactivos
- `pagos` - Registro de pagos y transacciones
- `usuarios` - Usuarios del sistema (administradores, propietarios, arrendatarios)
- `notificaciones` - Registro de notificaciones enviadas
- `solicitudes_mantenimiento` - Solicitudes de mantenimiento de inmuebles

## 🧪 Pruebas de las APIs

### Ejemplo: Registrar un propietario
```bash
curl -X POST http://localhost:8082/api/propietarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "correo": "juan.perez@email.com",
    "contrasena": "password123",
    "telefono": "3001234567",
    "cedula": "12345678"
  }'
```

### Ejemplo: Listar inmuebles disponibles
```bash
curl -X GET http://localhost:8083/api/inmuebles
```

### Ejemplo: Login de administrador
```bash
curl -X POST http://localhost:8081/api/admin/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "admin@sistema.com",
    "contrasena": "admin123"
  }'
```

### Ejemplo: Registrar usuario
```bash
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "usuario@example.com",
    "contrasena": "password123",
    "nombre": "Juan",
    "apellido": "Pérez",
    "telefono": "3001234567",
    "cedula": "1234567890",
    "tipoUsuario": "ARRENDATARIO"
  }'
```

### Ejemplo: Crear solicitud de mantenimiento
```bash
curl -X POST http://localhost:8088/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "solicitanteId": 1,
    "titulo": "Fuga de agua en baño",
    "descripcion": "Se detectó una fuga en la tubería del baño principal",
    "tipo": "CORRECTIVO",
    "prioridad": "ALTA"
  }'
```

### Ejemplo: Generar reporte de rentabilidad
```bash
curl -X GET "http://localhost:8089/api/reportes/rentabilidad?fechaInicio=2024-01-01&fechaFin=2024-12-31"
```

### Ejemplo: Exportar reporte a Excel
```bash
curl -X GET "http://localhost:8089/api/reportes/rentabilidad/excel" --output reporte.xlsx
```

## 🔒 Seguridad

- El microservicio de administración implementa autenticación JWT
- Las contraseñas se almacenan encriptadas usando BCrypt
- Validación de datos de entrada en todos los endpoints
- Configuración CORS para permitir requests desde frontend

## 📈 Escalabilidad

Cada microservicio puede escalarse independientemente:

```bash
docker-compose up --scale propietarios-service=3 --scale inmuebles-service=2
```

## 🐛 Solución de Problemas

### Error de conexión a base de datos
1. Verificar que PostgreSQL esté ejecutándose: `docker-compose ps`
2. Revisar logs: `docker-compose logs postgres-db`

### Puerto ocupado
Si algún puerto está ocupado, modificar el `docker-compose.yml`:
```yaml
ports:
  - "8091:8080"  # Cambiar puerto externo
```

### Memoria insuficiente
Aumentar memoria disponible para Docker en la configuración del Docker Desktop.

## 👥 Contribuciones

1. Fork el proyecto
2. Crear branch para feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo `LICENSE.md` para detalles.

---

## 📚 Documentación Adicional

### Documentación Principal del Proyecto
- **[../README.md](../README.md)** - Guía principal del proyecto
- **[../DOCUMENTACION.md](../DOCUMENTACION.md)** - Documentación técnica completa
- **[../docs/README.md](../docs/README.md)** - Índice de documentación
- **[../docs/ARCHITECTURE.md](../docs/ARCHITECTURE.md)** - Arquitectura del sistema
- **[../docs/API.md](../docs/API.md)** - Documentación de 52 endpoints
- **[../docs/DATABASE.md](../docs/DATABASE.md)** - Esquema de base de datos

### Scripts Útiles
- **[../scripts/README.md](../scripts/README.md)** - Guía de scripts de automatización

**Recomendación**: Para uso diario, consulta **[../docs/MICROSERVICIOS.md](../docs/MICROSERVICIOS.md)** que contiene toda la información unificada
