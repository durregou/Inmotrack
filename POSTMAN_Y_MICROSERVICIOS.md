# 📬 Colecciones Postman y Documentación de Microservicios

**Última actualización**: Octubre 2025  
**GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

## 🎉 **¡NUEVO!** Colecciones de Postman Disponibles

Ya puedes descargar y usar las colecciones completas de Postman para probar todos los endpoints del sistema.

---

## 📦 Archivos Disponibles

### 1. **Colección de Postman** (52 Endpoints)

**Ubicación**: `microservicios/postman-collections/Inmotrack-Complete.postman_collection.json`

**Contenido**:
- ✅ 52 endpoints completos con ejemplos
- ✅ Organizados por microservicio
- ✅ Request bodies pre-configurados
- ✅ Variables dinámicas para IDs

**Microservicios incluidos**:
1. **Usuarios Service** (:8086) - 6 endpoints
2. **Inmuebles Service** (:8083) - 7 endpoints
3. **Contratos Service** (:8084) - 8 endpoints
4. **Pagos Service** (:8085) - 6 endpoints
5. **Mantenimiento Service** (:8087) - 8 endpoints
6. **Notificaciones Service** (:8088) - 4 endpoints

---

### 2. **Environment de Postman**

**Ubicación**: `microservicios/postman-collections/Inmotrack-Local.postman_environment.json`

**Variables incluidas**:
- ✅ URLs y puertos de todos los servicios
- ✅ Credenciales de prueba (admin, propietario, arrendatario)
- ✅ Variables para IDs dinámicos
- ✅ Configuración lista para usar

---

## 🚀 Guía de Instalación Rápida

### Paso 1: Descargar Archivos

**Opción A - Desde el repositorio local**:
```bash
cd ~/Documents/arrendamientoProyecto\ 2/microservicios/postman-collections/
ls
# Verás:
# - Inmotrack-Complete.postman_collection.json
# - Inmotrack-Local.postman_environment.json
# - README.md
```

**Opción B - Desde GitHub**:
1. Ve a [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
2. Navega a `microservicios/postman-collections/`
3. Descarga los archivos `.json`

---

### Paso 2: Importar en Postman

#### Importar Colección

1. Abre **Postman**
2. Click en **"Import"** (esquina superior izquierda)
3. Click en **"Upload Files"**
4. Selecciona `Inmotrack-Complete.postman_collection.json`
5. Click **"Import"**

#### Importar Environment

1. Click en **"Import"** nuevamente
2. Selecciona `Inmotrack-Local.postman_environment.json`
3. Click **"Import"**

---

### Paso 3: Configurar Environment

1. En la esquina superior derecha, click en el dropdown de environments
2. Selecciona **"Inmotrack - Local"**
3. (Opcional) Click en el ícono del ojo 👁️ para ver las variables
4. ¡Listo para usar!

---

## 📋 Estructura de la Colección

```
Inmotrack - Sistema de Arrendamientos/
│
├── 1. Usuarios Service (:8086)/
│   ├── Login                         POST /api/usuarios/login
│   ├── Registro Usuario             POST /api/usuarios/registro
│   ├── Obtener Usuario por ID       GET  /api/usuarios/{id}
│   ├── Listar Usuarios por Tipo     GET  /api/usuarios?tipo=ARRENDATARIO
│   ├── Activar Usuario              PUT  /api/usuarios/{id}/activar
│   └── Desactivar Usuario           PUT  /api/usuarios/{id}/desactivar
│
├── 2. Inmuebles Service (:8083)/
│   ├── Crear Inmueble               POST /api/inmuebles
│   ├── Listar Todos los Inmuebles   GET  /api/inmuebles
│   ├── Obtener Inmueble por ID      GET  /api/inmuebles/{id}
│   ├── Filtrar por Propietario      GET  /api/inmuebles?propietarioId=1
│   ├── Actualizar Inmueble Completo PUT  /api/inmuebles/{id}
│   ├── Cambiar Disponibilidad       PUT  /api/inmuebles/{id}/disponibilidad
│   └── Eliminar Inmueble            DELETE /api/inmuebles/{id}
│
├── 3. Contratos Service (:8084)/
│   ├── Crear Contrato               POST /api/contratos
│   ├── Listar Todos los Contratos   GET  /api/contratos
│   ├── Obtener Contrato por ID      GET  /api/contratos/{id}
│   ├── Filtrar por Propietario      GET  /api/contratos?propietarioId=1
│   ├── Filtrar por Arrendatario     GET  /api/contratos?arrendatarioId=7
│   ├── Actualizar Contrato          PUT  /api/contratos/{id}
│   ├── Finalizar Contrato ✨        PUT  /api/contratos/{id}/finalizar
│   └── Eliminar Contrato            DELETE /api/contratos/{id}
│
├── 4. Pagos Service (:8085)/
│   ├── Registrar Pago               POST /api/pagos
│   ├── Listar Todos los Pagos       GET  /api/pagos
│   ├── Filtrar por Contrato         GET  /api/pagos?contrato=1
│   ├── Filtrar por Arrendatario     GET  /api/pagos?arrendatario=7
│   ├── Cambiar Estado - PAGADO ✨   PUT  /api/pagos/{id}/estado?estado=PAGADO
│   └── Cambiar Estado - VENCIDO ✨  PUT  /api/pagos/{id}/estado?estado=VENCIDO
│
├── 5. Mantenimiento Service (:8087)/
│   ├── Crear Solicitud              POST /api/mantenimiento
│   ├── Listar Todas                 GET  /api/mantenimiento
│   ├── Filtrar por Solicitante      GET  /api/mantenimiento?solicitante=7
│   ├── Actualizar Solicitud         PUT  /api/mantenimiento/{id}
│   ├── Aprobar Solicitud ✨         PUT  /api/mantenimiento/{id}/aprobar
│   ├── Iniciar Trabajo ✨           PUT  /api/mantenimiento/{id}/iniciar
│   ├── Completar Trabajo ✨         PUT  /api/mantenimiento/{id}/completar
│   └── Rechazar Solicitud ✨        PUT  /api/mantenimiento/{id}/rechazar
│
└── 6. Notificaciones Service (:8088)/
    ├── Crear Notificación           POST /api/notificaciones
    ├── Listar Todas                 GET  /api/notificaciones
    ├── Filtrar por Destinatario     GET  /api/notificaciones?destinatario=email
    └── Obtener por ID               GET  /api/notificaciones/{id}
```

**Total**: **52 endpoints** listos para usar

---

## 🔧 Variables de Entorno Configuradas

### Conexión

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `base_url` | `http://localhost` | URL base |
| `usuarios_port` | `8086` | Puerto usuarios |
| `inmuebles_port` | `8083` | Puerto inmuebles |
| `contratos_port` | `8084` | Puerto contratos |
| `pagos_port` | `8085` | Puerto pagos |
| `mantenimiento_port` | `8087` | Puerto mantenimiento |
| `notificaciones_port` | `8088` | Puerto notificaciones |

### Credenciales

| Variable | Valor | Rol |
|----------|-------|-----|
| `admin_email` | `admin@sistema.com` | ADMINISTRADOR |
| `admin_password` | `admin123` | - |
| `propietario_email` | `propietario@test.com` | PROPIETARIO |
| `propietario_password` | `prop123` | - |
| `arrendatario_email` | `inquilino@test.com` | ARRENDATARIO |
| `arrendatario_password` | `inqui123` | - |

### IDs Dinámicos

| Variable | Uso |
|----------|-----|
| `user_id` | Para endpoints de usuarios |
| `inmueble_id` | Para endpoints de inmuebles |
| `contrato_id` | Para endpoints de contratos |
| `pago_id` | Para endpoints de pagos |
| `mantenimiento_id` | Para mantenimiento |
| `notificacion_id` | Para notificaciones |

**Nota**: Actualiza estos IDs según tus datos de prueba.

---

## 📚 Documentación de Microservicios

### Documentación Completa por Servicio

**Ubicación**: `docs/microservices/`

#### 1. **Usuarios Service** ✅ Completo

**Archivo**: [docs/microservices/USUARIOS-SERVICE.md](docs/microservices/USUARIOS-SERVICE.md)

**Incluye**:
- Modelo de datos (tabla `usuarios`)
- 6 endpoints con ejemplos completos
- Lógica de BCrypt para contraseñas
- Validaciones implementadas
- Casos de uso
- Usuarios de prueba
- Estructura del proyecto

#### 2. **Otros Microservicios** (En desarrollo)

Los siguientes servicios tendrán documentación similar:
- Inmuebles Service
- Contratos Service
- Pagos Service
- Mantenimiento Service
- Notificaciones Service

**Índice**: [docs/microservices/README.md](docs/microservices/README.md)

---

## 🎯 Flujo de Prueba Recomendado

### Prueba Completa del Sistema

```
1. LOGIN
   POST /api/usuarios/login
   → Autenticar como admin@sistema.com
   → Verificar respuesta con datos del usuario

2. CREAR INMUEBLE
   POST /api/inmuebles
   → Crear apartamento de prueba
   → Copiar el ID del response a {{inmueble_id}}

3. LISTAR ARRENDATARIOS
   GET /api/usuarios?tipo=ARRENDATARIO
   → Obtener lista de arrendatarios disponibles
   → Anotar ID de alguno (ej: 7)

4. CREAR CONTRATO
   POST /api/contratos
   → Usar {{inmueble_id}} y arrendatarioId=7
   → Copiar el ID del response a {{contrato_id}}

5. REGISTRAR PAGO
   POST /api/pagos
   → Usar {{contrato_id}} y arrendatarioId=7
   → Copiar el ID del response a {{pago_id}}

6. MARCAR PAGO COMO PAGADO
   PUT /api/pagos/{{pago_id}}/estado?estado=PAGADO
   → Verificar cambio de estado

7. SOLICITAR MANTENIMIENTO
   POST /api/mantenimiento
   → Usar {{inmueble_id}} y solicitanteId=7
   → Copiar el ID del response a {{mantenimiento_id}}

8. APROBAR MANTENIMIENTO
   PUT /api/mantenimiento/{{mantenimiento_id}}/aprobar
   → Cambiar estado a APROBADO

9. INICIAR TRABAJO
   PUT /api/mantenimiento/{{mantenimiento_id}}/iniciar
   → Cambiar estado a EN_PROCESO

10. COMPLETAR TRABAJO
    PUT /api/mantenimiento/{{mantenimiento_id}}/completar
    → Proporcionar costoReal y observaciones
    → Cambiar estado a COMPLETADO

11. ENVIAR NOTIFICACIÓN
    POST /api/notificaciones
    → Enviar notificación al arrendatario
    → Verificar creación exitosa

12. FINALIZAR CONTRATO
    PUT /api/contratos/{{contrato_id}}/finalizar
    → Marcar como FINALIZADO
    → Inmueble queda disponible nuevamente
```

---

## 💡 Tips de Uso

### 1. Actualizar IDs Dinámicamente
Después de crear un recurso, copia el `id` del response y actualízalo en las variables de entorno.

### 2. Usar Variables en Requests
Las variables se usan con `{{nombre_variable}}`:
```json
{
  "contratoId": {{contrato_id}},
  "arrendatarioId": 7
}
```

### 3. Verificar Servicios Antes de Usar
```bash
docker-compose ps
# Todos los servicios deben estar "Up"
```

### 4. Ver Logs de Errores
```bash
docker-compose logs [servicio]
# Ejemplo:
docker-compose logs usuarios-service
```

---

## ⚠️ Requisitos Previos

1. ✅ **Docker Desktop** corriendo
2. ✅ **Microservicios activos**:
   ```bash
   cd microservicios
   docker-compose up -d
   ```
3. ✅ **PostgreSQL** con datos iniciales:
   ```bash
   # Verificar conexión
   docker exec -it microservicios-postgres-db-1 psql -U arrendamiento_user -d arrendamiento_db -c "\dt"
   ```

---

## 📖 Documentación Adicional

### Documentación Técnica
- **[docs/API.md](docs/API.md)** - API completa con 52 endpoints
- **[docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)** - Arquitectura del sistema
- **[docs/microservices/](docs/microservices/)** - Documentación por microservicio

### Guías de Usuario
- **[README.md](README.md)** - Punto de entrada principal
- **[DOCUMENTACION.md](DOCUMENTACION.md)** - Documentación técnica completa
- **[INICIO_RAPIDO.md](INICIO_RAPIDO.md)** - Guía de 5 minutos

### Scripts
- **[scripts/README.md](scripts/README.md)** - Guía de scripts
- **[microservicios/postman-collections/README.md](microservicios/postman-collections/README.md)** - Guía detallada de Postman

---

## 🐛 Solución de Problemas

### "Could not get response"
**Causa**: Servicio no corriendo  
**Solución**:
```bash
docker-compose restart [servicio]
```

### "404 Not Found"
**Causa**: Puerto o ruta incorrectos  
**Solución**: Verificar environment variables

### "400 Bad Request"
**Causa**: Body inválido  
**Solución**: Verificar campos requeridos en [docs/API.md](docs/API.md)

---

## 🔗 Enlaces Útiles

- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Issues**: [Reportar Bugs](https://github.com/durregou/Inmotrack/issues)
- **Pull Requests**: [Contribuir](https://github.com/durregou/Inmotrack/pulls)
- **Autor**: [David Urrego](https://github.com/durregou)

---

## ✅ Checklist de Configuración

- [ ] Postman instalado
- [ ] Colección importada (`Inmotrack-Complete.postman_collection.json`)
- [ ] Environment importado (`Inmotrack-Local.postman_environment.json`)
- [ ] Environment seleccionado ("Inmotrack - Local")
- [ ] Docker corriendo
- [ ] Microservicios activos (`docker-compose ps`)
- [ ] PostgreSQL con datos (`docker exec ... psql`)
- [ ] Usuarios de prueba creados (`scripts/crear-usuario-prueba.sh`)
- [ ] ¡Listo para probar! 🚀

---

**Última actualización**: Octubre 2025  
**Versión**: 2.0.0  
**Total de Endpoints**: 52

