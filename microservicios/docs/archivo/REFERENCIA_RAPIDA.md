# 📚 Referencia Rápida - Sistema de Microservicios de Arrendamiento

## 🚀 Inicio Rápido

### Compilar todos los microservicios
```bash
cd microservicios
./build-all.sh
```

### Levantar todos los servicios
```bash
docker-compose up -d
```

### Verificar estado
```bash
./verificar-servicios.sh
```

### Ver logs
```bash
docker-compose logs -f <nombre-servicio>
```

### Detener servicios
```bash
docker-compose down
```

---

## 📡 URLs de Servicios

| Servicio | Puerto | URL Base |
|----------|--------|----------|
| PostgreSQL | 5432 | localhost:5432 |
| Administración | 8081 | http://localhost:8081 |
| Propietarios | 8082 | http://localhost:8082 |
| Inmuebles | 8083 | http://localhost:8083 |
| Contratos | 8084 | http://localhost:8084 |
| Pagos | 8085 | http://localhost:8085 |
| **Usuarios** | **8086** | **http://localhost:8086** |
| **Notificaciones** | **8087** | **http://localhost:8087** |
| **Mantenimiento** | **8088** | **http://localhost:8088** |
| **Reportes** | **8089** | **http://localhost:8089** |

---

## 🔑 Endpoints Más Usados

### Usuarios Service (8086)
```bash
# Registrar usuario
POST /api/usuarios/registro

# Login
POST /api/usuarios/login

# Recuperar contraseña
POST /api/usuarios/recuperar-password

# Listar usuarios
GET /api/usuarios
```

### Notificaciones Service (8087)
```bash
# Enviar notificación
POST /api/notificaciones

# Alerta de pago pendiente
POST /api/notificaciones/enviar-pago-pendiente

# Listar notificaciones
GET /api/notificaciones
```

### Mantenimiento Service (8088)
```bash
# Crear solicitud
POST /api/mantenimiento

# Listar solicitudes
GET /api/mantenimiento

# Aprobar solicitud
PUT /api/mantenimiento/{id}/aprobar

# Completar mantenimiento
PUT /api/mantenimiento/{id}/completar
```

### Reportes Service (8089)
```bash
# Reporte de rentabilidad
GET /api/reportes/rentabilidad

# Reporte de ocupación
GET /api/reportes/ocupacion

# Exportar a Excel
GET /api/reportes/rentabilidad/excel
```

---

## 🔧 Comandos Útiles

### Docker Compose

```bash
# Ver estado de contenedores
docker-compose ps

# Ver logs en tiempo real
docker-compose logs -f

# Logs de un servicio específico
docker-compose logs -f usuarios-service

# Reiniciar un servicio
docker-compose restart usuarios-service

# Reconstruir y levantar
docker-compose up -d --build usuarios-service

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (⚠️ borra la BD)
docker-compose down -v
```

### Maven

```bash
# Compilar un microservicio
cd usuarios-service
mvn clean package -DskipTests

# Ejecutar localmente (sin Docker)
mvn spring-boot:run
```

### Base de Datos

```bash
# Conectar a PostgreSQL
docker-compose exec postgres-db psql -U arrendamiento_user -d arrendamiento_db

# Ver tablas
\dt

# Salir
\q
```

---

## 🐛 Solución de Problemas Comunes

### Error: Puerto ya en uso
```bash
# Ver qué proceso usa el puerto
lsof -i :8086

# Matar proceso
kill -9 <PID>
```

### Error: No se puede conectar a PostgreSQL
```bash
# Ver logs de PostgreSQL
docker-compose logs postgres-db

# Reiniciar PostgreSQL
docker-compose restart postgres-db
```

### Error: Microservicio no inicia
```bash
# Ver logs
docker-compose logs <servicio>

# Verificar compilación
cd <servicio>
mvn clean package

# Reconstruir imagen
docker-compose build <servicio>
docker-compose up -d <servicio>
```

### Error: No se envían correos
1. Configurar variables de entorno:
```bash
export MAIL_USERNAME=tu_correo@gmail.com
export MAIL_PASSWORD=tu_app_password
```
2. Reiniciar servicios

---

## 📊 Estructura de Proyecto

```
microservicios/
├── administracion-service/
├── propietarios-service/
├── inmuebles-service/
├── contratos-service/
├── pagos-service/
├── usuarios-service/          # ⭐ NUEVO
├── notificaciones-service/    # ⭐ NUEVO
├── mantenimiento-service/     # ⭐ NUEVO
├── reportes-service/          # ⭐ NUEVO
├── docker-compose.yml
├── build-all.sh
├── verificar-servicios.sh     # ⭐ NUEVO
├── README.md
├── NUEVOS_MICROSERVICIOS.md   # ⭐ NUEVO
└── REFERENCIA_RAPIDA.md       # ⭐ NUEVO (este archivo)
```

---

## 📝 Ejemplos de Prueba

### 1. Flujo Completo de Usuario

```bash
# 1. Registrar usuario
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "juan@example.com",
    "contrasena": "password123",
    "nombre": "Juan",
    "apellido": "Pérez",
    "tipoUsuario": "ARRENDATARIO"
  }'

# 2. Login
curl -X POST http://localhost:8086/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "juan@example.com",
    "contrasena": "password123"
  }'
```

### 2. Flujo de Mantenimiento

```bash
# 1. Crear solicitud
curl -X POST http://localhost:8088/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "solicitanteId": 1,
    "titulo": "Fuga de agua",
    "descripcion": "Hay una fuga en el baño",
    "tipo": "CORRECTIVO",
    "prioridad": "ALTA"
  }'

# 2. Aprobar (retorna ID, por ejemplo 1)
curl -X PUT http://localhost:8088/api/mantenimiento/1/aprobar

# 3. Iniciar
curl -X PUT http://localhost:8088/api/mantenimiento/1/iniciar

# 4. Completar
curl -X PUT "http://localhost:8088/api/mantenimiento/1/completar?costoReal=250000"
```

### 3. Flujo de Notificaciones

```bash
# Enviar alerta de pago pendiente
curl -X POST "http://localhost:8087/api/notificaciones/enviar-pago-pendiente?correo=usuario@example.com&contratoId=1&pagoId=5&monto=1500.00"

# Ver notificaciones enviadas
curl http://localhost:8087/api/notificaciones
```

### 4. Generar Reportes

```bash
# Reporte de ocupación
curl http://localhost:8089/api/reportes/ocupacion | jq

# Descargar reporte en Excel
curl http://localhost:8089/api/reportes/rentabilidad/excel --output reporte.xlsx

# Reporte de flujo financiero
curl "http://localhost:8089/api/reportes/flujo-financiero?mes=10&anio=2024" | jq
```

---

## 🎯 Checklist de Verificación

### Después de levantar los servicios:

- [ ] PostgreSQL está corriendo
- [ ] Los 9 microservicios están UP
- [ ] Puedo hacer login en usuarios-service
- [ ] Puedo crear una solicitud de mantenimiento
- [ ] Puedo enviar una notificación
- [ ] Puedo generar un reporte
- [ ] Puedo exportar un reporte a Excel

### Comandos de verificación:

```bash
# Ver todos los contenedores
docker-compose ps

# Probar cada endpoint
curl http://localhost:8086/api/usuarios
curl http://localhost:8087/api/notificaciones
curl http://localhost:8088/api/mantenimiento
curl http://localhost:8089/api/reportes/ocupacion
```

---

## 📚 Documentación Completa

- **README.md** - Documentación principal
- **NUEVOS_MICROSERVICIOS.md** - Detalle de los 4 nuevos microservicios
- **docker-compose.yml** - Configuración de servicios
- **build-all.sh** - Script de compilación
- **verificar-servicios.sh** - Script de verificación

---

## 🆘 Ayuda

Si tienes problemas:
1. Revisa los logs: `docker-compose logs <servicio>`
2. Verifica el estado: `./verificar-servicios.sh`
3. Revisa la documentación: `NUEVOS_MICROSERVICIOS.md`
4. Reconstruye: `docker-compose down && docker-compose up -d --build`

---

**¡Sistema completo y funcionando!** 🎉

