# 🏛️ Guía de Arquitectura Actualizada

**Última actualización**: Octubre 2025  
**Versión**: 2.0.0  
**GitHub**: [Inmotrack](https://github.com/durregou/Inmotrack)

Esta documentación describe la arquitectura completa del sistema con todas las implementaciones actuales.

---

## 📋 Índice

- [🎯 Visión General](#-visión-general)
- [🏗️ Arquitectura Completa](#️-arquitectura-completa)
- [🎨 Capa de Presentación](#-capa-de-presentación)
- [⚙️ Microservicios Backend](#️-microservicios-backend)
- [💾 Arquitectura de Datos](#-arquitectura-de-datos)
- [🔄 Patrones de Diseño](#-patrones-de-diseño)
- [🔗 Comunicación](#-comunicación)
- [🛡️ Seguridad](#️-seguridad)

---

## 🎯 Visión General

### Arquitectura Híbrida: Desktop + Microservicios

El sistema implementa una arquitectura **híbrida** que combina:
- **Frontend**: Java Swing (Desktop Application)
- **Backend**: Microservicios Spring Boot
- **Base de Datos**: PostgreSQL Centralizado
- **Contenedores**: Docker + Docker Compose

### Principios Arquitectónicos

1. ✅ **Separación de Responsabilidades**: UI, Lógica de Negocio, Datos
2. ✅ **Microservicios Independientes**: 8 servicios especializados
3. ✅ **REST API First**: Todas las operaciones vía HTTP
4. ✅ **Stateless**: No hay estado en el servidor (sesión en cliente)
5. ✅ **Containerizado**: Todo corre en Docker para portabilidad

---

## 🏗️ Arquitectura Completa

### Diagrama de Alto Nivel

```
┌─────────────────────────────────────────────────────────────┐
│                 CAPA DE PRESENTACIÓN                         │
│             Java Swing Desktop Application                    │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────────┐    │
│  │  frmlogin    │ │ frmadmin     │ │ frmpropietario/  │    │
│  │              │→│              │ │ frmarrendatario  │    │
│  └──────────────┘ └──────────────┘ └──────────────────┘    │
│         ↓                                                     │
│    ApiClient.java (HTTP Client)                              │
│    SesionUsuario.java (Session Singleton)                    │
└────────────────────────┬────────────────────────────────────┘
                         │ REST API (HTTP/JSON)
                         ▼
┌─────────────────────────────────────────────────────────────┐
│              CAPA DE MICROSERVICIOS (8 Servicios)            │
├────────────────┬────────────────┬───────────────────────────┤
│ Usuarios       │ Inmuebles      │ Contratos                 │
│ :8086          │ :8083          │ :8084                     │
│ • Login        │ • CRUD Completo│ • CRUD Completo           │
│ • Registro     │ • Editar       │ • Editar                  │
│ • Activar/     │ • Eliminar     │ • Eliminar                │
│   Desactivar   │ • Filtros      │ • Finalizar               │
├────────────────┼────────────────┼───────────────────────────┤
│ Pagos          │ Mantenimiento  │ Notificaciones            │
│ :8085          │ :8087          │ :8088                     │
│ • Registro     │ • Solicitudes  │ • Envío Masivo            │
│ • Cambiar      │ • Aprobar      │ • EMAIL/SMS               │
│   Estado       │ • Iniciar      │ • Historial               │
│ • Marcar       │ • Completar    │                           │
│   Pagado/      │ • Rechazar     │                           │
│   Vencido      │                │                           │
├────────────────┼────────────────┼───────────────────────────┤
│ Propietarios   │ Reportes       │                           │
│ :8082          │ :8089          │                           │
│ • Gestión      │ • Rentabilidad │                           │
│   Básica       │ • Ocupación    │                           │
│                │ • Mantenimiento│                           │
└────────────────┴────────────────┴───────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE DATOS                              │
│              PostgreSQL Database :5432                        │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐       │
│  │ usuarios │ │inmuebles │ │contratos │ │  pagos   │       │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘       │
│  ┌──────────┐ ┌──────────────────────┐ ┌──────────┐       │
│  │mantenim. │ │   notificaciones     │ │propietar.│       │
│  └──────────┘ └──────────────────────┘ └──────────┘       │
└─────────────────────────────────────────────────────────────┘
```

---

## 🎨 Capa de Presentación

### Java Swing Application

#### Estructura de Clases

```
src/Principal/
├── frmlogin.java                 # Login + Registro + Recuperar Contraseña
├── frmadministrador.java         # Panel Admin (7 tabs)
├── frmpropietario.java           # Panel Propietario (5 tabs)
├── frmarrendatario.java          # Panel Arrendatario (5 tabs)
├── ApiClient.java                # Cliente HTTP REST
├── SesionUsuario.java            # Singleton de Sesión
└── Validador.java                # Validaciones de Formularios
```

#### Funcionalidades Principales

**frmlogin**:
- ✅ Login con validación
- ✅ Registro de nuevos usuarios
- ✅ Recuperar contraseña (envía notificación)
- ✅ Validación de campos
- ✅ Redirección según rol

**frmadministrador** (7 pestañas):
1. **DASHBOARD**: Estadísticas en tiempo real (6 métricas)
2. **USUARIOS**: CRUD completo + Activar/Desactivar
3. **CONTRATOS**: CRUD + Ver Detalle + Finalizar
4. **PAGOS**: Ver Detalle + Cambiar Estado + Marcar Pagado/Vencido
5. **INMUEBLES**: CRUD completo + Ver Detalle
6. **MANTENIMIENTO**: Aprobar/Iniciar/Completar/Rechazar + Ver Detalle
7. **MI PERFIL**: Editar datos personales

**frmpropietario** (5 pestañas):
1. **MIS INMUEBLES**: CRUD + Estadísticas (Total/Disponibles/Arrendados)
2. **CONTRATOS**: Crear/Ver Detalle/Finalizar + Listar arrendatarios
3. **PAGOS RECIBIDOS**: Visualización + Total calculado en tiempo real
4. **REPORTES**: 5 tipos (Rentabilidad, Ocupación, Pagos, Mantenimiento, Estado)
5. **MI PERFIL**: Editar datos personales

**frmarrendatario** (5 pestañas):
1. **MI CONTRATO**: Ver contrato activo + Detalles completos
2. **MIS PAGOS**: Registrar pagos + Ver historial
3. **MANTENIMIENTO**: Solicitar + Ver estado de solicitudes
4. **NOTIFICACIONES**: Ver notificaciones + Marcar como leídas + Doble-clic para detalle
5. **MI PERFIL**: Editar datos + Botón "Cambiar Contraseña"

#### ApiClient - Cliente HTTP

```java
public class ApiClient {
    // Puertos de servicios
    public static final int USUARIOS_PORT = 8086;
    public static final int INMUEBLES_PORT = 8083;
    public static final int CONTRATOS_PORT = 8084;
    public static final int PAGOS_PORT = 8085;
    public static final int MANTENIMIENTO_PORT = 8087;
    public static final int NOTIFICACIONES_PORT = 8088;
    
    // Métodos HTTP
    public static JSONObject get(int puerto, String endpoint)
    public static JSONObject post(int puerto, String endpoint, JSONObject datos)
    public static JSONObject put(int puerto, String endpoint, JSONObject datos)
    public static JSONObject delete(int puerto, String endpoint)
}
```

**Response Format Unificado**:
```json
{
  "statusCode": 200,
  "data": { /* datos */ }
}
```

---

## ⚙️ Microservicios Backend

### 1. Usuarios Service (:8086)

**Responsabilidad**: Autenticación y gestión de usuarios

**Endpoints Implementados**:
```
POST   /api/usuarios/registro          # Crear usuario (ADMIN/PROPIETARIO/ARRENDATARIO)
POST   /api/usuarios/login             # Login con BCrypt
GET    /api/usuarios/{id}              # Obtener usuario
GET    /api/usuarios?tipo=ADMIN        # Filtrar por tipo
PUT    /api/usuarios/{id}/activar      # Activar usuario
PUT    /api/usuarios/{id}/desactivar   # Desactivar usuario
```

**Funcionalidades**:
- ✅ Hash BCrypt de contraseñas
- ✅ Validación de email único
- ✅ Gestión de roles (ADMINISTRADOR/PROPIETARIO/ARRENDATARIO)
- ✅ Estado activo/inactivo

---

### 2. Inmuebles Service (:8083)

**Responsabilidad**: Catálogo de propiedades

**Endpoints Implementados**:
```
POST   /api/inmuebles                           # Crear inmueble
GET    /api/inmuebles                           # Listar todos
GET    /api/inmuebles/{id}                      # Obtener por ID
GET    /api/inmuebles?propietarioId=1           # Filtrar por propietario
PUT    /api/inmuebles/{id}                      # Actualizar completo ✨ NUEVO
PUT    /api/inmuebles/{id}/disponibilidad       # Solo disponibilidad
DELETE /api/inmuebles/{id}                      # Eliminar inmueble ✨ NUEVO
```

**Funcionalidades Nuevas Hoy**:
- ✅ **PUT completo**: Actualizar todos los campos del inmueble
- ✅ **DELETE**: Eliminar inmueble (con validación de contrato activo)
- ✅ Validaciones de precio > 0, área > 0

---

### 3. Contratos Service (:8084)

**Responsabilidad**: Gestión de contratos de arrendamiento

**Endpoints Implementados**:
```
POST   /api/contratos                    # Crear contrato
GET    /api/contratos/{id}               # Obtener por ID
GET    /api/contratos?propietarioId=1    # Filtrar por propietario
GET    /api/contratos?arrendatarioId=7   # Filtrar por arrendatario
GET    /api/contratos?inmuebleId=1       # Filtrar por inmueble
PUT    /api/contratos/{id}               # Actualizar contrato ✨ NUEVO
PUT    /api/contratos/{id}/finalizar     # Finalizar contrato ✨ IMPLEMENTADO HOY
DELETE /api/contratos/{id}               # Eliminar contrato ✨ NUEVO
```

**Funcionalidades Nuevas Hoy**:
- ✅ **Finalizar Contrato**: Cambia estado a FINALIZADO + Marca inmueble como disponible
- ✅ **Editar Contrato**: Actualización completa de campos
- ✅ **Eliminar Contrato**: Con confirmaciones múltiples

**Lógica de Negocio**:
- Al crear: Valida inmueble disponible + Marca como NO disponible
- Al finalizar: Marca inmueble como disponible nuevamente
- Validación: `fechaFin > fechaInicio`

---

### 4. Pagos Service (:8085)

**Responsabilidad**: Sistema de pagos y seguimiento

**Endpoints Implementados**:
```
POST   /api/pagos                        # Registrar pago
GET    /api/pagos                        # Listar todos
GET    /api/pagos?contrato=1             # Filtrar por contrato
GET    /api/pagos?arrendatario=7         # Filtrar por arrendatario
PUT    /api/pagos/{id}/estado?estado=PAGADO  # Cambiar estado ✨ IMPLEMENTADO HOY
```

**Funcionalidades Nuevas Hoy**:
- ✅ **Cambiar Estado**: PENDIENTE → PAGADO / VENCIDO / PARCIAL
- ✅ **Marcar como Pagado**: Atajo rápido con validaciones
- ✅ **Marcar como Vencido**: Con warnings de mora
- ✅ **Ver Detalle Completo**: Popup con todos los datos + color dinámico

**Estados de Pago**:
- `PENDIENTE`: Esperando pago
- `PAGADO`: Confirmado ✅
- `VENCIDO`: Fuera de fecha 🔴
- `PARCIAL`: Pago parcial

---

### 5. Mantenimiento Service (:8087)

**Responsabilidad**: Solicitudes de mantenimiento

**Endpoints Implementados**:
```
POST   /api/mantenimiento                       # Crear solicitud
GET    /api/mantenimiento                       # Listar todas
GET    /api/mantenimiento?solicitante=7         # Filtrar por solicitante
GET    /api/mantenimiento?inmueble=1            # Filtrar por inmueble
PUT    /api/mantenimiento/{id}                  # Actualizar ✨ NUEVO
PUT    /api/mantenimiento/{id}/aprobar          # Aprobar ✨ IMPLEMENTADO HOY
PUT    /api/mantenimiento/{id}/iniciar          # Iniciar trabajo ✨ IMPLEMENTADO HOY
PUT    /api/mantenimiento/{id}/completar        # Completar ✨ IMPLEMENTADO HOY
PUT    /api/mantenimiento/{id}/rechazar         # Rechazar ✨ IMPLEMENTADO HOY
```

**Funcionalidades Nuevas Hoy**:
- ✅ **Workflow Completo**: PENDIENTE → APROBADO → EN_PROCESO → COMPLETADO
- ✅ **Aprobar**: Admin aprueba solicitud
- ✅ **Iniciar**: Trabajo en progreso
- ✅ **Completar**: Requiere costo real + observaciones
- ✅ **Rechazar**: Requiere motivo
- ✅ **Editar**: Actualizar título, descripción, tipo, prioridad, técnico, costo

**Flujo de Estados**:
```
PENDIENTE → APROBADO → EN_PROCESO → COMPLETADO
             ↓
         RECHAZADO
```

---

### 6. Notificaciones Service (:8088)

**Responsabilidad**: Sistema de notificaciones

**Endpoints Implementados**:
```
POST   /api/notificaciones                      # Crear notificación
GET    /api/notificaciones                      # Listar todas
GET    /api/notificaciones?destinatario=email   # Filtrar por destinatario
GET    /api/notificaciones/{id}                 # Obtener por ID
```

**Funcionalidades Nuevas Hoy**:
- ✅ **Envío Masivo**: Admin puede enviar a múltiples usuarios
- ✅ **Tipos**: EMAIL, SMS, WHATSAPP (simulado)
- ✅ **Estado**: PENDIENTE, ENVIADO, FALLIDO
- ✅ **Sistema de Leído**: Tracking visual en UI

**Uso en UI**:
- Admin: Enviar notificaciones masivas
- Arrendatario: Ver notificaciones + Doble-clic para detalle + Marcar como leída

---

### 7. Propietarios Service (:8082)

**Responsabilidad**: Gestión de propietarios (legacy)

**Endpoints**:
```
POST   /api/propietarios           # Crear propietario
GET    /api/propietarios/{id}      # Obtener por ID
```

**Nota**: Este servicio podría fusionarse con Usuarios Service en el futuro.

---

### 8. Reportes Service (:8089)

**Responsabilidad**: Generación de reportes

**Reportes Implementados** (calculados en UI):
1. **Reporte de Rentabilidad**: Ingresos vs Gastos (Mantenimiento)
2. **Reporte de Ocupación**: % de ocupación de inmuebles
3. **Reporte de Pagos**: Historial completo por contrato
4. **Reporte de Mantenimiento**: Estadísticas por estado
5. **Estado de Inmuebles**: Listado completo con disponibilidad

---

## 💾 Arquitectura de Datos

### Modelo de Datos Completo

```
┌─────────────┐
│  usuarios   │
│  id (PK)    │
│  nombre     │
│  apellido   │
│  correo     │◄─────────────┐
│  contrasena │              │
│  tipo       │              │
│  activo     │              │
└─────────────┘              │
                             │
┌─────────────┐              │
│propietarios │              │
│  id (PK)    │              │
└──────┬──────┘              │
       │                     │
       │ 1:N                 │
       ▼                     │
┌─────────────┐              │
│ inmuebles   │              │
│  id (PK)    │              │
│  propietar. │              │ N:1
│  tipo       │              │
│  direccion  │◄─────┐       │
│  disponible │      │       │
└──────┬──────┘      │       │
       │             │       │
       │ 1:N         │       │
       ▼             │       │
┌─────────────┐      │       │
│ contratos   │      │       │
│  id (PK)    │      │       │
│  inmueble   │──────┘       │
│  propietar. │              │
│  arrendat.  │──────────────┘
│  estado     │
└──────┬──────┘
       │
       │ 1:N
       ├───────────────┐
       ▼               ▼
┌─────────────┐ ┌──────────────────┐
│   pagos     │ │solicitudes_mant. │
│  id (PK)    │ │  id (PK)         │
│  contrato   │ │  inmueble        │
│  valor      │ │  tipo            │
│  estado     │ │  prioridad       │
│  metodoPago │ │  estado          │
└─────────────┘ └──────────────────┘

┌──────────────────┐
│ notificaciones   │
│  id (PK)         │
│  destinatario    │
│  asunto          │
│  mensaje         │
│  tipo            │
│  estado          │
│  contrato_id     │
└──────────────────┘
```

### Tablas Principales

| Tabla | Registros | Uso |
|-------|-----------|-----|
| usuarios | ~100s | Autenticación y gestión de usuarios |
| inmuebles | ~1000s | Catálogo de propiedades |
| contratos | ~500s | Contratos activos e históricos |
| pagos | ~10,000s | Transacciones mensuales |
| solicitudes_mantenimiento | ~1000s | Tickets de mantenimiento |
| notificaciones | ~50,000s | Historial de notificaciones |
| propietarios | ~50s | Datos adicionales de propietarios |

---

## 🔄 Patrones de Diseño

### 1. Patrón Repository (Backend)
```java
@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble, Long> {
    List<Inmueble> findByPropietarioId(Long propietarioId);
    List<Inmueble> findByDisponible(Boolean disponible);
}
```

### 2. Patrón DTO (Data Transfer Object)
```java
public class InmuebleRequest {
    private Long propietarioId;
    private String tipo;
    private String direccion;
    // ... más campos
}
```

### 3. Patrón Singleton (Frontend)
```java
public class SesionUsuario {
    private static int usuarioID;
    private static String nombre;
    // ... métodos estáticos
}
```

### 4. Patrón Strategy (Validaciones)
```java
public class Validador {
    public static boolean noVacio(String valor, String campo, StringBuilder errores)
    public static boolean email(String valor, StringBuilder errores)
    public static boolean numeroPositivo(String valor, String campo, StringBuilder errores)
    // ... más validaciones
}
```

---

## 🔗 Comunicación

### Frontend → Backend

**Todas las comunicaciones usan REST API con JSON**

```java
// Ejemplo: Crear inmueble
JSONObject datos = new JSONObject();
datos.put("tipo", "APARTAMENTO");
datos.put("direccion", "Calle 123");
datos.put("precio", 1500000);

JSONObject response = ApiClient.post(8083, "/api/inmuebles", datos);
```

### Backend → Backend

**Comunicación Inter-Servicio con WebClient**

```java
@Service
public class ContratoService {
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private Boolean validarInmuebleDisponible(Long inmuebleId) {
        return webClient.get()
            .uri(inmueblesServiceUrl + "/api/inmuebles/" + inmuebleId)
            .retrieve()
            .bodyToMono(Boolean.class)
            .block();
    }
}
```

---

## 🛡️ Seguridad

### Implementaciones Actuales

1. **Password Hashing**: BCrypt con 10 rounds
2. **Validación de Entrada**: En frontend Y backend
3. **CORS Enabled**: Para permitir llamadas del frontend
4. **Environment Variables**: Credenciales en `.env`

### Pendiente (Roadmap)

- [ ] JWT Tokens para sesiones
- [ ] Rate Limiting
- [ ] HTTPS/TLS
- [ ] Input Sanitization adicional

---

## 📊 Métricas del Sistema

### Complejidad

| Métrica | Valor |
|---------|-------|
| Total de Líneas de Código Java | ~13,500 |
| Clases Frontend | 6 |
| Microservicios | 8 |
| Endpoints REST | 52 |
| Tablas BD | 7 |
| Funcionalidades Completas | 45+ |

### Cobertura Funcional

| Módulo | Cobertura | Estado |
|--------|-----------|--------|
| Autenticación | 100% | ✅ Completo |
| CRUD Inmuebles | 100% | ✅ Completo |
| CRUD Contratos | 100% | ✅ Completo |
| Gestión Pagos | 100% | ✅ Completo |
| Mantenimiento | 100% | ✅ Completo |
| Notificaciones | 90% | ⚠️ Falta "marcar leído" en BD |
| Reportes | 80% | ⚠️ Calculados en UI, no BD |
| Mi Perfil | 70% | ⚠️ Falta PUT en backend |

---

## 🚀 Próximos Pasos Arquitectónicos

### Fase 1: Mejoras Inmediatas
- [ ] Implementar `PUT /api/usuarios/{id}` para editar perfil
- [ ] Implementar `PUT /api/notificaciones/{id}/marcar-leida`
- [ ] Agregar paginación a endpoints que retornan listas

### Fase 2: Escalabilidad
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Service Discovery (Eureka)
- [ ] Circuit Breaker (Resilience4j)
- [ ] Distributed Tracing (Zipkin)

### Fase 3: Modernización
- [ ] Event-Driven Architecture (Kafka)
- [ ] CQRS para reportes
- [ ] Cache distribuido (Redis)
- [ ] Base de datos por microservicio

---

## 📞 Referencias

- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Documentación Completa**: [../DOCUMENTACION.md](../DOCUMENTACION.md)
- **API Documentation**: [./API.md](./API.md)
- **Database Schema**: [./DATABASE.md](./DATABASE.md)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última revisión**: Octubre 2025  
**Versión del Sistema**: 2.0.0 (Con todas las implementaciones de Octubre 2025)
