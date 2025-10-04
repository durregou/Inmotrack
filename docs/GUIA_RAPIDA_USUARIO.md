# 🎯 Guía Rápida de Usuario - Sistema de Arrendamiento

## 🚀 Inicio Rápido (3 pasos)

### Paso 1: Iniciar los Servicios
```bash
cd microservicios
docker-compose up -d
```
⏱️ *Espera ~30 segundos para que los servicios estén listos*

### Paso 2: Ejecutar la Aplicación
```bash
cd ..
./run-app.sh
```

### Paso 3: ¡Listo!
La ventana de login aparecerá automáticamente.

---

## 👤 Usando la Aplicación

### 🆕 Primera Vez - Crear una Cuenta

1. **Abrir la ventana de registro**
   - Haz clic en el botón **"Registro"** en la esquina superior izquierda

2. **Llenar el formulario**
   - **Nombre**: Tu nombre completo (ej: "Juan Pérez")
   - **Teléfono**: Tu número de teléfono
   - **Correo**: Tu correo electrónico (servirá como usuario)
   - **Documento**: Tu número de identificación
   - **Contraseña**: Una contraseña segura
   - **Tipo de usuario**: Selecciona tu rol:
     - **Administrador**: Gestiona todo el sistema
     - **Propietario**: Dueño de inmuebles
     - **Arrendatario**: Inquilino

3. **Registrar**
   - Haz clic en **"Registrar usuario"**
   - Verás un mensaje de éxito
   - La ventana se cerrará automáticamente

### 🔐 Iniciar Sesión

1. **En la ventana de login**
   - **Usuario**: Tu correo electrónico
   - **Contraseña**: Tu contraseña

2. **Hacer clic en "Iniciar sesión"**

3. **¡Listo!** Se abrirá tu panel según tu rol:
   - **Administrador**: Panel de administración
   - **Propietario**: Panel de propietario
   - **Arrendatario**: Panel de inquilino

---

## 🎭 Roles y Funcionalidades

### 👔 Administrador
**Permisos completos del sistema**

**Puede hacer:**
- ✅ Ver todos los usuarios
- ✅ Generar reportes del sistema
- ✅ Ver historial de mantenimiento
- ✅ Enviar notificaciones
- ✅ Gestionar contratos

### 🏠 Propietario
**Gestiona sus inmuebles y contratos**

**Puede hacer:**
- ✅ Registrar inmuebles
- ✅ Ver sus propiedades
- ✅ Ver contratos activos
- ✅ Ver pagos recibidos
- ✅ Solicitar reportes de rentabilidad
- ✅ Ver solicitudes de mantenimiento

### 🔑 Arrendatario (Inquilino)
**Gestiona su arrendamiento**

**Puede hacer:**
- ✅ Ver su contrato
- ✅ Ver historial de pagos
- ✅ Realizar pagos
- ✅ Solicitar mantenimiento
- ✅ Ver notificaciones

---

## 💡 Consejos y Tips

### ✨ Primer Uso
1. **Crea una cuenta de Administrador** primero para tener control total
2. Luego crea cuentas de Propietario e Inquilino para probar el sistema completo

### 🔒 Seguridad
- Usa una contraseña fuerte
- No compartas tus credenciales
- Cada usuario tiene acceso solo a sus datos

### 🐛 Solución de Problemas

**❌ "Error de conexión con el servidor"**
- Verifica que los microservicios estén corriendo:
  ```bash
  cd microservicios
  ./verificar-servicios.sh
  ```

**❌ "Credenciales incorrectas"**
- Verifica que tu correo y contraseña sean correctos
- El correo es case-sensitive (mayúsculas/minúsculas importan)

**❌ La ventana no aparece**
- Cierra cualquier instancia anterior:
  ```bash
  pkill -f Principal.frmlogin
  ```
- Vuelve a ejecutar:
  ```bash
  ./run-app.sh
  ```

**❌ "Todos los campos son obligatorios"**
- Asegúrate de llenar todos los campos del formulario
- En el registro, selecciona un tipo de usuario (no dejes "Seleccionar")

---

## 📊 Estados del Sistema

### 🟢 Sistema Funcionando Correctamente
```
✅ PostgreSQL está corriendo
✅ Usuarios Service (8086) - OK
✅ Todos los servicios respondiendo
```

### 🔴 Problema con los Servicios
```
❌ No se puede conectar al servicio
```
**Solución**: Reinicia los servicios
```bash
cd microservicios
docker-compose restart
```

---

## 🎨 Capturas de Pantalla del Flujo

### 1️⃣ Ventana de Login
```
┌─────────────────────────────┐
│     [👤 Icono Usuario]      │
│                             │
│  Usuario:  [____________]   │
│                             │
│  Contraseña: [__________]   │
│                             │
│     [Iniciar sesión]        │
│                             │
│  [Registro]                 │
└─────────────────────────────┘
```

### 2️⃣ Ventana de Registro
```
┌──────────────────────────────────────┐
│         Registro de Usuario          │
│                                      │
│  Nombre:     [____________]          │
│  Documento:  [____________]          │
│                                      │
│  Teléfono:   [____________]          │
│  Correo:     [____________]          │
│                                      │
│  Contraseña: [____________]          │
│                                      │
│  Tipo:       [Seleccionar ▼]         │
│               - Administrador        │
│               - Propietario          │
│  [Registrar usuario]  - Arrendatario │
└──────────────────────────────────────┘
```

---

## 📞 Soporte

¿Tienes problemas? Contacta al administrador del sistema o revisa:
- 📄 [Documentación Técnica](INTEGRACION_VISUAL_MICROSERVICIOS.md)
- 🏗️ [Arquitectura del Sistema](docs/ARCHITECTURE.md)
- 🔧 [Guía de Desarrollo](docs/DEVELOPMENT.md)

---

## ⚡ Comandos Útiles

```bash
# Ver estado de servicios
cd microservicios && ./verificar-servicios.sh

# Reiniciar todo el sistema
cd microservicios && docker-compose restart

# Ver logs de un servicio específico
cd microservicios && docker-compose logs -f usuarios-service

# Detener todo
cd microservicios && docker-compose down

# Iniciar todo
cd microservicios && docker-compose up -d
```

---

**¡Disfruta usando el Sistema de Gestión de Arrendamientos!** 🎉

