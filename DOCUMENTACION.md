# 📖 Documentación Técnica Completa

## Sistema de Gestión de Arrendamientos

---

## 📑 Índice

1. [Arquitectura del Sistema](#1-arquitectura-del-sistema)
2. [Microservicios](#2-microservicios)
3. [Frontend - Interfaz Swing](#3-frontend---interfaz-swing)
4. [Base de Datos](#4-base-de-datos)
5. [API REST](#5-api-rest)
6. [Autenticación y Seguridad](#6-autenticación-y-seguridad)
7. [Flujos de Trabajo](#7-flujos-de-trabajo)
8. [Guía de Desarrollo](#8-guía-de-desarrollo)
9. [Testing](#9-testing)
10. [Deployment](#10-deployment)

---

## 1. Arquitectura del Sistema

### 1.1 Visión General

El sistema utiliza una arquitectura de **microservicios** desacoplada donde:
- El **frontend** (Java Swing) se comunica con los microservicios vía REST API
- Cada **microservicio** es independiente con su propia lógica y base de datos
- **PostgreSQL** centralizado para todos los microservicios (podría separarse)
- **Docker Compose** orquesta todos los servicios

### 1.2 Diagrama de Componentes

```
┌────────────────────────────────────────────────────────┐
│                  JAVA SWING CLIENT                     │
│  ┌──────────┐  ┌──────────┐  ┌───────────────────┐   │
│  │ frmlogin │  │ frmadmin │  │ frmpropietario/   │   │
│  │          │→ │          │  │ frmarrendatario   │   │
│  └──────────┘  └──────────┘  └───────────────────┘   │
│                      ↓                                 │
│                 ApiClient.java                         │
└───────────────────────┬────────────────────────────────┘
                        │ HTTP REST
                        ▼
┌────────────────────────────────────────────────────────┐
│              API GATEWAY (Opcional futuro)             │
└───────────────────────┬────────────────────────────────┘
                        │
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ usuarios-    │ │ inmuebles-   │ │ contratos-   │
│ service      │ │ service      │ │ service      │
│ :8086        │ │ :8083        │ │ :8084        │
└──────┬───────┘ └──────┬───────┘ └──────┬───────┘
       │                │                │
┌──────────────┐ ┌──────────────┐ ┌──────────────┐
│ pagos-       │ │ mantenimien- │ │ notificacio- │
│ service      │ │ to-service   │ │ nes-service  │
│ :8085        │ │ :8087        │ │ :8088        │
└──────┬───────┘ └──────┬───────┘ └──────┬───────┘
       │                │                │
       └────────────────┼────────────────┘
                        ▼
             ┌─────────────────────┐
             │   PostgreSQL DB     │
             │   arrendamiento_db  │
             │        :5432        │
             └─────────────────────┘
```

### 1.3 Puertos de los Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| Propietarios | 8082 | Gestión de propietarios |
| Inmuebles | 8083 | CRUD de inmuebles |
| Contratos | 8084 | Gestión de contratos |
| Pagos | 8085 | Registro y seguimiento de pagos |
| Usuarios | 8086 | Autenticación y gestión de usuarios |
| Mantenimiento | 8087 | Solicitudes de mantenimiento |
| Notificaciones | 8088 | Sistema de notificaciones |
| Reportes | 8089 | Generación de reportes |
| PostgreSQL | 5432 | Base de datos principal |

---

## 2. Microservicios

### 2.1 Usuarios Service (8086)

**Responsabilidad**: Autenticación, gestión de usuarios, roles.

**Endpoints Principales**:
```
POST   /api/usuarios/registro          # Crear nuevo usuario
POST   /api/usuarios/login             # Autenticar usuario
GET    /api/usuarios/{id}              # Obtener usuario por ID
GET    /api/usuarios?tipo=ADMIN        # Filtrar por tipo
PUT    /api/usuarios/{id}/activar      # Activar usuario
PUT    /api/usuarios/{id}/desactivar   # Desactivar usuario
```

**Entidades**:
- `Usuario`: id, nombre, apellido, correo, contraseña (hash), teléfono, dirección, cédula, tipoUsuario (ADMIN/PROPIETARIO/ARRENDATARIO), activo, fechaRegistro

**Lógica de Negocio**:
- Hash de contraseñas con BCrypt
- Validación de email único
- Generación de tokens JWT (en desarrollo)

---

### 2.2 Inmuebles Service (8083)

**Responsabilidad**: CRUD de inmuebles/propiedades.

**Endpoints Principales**:
```
POST   /api/inmuebles                           # Crear inmueble
GET    /api/inmuebles                           # Listar todos
GET    /api/inmuebles/{id}                      # Obtener por ID
GET    /api/inmuebles?propietarioId=1           # Filtrar por propietario
PUT    /api/inmuebles/{id}                      # Actualizar inmueble completo
PUT    /api/inmuebles/{id}/disponibilidad       # Cambiar disponibilidad
DELETE /api/inmuebles/{id}                      # Eliminar inmueble
```

**Entidades**:
- `Inmueble`: id, propietarioId, tipo, dirección, ciudad, departamento, área, habitaciones, baños, parqueaderos, precioArriendo, precioAdministración, descripción, amoblado, disponible, activo, fechaRegistro

**Validaciones**:
- Precio > 0
- Área > 0
- Habitaciones >= 0
- Baños >= 0

---

### 2.3 Contratos Service (8084)

**Responsabilidad**: Gestión del ciclo de vida de contratos.

**Endpoints Principales**:
```
POST   /api/contratos                    # Crear contrato
GET    /api/contratos/{id}               # Obtener por ID
GET    /api/contratos?propietarioId=1    # Filtrar por propietario
GET    /api/contratos?arrendatarioId=7   # Filtrar por arrendatario
GET    /api/contratos?inmuebleId=1       # Filtrar por inmueble
PUT    /api/contratos/{id}               # Actualizar contrato
PUT    /api/contratos/{id}/finalizar     # Finalizar contrato
DELETE /api/contratos/{id}               # Eliminar contrato
```

**Entidades**:
- `Contrato`: id, inmuebleId, propietarioId, arrendatarioId, fechaInicio, fechaFin, valorArriendo, valorAdministración, depósito, diaPago, estado (ACTIVO/FINALIZADO/CANCELADO), observaciones, fechaCreación, activo

**Lógica de Negocio**:
- Validar que el inmueble esté disponible
- Al crear contrato, marcar inmueble como NO disponible
- Al finalizar contrato, marcar inmueble como disponible
- `fechaFin` debe ser posterior a `fechaInicio`
- No permitir contratos superpuestos para el mismo inmueble

---

### 2.4 Pagos Service (8085)

**Responsabilidad**: Registro y seguimiento de pagos.

**Endpoints Principales**:
```
POST   /api/pagos                        # Registrar pago
GET    /api/pagos                        # Listar todos
GET    /api/pagos?contrato=1             # Filtrar por contrato
GET    /api/pagos?arrendatario=7         # Filtrar por arrendatario
PUT    /api/pagos/{id}/estado?estado=PAGADO  # Cambiar estado
```

**Entidades**:
- `Pago`: id, contratoId, arrendatarioId, fecha, valor, metodoPago (EFECTIVO/TRANSFERENCIA/TARJETA), estado (PENDIENTE/PAGADO/VENCIDO/PARCIAL), mesCorrespondiente, comprobante, mora, observaciones, fechaRegistro

**Cálculos**:
- Mora automática si está vencido
- Cambio de estado manual por admin

---

### 2.5 Mantenimiento Service (8087)

**Responsabilidad**: Gestión de solicitudes de mantenimiento.

**Endpoints Principales**:
```
POST   /api/mantenimiento                       # Crear solicitud
GET    /api/mantenimiento                       # Listar todas
GET    /api/mantenimiento?solicitante=7         # Filtrar por solicitante
GET    /api/mantenimiento?inmueble=1            # Filtrar por inmueble
PUT    /api/mantenimiento/{id}                  # Actualizar
PUT    /api/mantenimiento/{id}/aprobar          # Aprobar solicitud
PUT    /api/mantenimiento/{id}/iniciar          # Iniciar trabajo
PUT    /api/mantenimiento/{id}/completar        # Completar trabajo
PUT    /api/mantenimiento/{id}/rechazar         # Rechazar solicitud
```

**Entidades**:
- `SolicitudMantenimiento`: id, inmuebleId, solicitanteId, titulo, descripcion, tipo (PLOMERIA/ELECTRICO/PINTURA/LIMPIEZA/OTRO), prioridad (BAJA/MEDIA/ALTA/URGENTE), estado (PENDIENTE/APROBADO/EN_PROCESO/COMPLETADO/RECHAZADO), tecnico, costoEstimado, costoReal, fechaSolicitud, fechaCompletado, observaciones

**Flujo de Estados**:
```
PENDIENTE → APROBADO → EN_PROCESO → COMPLETADO
             ↓
         RECHAZADO
```

---

### 2.6 Notificaciones Service (8088)

**Responsabilidad**: Envío de notificaciones por email/SMS.

**Endpoints Principales**:
```
POST   /api/notificaciones                      # Crear notificación
GET    /api/notificaciones                      # Listar todas
GET    /api/notificaciones?destinatario=email   # Filtrar por destinatario
GET    /api/notificaciones/{id}                 # Obtener por ID
```

**Entidades**:
- `Notificacion`: id, destinatario, asunto, mensaje, tipo (EMAIL/SMS/WHATSAPP), estado (PENDIENTE/ENVIADO/FALLIDO), fechaCreación, fechaEnvío, intentosEnvío, errorMensaje, contratoId, pagoId

**Funcionalidades**:
- Procesamiento asíncrono
- Reintentos automáticos (máx 3)
- Logs de errores

---

### 2.7 Propietarios Service (8082)

**Responsabilidad**: Gestión de propietarios (legacy, podría fusionarse con Usuarios).

**Endpoints Principales**:
```
POST   /api/propietarios           # Crear propietario
GET    /api/propietarios/{id}      # Obtener por ID
```

---

### 2.8 Reportes Service (8089)

**Responsabilidad**: Generación de reportes y estadísticas.

**Endpoints** (en desarrollo):
```
GET    /api/reportes/rentabilidad
GET    /api/reportes/ocupacion
```

---

## 3. Frontend - Interfaz Swing

### 3.1 Estructura de Clases

```
Principal/
├── frmlogin.java              # Pantalla de inicio de sesión
├── frmadministrador.java      # Panel del administrador
├── frmpropietario.java        # Panel del propietario
├── frmarrendatario.java       # Panel del arrendatario
├── ApiClient.java             # Cliente HTTP para APIs
├── SesionUsuario.java         # Singleton para sesión activa
└── Validador.java             # Validaciones de formularios (en desarrollo)
```

### 3.2 ApiClient

**Métodos Principales**:
```java
public static JSONObject get(int puerto, String endpoint)
public static JSONObject post(int puerto, String endpoint, JSONObject datos)
public static JSONObject put(int puerto, String endpoint, JSONObject datos)
public static JSONObject delete(int puerto, String endpoint)
```

**Response Format**:
```json
{
  "statusCode": 200,
  "data": { ... }
}
```

### 3.3 SesionUsuario

**Singleton** que almacena:
- `usuarioID`: ID del usuario autenticado
- `nombre`: Nombre del usuario
- `apellido`: Apellido
- `correo`: Email
- `rol`: ADMINISTRADOR/PROPIETARIO/ARRENDATARIO
- `sesionActiva`: Boolean

### 3.4 Paneles Principales

#### frmadministrador
**Pestañas**:
1. DASHBOARD - Estadísticas generales
2. USUARIOS - CRUD de usuarios
3. CONTRATOS - Gestión de contratos
4. PAGOS - Control de pagos
5. INMUEBLES - CRUD de inmuebles
6. MANTENIMIENTO - Aprobación y seguimiento
7. MI PERFIL - Datos del admin

**Funcionalidades Clave**:
- `cargarEstadisticas()`: Fetch de estadísticas en tiempo real
- `enviarNotificacionMasiva()`: Envío a múltiples usuarios
- `cambiarEstadoPago()`: Gestión manual de estados
- `aprobarMantenimiento()`: Workflow de mantenimientos

#### frmpropietario
**Pestañas**:
1. MIS INMUEBLES - CRUD de propiedades
2. CONTRATOS - Crear y gestionar contratos
3. PAGOS RECIBIDOS - Visualización de ingresos
4. REPORTES - 5 tipos de reportes
5. MI PERFIL - Datos del propietario

**Funcionalidades Clave**:
- `registrarInmueble()`: Con validaciones completas
- `crearContrato()`: Selección de arrendatarios
- `finalizarContrato()`: Libera inmueble
- `generarReporteRentabilidad()`: Cálculo de ingresos vs gastos

#### frmarrendatario
**Pestañas**:
1. MI CONTRATO - Visualización de contrato activo
2. MIS PAGOS - Registro y seguimiento
3. MANTENIMIENTO - Solicitudes y estado
4. NOTIFICACIONES - Centro de mensajes
5. MI PERFIL - Datos del arrendatario

**Funcionalidades Clave**:
- `realizarPago()`: Registro de pagos con validaciones
- `solicitarMantenimiento()`: Formulario completo
- `mostrarDetalleNotificacion()`: Popup con mensaje completo
- Sistema de "leído" para notificaciones

---

## 4. Base de Datos

### 4.1 Esquema PostgreSQL

**Base de Datos**: `arrendamiento_db`  
**Usuario**: `arrendamiento_user`  
**Contraseña**: `arrendamiento_pass`

### 4.2 Tablas Principales

#### usuarios
```sql
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    correo VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    cedula VARCHAR(20),
    tipo_usuario VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT true,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### inmuebles
```sql
CREATE TABLE inmuebles (
    id SERIAL PRIMARY KEY,
    propietario_id INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    departamento VARCHAR(100),
    area DECIMAL(10,2),
    habitaciones INTEGER,
    banos INTEGER,
    parqueaderos INTEGER,
    precio_arriendo DECIMAL(12,2) NOT NULL,
    precio_administracion DECIMAL(12,2),
    descripcion TEXT,
    amoblado BOOLEAN DEFAULT false,
    disponible BOOLEAN DEFAULT true,
    activo BOOLEAN DEFAULT true,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### contratos
```sql
CREATE TABLE contratos (
    id SERIAL PRIMARY KEY,
    inmueble_id INTEGER NOT NULL,
    propietario_id INTEGER NOT NULL,
    arrendatario_id INTEGER NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    valor_arriendo DECIMAL(12,2) NOT NULL,
    valor_administracion DECIMAL(12,2),
    deposito DECIMAL(12,2),
    dia_pago INTEGER,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    observaciones TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT true
);
```

#### pagos
```sql
CREATE TABLE pagos (
    id SERIAL PRIMARY KEY,
    contrato_id INTEGER NOT NULL,
    arrendatario_id INTEGER NOT NULL,
    fecha DATE NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    metodo_pago VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    mes_correspondiente VARCHAR(20),
    comprobante VARCHAR(255),
    mora DECIMAL(12,2) DEFAULT 0,
    observaciones TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### solicitudes_mantenimiento
```sql
CREATE TABLE solicitudes_mantenimiento (
    id SERIAL PRIMARY KEY,
    inmueble_id INTEGER NOT NULL,
    solicitante_id INTEGER NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    tipo VARCHAR(50),
    prioridad VARCHAR(20) DEFAULT 'MEDIA',
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    tecnico VARCHAR(100),
    costo_estimado DECIMAL(12,2),
    costo_real DECIMAL(12,2),
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_completado TIMESTAMP,
    observaciones TEXT
);
```

#### notificaciones
```sql
CREATE TABLE notificaciones (
    id SERIAL PRIMARY KEY,
    destinatario VARCHAR(150) NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_envio TIMESTAMP,
    intentos_envio INTEGER DEFAULT 0,
    error_mensaje TEXT,
    contrato_id INTEGER,
    pago_id INTEGER
);
```

### 4.3 Relaciones

```
usuarios (id) ←──┐
                 │
inmuebles (propietario_id) ──→ contratos (inmueble_id, propietario_id)
                                    ↓
                              pagos (contrato_id)
                              solicitudes_mantenimiento (inmueble_id)
                              notificaciones (contrato_id)
```

---

## 5. API REST

### 5.1 Formato de Respuesta Estándar

**Success (200/201)**:
```json
{
  "id": 1,
  "nombre": "...",
  ...
}
```

**Error (400/404/500)**:
```json
{
  "error": "Descripción del error",
  "mensaje": "Detalles adicionales"
}
```

### 5.2 Headers Requeridos

```
Content-Type: application/json
Accept: application/json
```

### 5.3 Códigos de Estado HTTP

| Código | Significado |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado |
| 400 | Bad Request - Datos inválidos |
| 404 | Not Found - Recurso no existe |
| 405 | Method Not Allowed - Método HTTP incorrecto |
| 500 | Internal Server Error - Error del servidor |

---

## 6. Autenticación y Seguridad

### 6.1 Login Flow

```
1. Usuario ingresa correo y contraseña
2. POST /api/usuarios/login {correo, contrasena}
3. Backend valida con BCrypt
4. Si válido, retorna datos del usuario
5. Frontend guarda en SesionUsuario
6. Redirige al panel correspondiente según rol
```

### 6.2 Hashing de Contraseñas

- **Algoritmo**: BCrypt
- **Rounds**: 10
- **Ejemplo**:
  ```java
  String hash = BCrypt.hashpw("admin123", BCrypt.gensalt(10));
  boolean match = BCrypt.checkpw("admin123", hash);
  ```

### 6.3 JWT (En desarrollo)

Actualmente no se usa JWT. La sesión se maneja en el cliente (SesionUsuario).

**Mejora futura**:
- Generar JWT en login
- Incluir token en header: `Authorization: Bearer <token>`
- Validar token en cada request

---

## 7. Flujos de Trabajo

### 7.1 Crear Contrato de Arrendamiento

```
1. Propietario registra inmueble
2. Inmueble queda "disponible = true"
3. Propietario crea contrato:
   - Selecciona inmueble de su lista
   - Ingresa ID de arrendatario
   - Define fechas y valor
4. Backend valida:
   - Inmueble disponible?
   - Arrendatario existe?
   - Fechas coherentes?
5. Si OK:
   - Crea contrato con estado ACTIVO
   - Marca inmueble como "disponible = false"
6. Arrendatario puede ver su contrato y realizar pagos
```

### 7.2 Solicitar Mantenimiento

```
1. Arrendatario va a pestaña MANTENIMIENTO
2. Llena formulario:
   - Título, descripción
   - Tipo (plomería, eléctrico, etc.)
   - Prioridad
3. POST /api/mantenimiento
4. Estado inicial: PENDIENTE
5. Admin ve solicitud en su panel
6. Admin puede:
   - Aprobar → estado APROBADO
   - Iniciar → estado EN_PROCESO
   - Completar → estado COMPLETADO (ingresa costo real)
   - Rechazar → estado RECHAZADO
7. Arrendatario ve cambios en tiempo real
```

### 7.3 Realizar Pago

```
1. Arrendatario va a MIS PAGOS → Realizar Pago
2. Ingresa:
   - Valor
   - Método de pago
   - Mes correspondiente
3. POST /api/pagos {contratoId, arrendatarioId, ...}
4. Estado inicial: PENDIENTE
5. Admin puede cambiar estado a PAGADO
6. Propietario ve el pago en PAGOS RECIBIDOS
```

---

## 8. Guía de Desarrollo

### 8.1 Agregar un Nuevo Endpoint

**Ejemplo: Agregar endpoint para actualizar perfil de usuario**

1. **En el Controller**:
```java
@PutMapping("/{id}/perfil")
public ResponseEntity<?> actualizarPerfil(
    @PathVariable Long id, 
    @RequestBody PerfilRequest request) {
    
    Usuario usuario = usuarioService.actualizarPerfil(id, request);
    return ResponseEntity.ok(usuario);
}
```

2. **En el Service**:
```java
public Usuario actualizarPerfil(Long id, PerfilRequest request) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    usuario.setNombre(request.getNombre());
    usuario.setTelefono(request.getTelefono());
    
    return usuarioRepository.save(usuario);
}
```

3. **Crear DTO (PerfilRequest)**:
```java
public class PerfilRequest {
    private String nombre;
    private String telefono;
    // getters y setters
}
```

4. **En el Frontend (ApiClient)**:
```java
JSONObject datos = new JSONObject();
datos.put("nombre", "Nuevo Nombre");
datos.put("telefono", "123456789");

JSONObject response = ApiClient.put(
    ApiClient.USUARIOS_PORT, 
    "/api/usuarios/" + id + "/perfil", 
    datos
);
```

### 8.2 Agregar una Nueva Validación

En `Validador.java`:
```java
public static boolean esNumeroTelefono(String telefono, StringBuilder errores) {
    if (!telefono.matches("^[0-9]{10}$")) {
        errores.append("• Teléfono debe tener 10 dígitos\n");
        return false;
    }
    return true;
}
```

Usar en formulario:
```java
StringBuilder errores = new StringBuilder();
Validador.noVacio(txtNombre.getText(), "Nombre", errores);
Validador.esNumeroTelefono(txtTelefono.getText(), errores);

if (errores.length() > 0) {
    JOptionPane.showMessageDialog(this, errores.toString());
    return;
}
```

---

## 9. Testing

### 9.1 Testing Manual

**Scripts de Prueba**:
```bash
./scripts/crear-usuario-prueba.sh           # Crear usuarios
./scripts/inicializar-datos-microservicios.sh  # Datos de ejemplo
```

**Casos de Prueba**:
1. Login con credenciales válidas/inválidas
2. Crear inmueble con datos válidos/inválidos
3. Crear contrato (inmueble disponible vs ocupado)
4. Realizar pago
5. Cambiar estado de pago
6. Finalizar contrato (verificar que inmueble quede disponible)

### 9.2 Testing de API con cURL

```bash
# Login
curl -X POST http://localhost:8086/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{"correo":"admin@test.com","contrasena":"admin123"}'

# Listar inmuebles
curl http://localhost:8083/api/inmuebles

# Crear pago
curl -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d '{
    "contratoId": 1,
    "arrendatarioId": 7,
    "fecha": "2025-10-04",
    "valor": 1500000,
    "metodoPago": "TRANSFERENCIA",
    "mesCorrespondiente": "Octubre 2025"
  }'
```

---

## 10. Deployment

### 10.1 Desarrollo Local

**Requisitos**:
- Docker Desktop
- Java 17+
- Maven 3.8+

**Pasos**:
```bash
# 1. Iniciar microservicios
cd microservicios
docker-compose up -d

# 2. Compilar frontend
cd ..
javac -cp "sqlite-jdbc-3.7.2.jar:json-20231013.jar" -d build/classes src/Principal/*.java

# 3. Ejecutar
./scripts/run-app.sh
```

### 10.2 Producción (Futuro)

**Recomendaciones**:
1. **Dockerizar el frontend**: Crear imagen Docker para el cliente Swing
2. **API Gateway**: Agregar Spring Cloud Gateway
3. **Service Discovery**: Usar Eureka
4. **Load Balancer**: Nginx o AWS ELB
5. **Base de datos separadas**: Una BD por microservicio
6. **Logs centralizados**: ELK Stack
7. **Monitoring**: Prometheus + Grafana

---

## 📞 Contacto y Soporte

Para más información, consulta los otros documentos en la carpeta `docs/`:
- [CREDENCIALES_PRUEBA.md](./docs/CREDENCIALES_PRUEBA.md)
- [GUIA_RAPIDA_USUARIO.md](./docs/GUIA_RAPIDA_USUARIO.md)
- [INTEGRACION_VISUAL_MICROSERVICIOS.md](./docs/INTEGRACION_VISUAL_MICROSERVICIOS.md)

### 🐙 GitHub

- **Repositorio**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Issues**: [Reportar Bugs](https://github.com/durregou/Inmotrack/issues)
- **Contribuir**: [Pull Requests](https://github.com/durregou/Inmotrack/pulls)

---

**Última actualización**: Octubre 2025  
**Versión**: 1.0.0  
**Autor**: [David Urrego](https://github.com/durregou)  
**Repositorio**: [Inmotrack](https://github.com/durregou/Inmotrack)

