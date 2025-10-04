# 👤 Usuarios Service

**Puerto**: 8086  
**Repositorio**: usuarios-service  
**Base de Datos**: PostgreSQL (tabla `usuarios`)

---

## 📋 Descripción

Microservicio responsable de la autenticación y gestión de usuarios del sistema. Maneja tres roles: ADMINISTRADOR, PROPIETARIO y ARRENDATARIO.

---

## 🎯 Responsabilidades

- ✅ **Registro de usuarios** con roles diferenciados
- ✅ **Autenticación** con hash BCrypt
- ✅ **Gestión de usuarios**: activar/desactivar cuentas
- ✅ **Validación** de emails únicos
- ✅ **Filtrado** por tipo de usuario

---

## 🗄️ Modelo de Datos

### Tabla: `usuarios`

| Campo | Tipo | Restricción | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `nombre` | VARCHAR(100) | NOT NULL | Nombre del usuario |
| `apellido` | VARCHAR(100) | NOT NULL | Apellido del usuario |
| `correo` | VARCHAR(150) | UNIQUE, NOT NULL | Email (usado para login) |
| `contrasena` | VARCHAR(255) | NOT NULL | Hash BCrypt de la contraseña |
| `telefono` | VARCHAR(20) | | Teléfono de contacto |
| `direccion` | VARCHAR(255) | | Dirección física |
| `cedula` | VARCHAR(20) | | Número de identificación |
| `tipo_usuario` | VARCHAR(50) | NOT NULL | ADMINISTRADOR, PROPIETARIO, ARRENDATARIO |
| `activo` | BOOLEAN | DEFAULT TRUE | Estado de la cuenta |
| `fecha_registro` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de creación |

---

## 🔌 Endpoints

### 1. **POST** `/api/usuarios/registro`
Registrar un nuevo usuario en el sistema.

**Request Body**:
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "contrasena": "password123",
  "telefono": "3001234567",
  "direccion": "Calle 123 #45-67",
  "cedula": "1234567890",
  "tipoUsuario": "PROPIETARIO"
}
```

**Response 201 Created**:
```json
{
  "mensaje": "Usuario registrado exitosamente",
  "usuario": {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "correo": "juan@example.com",
    "tipoUsuario": "PROPIETARIO",
    "activo": true,
    "fechaRegistro": "2025-10-04T10:00:00"
  }
}
```

**Validaciones**:
- ✅ Email único (no puede existir otro usuario con el mismo correo)
- ✅ Contraseña mínimo 6 caracteres
- ✅ Tipo de usuario válido: ADMINISTRADOR, PROPIETARIO, ARRENDATARIO
- ✅ Campos obligatorios: nombre, apellido, correo, contrasena, tipoUsuario

**Lógica de Negocio**:
- La contraseña se hashea con BCrypt (10 rounds) antes de guardar
- El usuario se crea con `activo = true` por defecto

---

### 2. **POST** `/api/usuarios/login`
Autenticar un usuario en el sistema.

**Request Body**:
```json
{
  "correo": "admin@sistema.com",
  "contrasena": "admin123"
}
```

**Response 200 OK**:
```json
{
  "id": 5,
  "nombre": "Admin",
  "apellido": "Sistema",
  "correo": "admin@sistema.com",
  "tipoUsuario": "ADMINISTRADOR",
  "activo": true
}
```

**Response 401 Unauthorized**:
```json
{
  "error": "Credenciales inválidas"
}
```

**Lógica de Negocio**:
- Busca usuario por email
- Valida contraseña con BCrypt.matches()
- Retorna datos del usuario si es exitoso (sin contraseña)

---

### 3. **GET** `/api/usuarios/{id}`
Obtener datos completos de un usuario por su ID.

**Response 200 OK**:
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "telefono": "3001234567",
  "direccion": "Calle 123 #45-67",
  "cedula": "1234567890",
  "tipoUsuario": "PROPIETARIO",
  "activo": true,
  "fechaRegistro": "2025-10-04T10:00:00"
}
```

**Response 404 Not Found**:
```json
{
  "error": "Usuario no encontrado"
}
```

---

### 4. **GET** `/api/usuarios?tipo={TIPO}`
Listar usuarios filtrados por tipo.

**Query Parameters**:
- `tipo` (opcional): ADMINISTRADOR | PROPIETARIO | ARRENDATARIO

**Ejemplo**:
```
GET /api/usuarios?tipo=ARRENDATARIO
```

**Response 200 OK**:
```json
[
  {
    "id": 7,
    "nombre": "Maria",
    "apellido": "Inquilina",
    "correo": "inquilino@test.com",
    "tipoUsuario": "ARRENDATARIO",
    "activo": true
  },
  {
    "id": 8,
    "nombre": "Carlos",
    "apellido": "Gómez",
    "correo": "carlos@example.com",
    "tipoUsuario": "ARRENDATARIO",
    "activo": true
  }
]
```

**Comportamiento**:
- Si no se proporciona `tipo`, retorna **todos** los usuarios
- Si se proporciona `tipo`, retorna solo usuarios de ese tipo

---

### 5. **PUT** `/api/usuarios/{id}/activar`
Activar cuenta de un usuario.

**Response 200 OK**:
```json
{
  "mensaje": "Usuario activado exitosamente",
  "usuario": {
    "id": 1,
    "activo": true
  }
}
```

**Lógica**:
- Cambia el campo `activo` a `true`
- Útil para reactivar cuentas previamente desactivadas

---

### 6. **PUT** `/api/usuarios/{id}/desactivar`
Desactivar cuenta de un usuario.

**Response 200 OK**:
```json
{
  "mensaje": "Usuario desactivado exitosamente",
  "usuario": {
    "id": 1,
    "activo": false
  }
}
```

**Lógica**:
- Cambia el campo `activo` a `false`
- El usuario no podrá hacer login
- **No elimina** el usuario de la base de datos (soft delete)

---

## 🔐 Seguridad

### Hash de Contraseñas
```java
// Al registrar
String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));

// Al hacer login
boolean isValid = BCrypt.checkpw(plainPassword, hashedPassword);
```

**Configuración**:
- **Algoritmo**: BCrypt
- **Rounds**: 10 (balance entre seguridad y performance)
- **Salt**: Generado automáticamente por BCrypt

### Validaciones Implementadas

1. ✅ **Email único**: No permite duplicados
2. ✅ **Longitud de contraseña**: Mínimo 6 caracteres
3. ✅ **Tipo de usuario válido**: Solo roles permitidos
4. ✅ **Formato de email**: Validación básica

---

## 🔗 Dependencias

### Otros Servicios
Este servicio **NO** depende de otros microservicios.

### Base de Datos
- **PostgreSQL**: `arrendamiento_db`
- **Usuario**: `arrendamiento_user`
- **Puerto**: 5432

---

## 📊 Casos de Uso

### 1. Flujo de Registro + Login
```bash
# 1. Registrar usuario
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test",
    "apellido": "Usuario",
    "correo": "test@example.com",
    "contrasena": "test123",
    "tipoUsuario": "ARRENDATARIO"
  }'

# 2. Hacer login
curl -X POST http://localhost:8086/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "test@example.com",
    "contrasena": "test123"
  }'
```

### 2. Gestión de Usuarios (Admin)
```bash
# Listar todos los arrendatarios
curl http://localhost:8086/api/usuarios?tipo=ARRENDATARIO

# Desactivar un usuario
curl -X PUT http://localhost:8086/api/usuarios/8/desactivar

# Reactivar el usuario
curl -X PUT http://localhost:8086/api/usuarios/8/activar
```

---

## 🧪 Testing

### Usuarios de Prueba Creados

| Email | Password | Rol | ID |
|-------|----------|-----|-----|
| `admin@sistema.com` | `admin123` | ADMINISTRADOR | 5 |
| `propietario@test.com` | `prop123` | PROPIETARIO | 6 |
| `inquilino@test.com` | `inqui123` | ARRENDATARIO | 7 |

---

## 🐛 Errores Comunes

### Error: "Email ya registrado"
**Causa**: Intento de registro con un correo ya existente  
**Solución**: Usar otro email o hacer login con el existente

### Error: "Credenciales inválidas"
**Causa**: Email no existe o contraseña incorrecta  
**Solución**: Verificar credenciales o registrar el usuario

### Error: "Usuario no encontrado"
**Causa**: ID no existe en la base de datos  
**Solución**: Verificar el ID o listar usuarios disponibles

---

## 🚀 Mejoras Futuras

### Fase 1 (Corto Plazo)
- [ ] **PUT** `/api/usuarios/{id}` - Editar datos del usuario
- [ ] **Recuperar contraseña** - Endpoint para reset
- [ ] **Validación de email** - Código de activación por email

### Fase 2 (Mediano Plazo)
- [ ] **JWT Tokens** - Autenticación con tokens
- [ ] **Refresh Tokens** - Renovación automática
- [ ] **Rate Limiting** - Protección contra fuerza bruta
- [ ] **2FA (Two-Factor Auth)** - Autenticación de dos factores

---

## 📁 Estructura del Proyecto

```
usuarios-service/
├── src/main/java/com/arrendamiento/usuarios/
│   ├── controller/
│   │   └── UsuarioController.java       # Endpoints REST
│   ├── service/
│   │   └── UsuarioService.java          # Lógica de negocio
│   ├── repository/
│   │   └── UsuarioRepository.java       # Acceso a BD
│   ├── model/
│   │   └── Usuario.java                 # Entidad JPA
│   └── dto/
│       ├── UsuarioRequest.java          # DTO de entrada
│       ├── LoginRequest.java            # DTO para login
│       └── LoginResponse.java           # DTO de respuesta
├── src/main/resources/
│   └── application.properties           # Configuración
└── pom.xml                              # Dependencias Maven
```

---

## 🔗 Referencias

- **API Completa**: [../API.md](../API.md)
- **Arquitectura**: [../ARCHITECTURE.md](../ARCHITECTURE.md)
- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última actualización**: Octubre 2025

