# Sistema de Microservicios de Arrendamiento

Este proyecto implementa un sistema completo de gestión de arrendamientos usando arquitectura de microservicios con Spring Boot.

## 🏗️ Arquitectura

El sistema está compuesto por 5 microservicios independientes:

1. **Administración Service** (Puerto 8081) - Autenticación de administradores
2. **Propietarios Service** (Puerto 8082) - Gestión de propietarios de inmuebles
3. **Inmuebles Service** (Puerto 8083) - Gestión del catálogo de inmuebles
4. **Contratos Service** (Puerto 8084) - Gestión de contratos de arrendamiento
5. **Pagos Service** (Puerto 8085) - Gestión de pagos y facturación

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

## 📊 Base de Datos

El sistema utiliza una base de datos PostgreSQL compartida con las siguientes tablas principales:

- `administradores` - Datos de administradores del sistema
- `propietarios` - Información de propietarios de inmuebles
- `inmuebles` - Catálogo de inmuebles disponibles
- `contratos` - Contratos de arrendamiento activos e inactivos
- `pagos` - Registro de pagos y transacciones

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
