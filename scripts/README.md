# 📜 Scripts de Utilidad

Esta carpeta contiene scripts auxiliares para facilitar el desarrollo y testing del sistema.

---

## 🚀 Scripts Principales

### `run-app.sh`
**Descripción**: Ejecuta la aplicación Java Swing.

**Uso**:
```bash
./scripts/run-app.sh
```

**Qué hace**:
1. Verifica si los microservicios están corriendo
2. Ofrece iniciarlos si no lo están
3. Compila la aplicación (si hay cambios)
4. Inicia la interfaz gráfica

---

### `crear-usuario-prueba.sh`
**Descripción**: Crea 3 usuarios de prueba (Admin, Propietario, Arrendatario).

**Uso**:
```bash
./scripts/crear-usuario-prueba.sh
```

**Usuarios creados**:
- `admin@sistema.com` / `admin123` (ADMINISTRADOR)
- `propietario@test.com` / `prop123` (PROPIETARIO)
- `inquilino@test.com` / `inqui123` (ARRENDATARIO)

**Cuándo usar**: Primera vez que ejecutas el sistema o después de resetear la BD.

---

### `inicializar-datos-microservicios.sh`
**Descripción**: Inserta datos de prueba en la base de datos (inmuebles, contratos, pagos, mantenimientos).

**Uso**:
```bash
./scripts/inicializar-datos-microservicios.sh
```

**Datos insertados**:
- 2 propietarios
- 3 inmuebles
- 1 contrato activo
- 3 pagos
- 2 solicitudes de mantenimiento

**Cuándo usar**: Después de crear usuarios, para tener datos de ejemplo.

---

### `crear-notificaciones-demo-fixed.sh`
**Descripción**: Inserta notificaciones de prueba para el arrendatario.

**Uso**:
```bash
./scripts/crear-notificaciones-demo-fixed.sh
```

**Qué hace**:
- Inserta 10 notificaciones variadas (EMAIL, SMS, WHATSAPP)
- Destinatario: `inquilino@test.com`
- Tipos: Bienvenida, Pagos, Mantenimiento, etc.

**Cuándo usar**: Para probar el módulo de notificaciones del arrendatario.

---

## 🗂️ Scripts Legacy (No recomendados)

### `crear-datos-prueba-completos.sh`
**Estado**: Deprecado  
**Razón**: Usaba API calls que podían fallar. Reemplazado por `inicializar-datos-microservicios.sh` que inserta directamente en BD.

### `crear-notificaciones-demo.sh`
**Estado**: Deprecado  
**Razón**: Usuario incorrecto. Reemplazado por `crear-notificaciones-demo-fixed.sh`.

### `crear-notificaciones-demo-docker.sh`
**Estado**: Deprecado  
**Razón**: Nombre de BD incorrecto. Reemplazado por `crear-notificaciones-demo-fixed.sh`.

---

## 🔄 Flujo de Trabajo Recomendado

### Primera Vez (Setup Completo)
```bash
# 1. Iniciar microservicios
cd microservicios
docker-compose up -d
cd ..

# 2. Esperar 30-60 segundos

# 3. Crear usuarios
./scripts/crear-usuario-prueba.sh

# 4. Cargar datos de ejemplo
./scripts/inicializar-datos-microservicios.sh
./scripts/crear-notificaciones-demo-fixed.sh

# 5. Ejecutar aplicación
./scripts/run-app.sh
```

### Uso Diario
```bash
# Solo ejecutar la app (microservicios ya corriendo)
./scripts/run-app.sh
```

### Resetear Todo
```bash
# 1. Detener microservicios
cd microservicios
docker-compose down -v

# 2. Reiniciar
docker-compose up -d
cd ..

# 3. Recargar datos
./scripts/crear-usuario-prueba.sh
./scripts/inicializar-datos-microservicios.sh
./scripts/crear-notificaciones-demo-fixed.sh
```

---

## 🛠️ Requisitos

- **Bash**: Shell Unix (macOS/Linux)
- **Docker**: Para microservicios
- **curl**: Para API calls (crear usuarios)
- **psql**: Para inserción directa en BD (incluido en Docker)

---

## 📝 Notas

- Todos los scripts deben ejecutarse desde la **raíz del proyecto**
- Asegúrate de que los scripts tengan permisos de ejecución:
  ```bash
  chmod +x scripts/*.sh
  ```
- Si un script falla, verifica los logs de Docker:
  ```bash
  cd microservicios
  docker-compose logs usuarios-service
  ```

---

## 🆘 Solución de Problemas

### Error: "Permission denied"
```bash
chmod +x scripts/*.sh
```

### Error: "Connection refused"
Microservicios no están corriendo:
```bash
cd microservicios
docker-compose up -d
# Esperar 30-60 segundos
```

### Error: "User already exists"
Los usuarios ya fueron creados. Puedes:
1. Ignorar el error (es normal)
2. O resetear la BD (ver "Resetear Todo" arriba)

---

## 🐙 **GitHub**

**Repositorio**: [Inmotrack](https://github.com/durregou/Inmotrack)  
**Autor**: [David Urrego](https://github.com/durregou)

**⭐ ¡Dale una estrella si te gusta el proyecto!**

---

**Última actualización**: Octubre 2025

