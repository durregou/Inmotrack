# 🚀 Guía Completa de Despliegue

Esta guía te ayudará a desplegar el sistema completo de microservicios paso a paso.

---

## 📋 Requisitos Previos

### Software Necesario

✅ **Java 17 o superior**
```bash
java -version
# Debe mostrar versión 17 o superior
```

✅ **Maven 3.6+**
```bash
mvn -version
# Debe estar instalado
```

✅ **Docker Desktop**
```bash
docker --version
docker-compose --version
```

### Verificar Instalación
```bash
# Verificar Java
which java

# Verificar Maven
which mvn

# Verificar Docker
docker ps
```

---

## 🔧 Paso 1: Preparación

### 1.1 Navegar al Directorio
```bash
cd /Users/davidurrego/Documents/arrendamientoProyecto\ 2/microservicios
```

### 1.2 Verificar Estructura
```bash
ls -la
# Deberías ver:
# - administracion-service/
# - propietarios-service/
# - inmuebles-service/
# - contratos-service/
# - pagos-service/
# - usuarios-service/           ⭐ NUEVO
# - notificaciones-service/     ⭐ NUEVO
# - mantenimiento-service/      ⭐ NUEVO
# - reportes-service/           ⭐ NUEVO
# - docker-compose.yml
# - build-all.sh
```

---

## 🔨 Paso 2: Compilación

### 2.1 Dar Permisos al Script (si es necesario)
```bash
chmod +x build-all.sh
chmod +x verificar-servicios.sh
```

### 2.2 Compilar Todos los Microservicios
```bash
./build-all.sh
```

**Esto compilará los 9 microservicios automáticamente.**

⏱️ **Tiempo estimado**: 3-5 minutos (primera vez puede tardar más descargando dependencias)

### 2.3 Verificar Compilación Exitosa
Deberías ver mensajes como:
```
✅ administracion-service compilado exitosamente
✅ propietarios-service compilado exitosamente
✅ inmuebles-service compilado exitosamente
✅ contratos-service compilado exitosamente
✅ pagos-service compilado exitosamente
✅ usuarios-service compilado exitosamente
✅ notificaciones-service compilado exitosamente
✅ mantenimiento-service compilado exitosamente
✅ reportes-service compilado exitosamente
🎉 Todos los microservicios compilados exitosamente!
```

### 2.4 En Caso de Error
Si algún servicio falla:
```bash
# Ver log de error
cat build_<nombre-servicio>.log

# Compilar manualmente
cd <nombre-servicio>
mvn clean package -DskipTests
cd ..
```

---

## 🐳 Paso 3: Levantar Servicios con Docker

### 3.1 Iniciar Docker Desktop
Asegúrate de que Docker Desktop esté corriendo.

### 3.2 Levantar Todos los Servicios
```bash
docker-compose up -d
```

El flag `-d` ejecuta los contenedores en segundo plano (detached mode).

⏱️ **Tiempo estimado**: 30-60 segundos

### 3.3 Ver el Proceso de Inicio
```bash
docker-compose logs -f
```

Presiona `Ctrl+C` para salir de los logs (los servicios seguirán corriendo).

### 3.4 Verificar que Todos Estén Corriendo
```bash
docker-compose ps
```

Deberías ver 10 contenedores en estado "Up":
```
postgres-db              Up
administracion-service   Up
propietarios-service     Up
inmuebles-service        Up
contratos-service        Up
pagos-service            Up
usuarios-service         Up      ⭐ NUEVO
notificaciones-service   Up      ⭐ NUEVO
mantenimiento-service    Up      ⭐ NUEVO
reportes-service         Up      ⭐ NUEVO
```

---

## ✅ Paso 4: Verificación

### 4.1 Ejecutar Script de Verificación
```bash
./verificar-servicios.sh
```

Este script verificará que todos los servicios respondan correctamente.

### 4.2 Pruebas Manuales

#### Verificar PostgreSQL
```bash
docker-compose logs postgres-db | grep "database system is ready"
```

#### Verificar Cada Servicio
```bash
# Usuarios
curl http://localhost:8086/api/usuarios

# Notificaciones
curl http://localhost:8087/api/notificaciones

# Mantenimiento
curl http://localhost:8088/api/mantenimiento

# Reportes
curl http://localhost:8089/api/reportes/ocupacion
```

Todos deberían retornar una respuesta (puede ser una lista vacía `[]` o un objeto JSON).

---

## 🧪 Paso 5: Pruebas Funcionales

### 5.1 Registrar un Usuario
```bash
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "test@example.com",
    "contrasena": "password123",
    "nombre": "Usuario",
    "apellido": "Prueba",
    "telefono": "3001234567",
    "cedula": "1234567890",
    "tipoUsuario": "ARRENDATARIO"
  }'
```

**Respuesta esperada**: `200 OK` con datos del usuario creado.

### 5.2 Hacer Login
```bash
curl -X POST http://localhost:8086/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "test@example.com",
    "contrasena": "password123"
  }'
```

**Respuesta esperada**: JWT token y datos del usuario.

### 5.3 Crear Solicitud de Mantenimiento
```bash
curl -X POST http://localhost:8088/api/mantenimiento \
  -H "Content-Type: application/json" \
  -d '{
    "inmuebleId": 1,
    "solicitanteId": 1,
    "titulo": "Prueba de mantenimiento",
    "descripcion": "Esta es una prueba del sistema",
    "tipo": "PREVENTIVO",
    "prioridad": "MEDIA"
  }'
```

**Respuesta esperada**: `201 Created` con datos de la solicitud.

### 5.4 Generar Reporte
```bash
curl http://localhost:8089/api/reportes/ocupacion
```

**Respuesta esperada**: JSON con datos del reporte.

### 5.5 Descargar Reporte Excel
```bash
curl http://localhost:8089/api/reportes/ocupacion/excel --output reporte.xlsx
```

**Resultado**: Archivo `reporte.xlsx` descargado.

---

## 📊 Paso 6: Monitoreo

### 6.1 Ver Logs en Tiempo Real
```bash
# Todos los servicios
docker-compose logs -f

# Un servicio específico
docker-compose logs -f usuarios-service

# Últimas 100 líneas
docker-compose logs --tail=100 usuarios-service
```

### 6.2 Ver Estado de Contenedores
```bash
docker-compose ps
```

### 6.3 Ver Recursos Utilizados
```bash
docker stats
```

### 6.4 Ver Redes
```bash
docker network ls
docker network inspect arrendamiento-network
```

---

## 🔄 Comandos de Gestión

### Reiniciar un Servicio
```bash
docker-compose restart usuarios-service
```

### Detener un Servicio
```bash
docker-compose stop usuarios-service
```

### Iniciar un Servicio Detenido
```bash
docker-compose start usuarios-service
```

### Reconstruir un Servicio
```bash
docker-compose up -d --build usuarios-service
```

### Detener Todos los Servicios
```bash
docker-compose down
```

### Detener y Eliminar Volúmenes (⚠️ CUIDADO: Borra la BD)
```bash
docker-compose down -v
```

---

## 🐛 Solución de Problemas

### Problema: Puerto Ocupado

**Error**: `Bind for 0.0.0.0:8086 failed: port is already allocated`

**Solución**:
```bash
# Ver qué está usando el puerto
lsof -i :8086

# Matar el proceso
kill -9 <PID>

# O cambiar el puerto en docker-compose.yml
ports:
  - "8186:8080"  # Cambiar 8086 a 8186
```

### Problema: Servicio No Inicia

**Síntomas**: Contenedor se detiene inmediatamente

**Solución**:
```bash
# Ver logs del error
docker-compose logs usuarios-service

# Verificar compilación
cd usuarios-service
mvn clean package
cd ..

# Reconstruir imagen
docker-compose build usuarios-service
docker-compose up -d usuarios-service
```

### Problema: No Conecta a PostgreSQL

**Síntomas**: Errores de conexión a base de datos

**Solución**:
```bash
# Verificar que PostgreSQL esté corriendo
docker-compose ps postgres-db

# Ver logs de PostgreSQL
docker-compose logs postgres-db

# Reiniciar PostgreSQL
docker-compose restart postgres-db

# Esperar unos segundos y reiniciar servicios
docker-compose restart
```

### Problema: Compilación Falla

**Error**: `BUILD FAILURE`

**Solución**:
```bash
# Limpiar repositorio Maven
rm -rf ~/.m2/repository

# Compilar con verbose
cd <servicio>
mvn clean package -X

# O compilar sin tests
mvn clean package -DskipTests
```

### Problema: Docker Out of Memory

**Síntomas**: Servicios muy lentos o fallan aleatoriamente

**Solución**:
1. Abrir Docker Desktop
2. Ir a Settings → Resources
3. Aumentar Memory a 4GB o más
4. Apply & Restart

---

## 📧 Configuración Opcional: Email

Para habilitar el envío de correos reales:

### 1. Obtener Credenciales de Gmail

1. Habilitar autenticación de 2 factores en Gmail
2. Ir a https://myaccount.google.com/apppasswords
3. Generar una "App Password"

### 2. Configurar Variables de Entorno

```bash
export MAIL_USERNAME=tu_correo@gmail.com
export MAIL_PASSWORD=tu_app_password
```

### 3. Reiniciar Servicios
```bash
docker-compose restart usuarios-service
docker-compose restart notificaciones-service
```

### 4. Probar
```bash
curl -X POST http://localhost:8086/api/usuarios/recuperar-password \
  -H "Content-Type: application/json" \
  -d '{"correo": "test@example.com"}'
```

---

## 🔐 Acceso a PostgreSQL

### Desde Terminal
```bash
docker-compose exec postgres-db psql -U arrendamiento_user -d arrendamiento_db
```

### Comandos Útiles PostgreSQL
```sql
-- Ver todas las tablas
\dt

-- Describir tabla
\d usuarios

-- Consultar datos
SELECT * FROM usuarios;

-- Salir
\q
```

### Desde Cliente Externo (DBeaver, pgAdmin, etc.)
- **Host**: localhost
- **Port**: 5432
- **Database**: arrendamiento_db
- **User**: arrendamiento_user
- **Password**: arrendamiento_pass

---

## 📚 Recursos Adicionales

### Documentación
- `README.md` - Documentación principal
- `NUEVOS_MICROSERVICIOS.md` - Detalle de nuevos servicios
- `REFERENCIA_RAPIDA.md` - Comandos rápidos
- `RESUMEN_FINAL.md` - Resumen visual

### Scripts
- `build-all.sh` - Compilar todos los servicios
- `verificar-servicios.sh` - Verificar estado

### URLs Importantes
```
Usuarios:        http://localhost:8086
Notificaciones:  http://localhost:8087
Mantenimiento:   http://localhost:8088
Reportes:        http://localhost:8089
```

---

## ✅ Checklist Final

Antes de dar por terminado el despliegue, verifica:

- [ ] Java 17+ instalado
- [ ] Maven instalado
- [ ] Docker Desktop corriendo
- [ ] Todos los servicios compilados (`./build-all.sh`)
- [ ] Docker Compose levantado (`docker-compose up -d`)
- [ ] 10 contenedores corriendo (`docker-compose ps`)
- [ ] Script de verificación exitoso (`./verificar-servicios.sh`)
- [ ] Puedo registrar un usuario
- [ ] Puedo hacer login
- [ ] Puedo crear solicitud de mantenimiento
- [ ] Puedo generar reportes
- [ ] Puedo descargar Excel

---

## 🎉 ¡Despliegue Exitoso!

Si todos los checks están ✅, tu sistema está completamente desplegado y funcional.

**Siguiente paso**: Comenzar a usar el sistema o desarrollar el frontend.

---

**¿Necesitas ayuda?** Revisa la sección de solución de problemas o consulta los logs:
```bash
docker-compose logs -f <servicio>
```

