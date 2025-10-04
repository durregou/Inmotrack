# 🔐 Credenciales de Prueba

## Usuarios del Sistema

Usa estas credenciales para hacer login en la aplicación:

---

## 👔 ADMINISTRADOR
**Correo**: `admin@sistema.com`  
**Contraseña**: `admin123`  
**Rol**: Administrador

**Permisos**:
- ✅ Gestión completa del sistema
- ✅ Ver todos los usuarios
- ✅ Generar reportes
- ✅ Gestionar contratos
- ✅ Ver historial de mantenimiento

---

## 🏠 PROPIETARIO
**Correo**: `propietario@test.com`  
**Contraseña**: `prop123`  
**Rol**: Propietario

**Permisos**:
- ✅ Registrar inmuebles
- ✅ Ver mis propiedades
- ✅ Gestionar contratos
- ✅ Ver pagos recibidos
- ✅ Solicitar reportes de rentabilidad

---

## 🔑 ARRENDATARIO (Inquilino)
**Correo**: `inquilino@test.com`  
**Contraseña**: `inqui123`  
**Rol**: Arrendatario

**Permisos**:
- ✅ Ver mi contrato
- ✅ Ver historial de pagos
- ✅ Realizar pagos
- ✅ Solicitar mantenimiento
- ✅ Ver notificaciones

---

## 🧪 Usuario de Prueba Existente

**Correo**: `test@test.com`  
**Contraseña**: `test123`  
**Rol**: Arrendatario

---

## 📝 Cómo Crear Más Usuarios

### Opción 1: Desde la Interfaz
1. Abre la aplicación
2. Clic en "Registro"
3. Llena el formulario
4. Selecciona el tipo de usuario
5. Clic en "Registrar usuario"

### Opción 2: Con el Script
```bash
./crear-usuario-prueba.sh
```

### Opción 3: Con CURL
```bash
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Nuevo",
    "apellido": "Usuario",
    "correo": "nuevo@test.com",
    "contrasena": "password123",
    "telefono": "3001234567",
    "cedula": "123456789",
    "tipoUsuario": "ARRENDATARIO"
  }'
```

---

## ⚠️ Notas Importantes

1. **Seguridad**: Estas son credenciales de prueba. En producción, usa contraseñas seguras.
2. **Mayúsculas**: El correo NO es case-sensitive, pero la contraseña SÍ lo es.
3. **Roles disponibles**: 
   - `ADMINISTRADOR`
   - `PROPIETARIO`
   - `ARRENDATARIO`

---

## 🔄 Resetear la Base de Datos

Si necesitas empezar de cero:

```bash
cd microservicios
docker-compose down -v
docker-compose up -d
```

Luego vuelve a ejecutar:
```bash
./crear-usuario-prueba.sh
```

---

**¡Listo para usar!** 🚀

