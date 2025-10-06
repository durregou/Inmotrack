# 🚀 Inicio Rápido - 5 Minutos

## Guía Express para Ejecutar el Sistema

---

## ✅ Pre-requisitos (Solo primera vez)

```bash
# Verificar instalaciones
java -version    # Debe ser 17+
docker --version # Debe mostrar versión
```

Si falta algo, consulta [README.md](./README.md#-requisitos-previos).

---

## 🎯 3 Pasos para Empezar

### **Paso 1: Iniciar Microservicios** (⏱️ 1 min)

```bash
cd microservicios
docker-compose up -d
cd ..
```

**Espera 30 segundos** mientras los servicios inician.

---

### **Paso 2: Crear Datos de Prueba** (⏱️ 2 min)

```bash
# Crear usuarios
./scripts/crear-usuario-prueba.sh

# Cargar datos de ejemplo (opcional pero recomendado)
./scripts/inicializar-datos-microservicios.sh
./scripts/crear-notificaciones-demo-fixed.sh
```

---

### **Paso 3: Ejecutar la Aplicación** (⏱️ 10 seg)

```bash
./scripts/run-app.sh
```

**¡Listo!** La ventana de login debería aparecer.

---

## 🔐 Credenciales de Prueba

| Usuario | Email | Contraseña |
|---------|-------|------------|
| **Admin** | `admin@sistema.com` | `admin123` |
| **Propietario** | `propietario@test.com` | `prop123` |
| **Arrendatario** | `inquilino@test.com` | `inqui123` |

---

## 🎨 Primer Login

1. **Ingresa**: `admin@sistema.com` / `admin123`
2. **Explora** las 7 pestañas del Dashboard
3. **Prueba**: Ver usuarios, contratos, pagos, etc.

---

## 📱 Qué Explorar Primero

### Como **Administrador** (`admin@sistema.com`)
1. **DASHBOARD** → Ver estadísticas generales
2. **USUARIOS** → Ver todos los usuarios del sistema
3. **PAGOS** → Cambiar estado de un pago a "PAGADO"
4. **MANTENIMIENTO** → Aprobar una solicitud

### Como **Propietario** (`propietario@test.com`)
1. **MIS INMUEBLES** → Ver tus propiedades
2. **CONTRATOS** → Ver contrato activo
3. **PAGOS RECIBIDOS** → Ver ingresos
4. **REPORTES** → Generar Reporte de Rentabilidad

### Como **Arrendatario** (`inquilino@test.com`)
1. **MI CONTRATO** → Ver detalles de tu contrato
2. **MIS PAGOS** → Ver historial de pagos
3. **MANTENIMIENTO** → Solicitar un nuevo mantenimiento
4. **NOTIFICACIONES** → Ver mensajes (doble-clic para detalle)

---

## 🛑 ¿Algo no funciona?

### Error: "No se puede conectar"
```bash
# Verificar servicios
docker ps | grep service

# Si no hay servicios, reiniciar
cd microservicios
docker-compose down
docker-compose up -d
```

### Error: "Credenciales incorrectas"
```bash
# Recrear usuarios
./scripts/crear-usuario-prueba.sh
```

### Error: "No aparecen datos"
```bash
# Recargar datos
./scripts/inicializar-datos-microservicios.sh
```

---

## 📚 Siguiente Paso

Una vez que explores el sistema, consulta:
- **[README.md](./README.md)** - Documentación completa del proyecto
- **[DOCUMENTACION.md](./DOCUMENTACION.md)** - Guía técnica detallada
- **[docs/README.md](./docs/README.md)** - Índice de documentación
- **[docs/MICROSERVICIOS.md](./docs/MICROSERVICIOS.md)** - Documentación de microservicios
- **[microservicios/postman-collections/](./microservicios/postman-collections/)** - Colecciones de Postman

---

## 💡 Tips

- **Cerrar ventana**: Usa el botón "Cerrar Sesión" (no X)
- **Actualizar datos**: Usa botones "Actualizar" en cada módulo
- **Ver detalles**: Doble-clic en cualquier fila de tabla
- **Atajos**: 
  - `Ctrl+Q` = Cerrar sesión (en desarrollo)
  - `F5` = Actualizar datos (en desarrollo)

---

## 🎉 ¡Disfruta el Sistema!

Si tienes preguntas, consulta la carpeta `docs/` o el README principal.

---

## 🐙 **GitHub**

**⭐ ¡Dale una estrella al proyecto!**  
[https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

**Reportar Bugs**: [Issues](https://github.com/durregou/Inmotrack/issues)  
**Contribuir**: [Pull Requests](https://github.com/durregou/Inmotrack/pulls)

---

**⏱️ Tiempo total**: 5 minutos  
**✅ Dificultad**: Fácil  
**📊 Nivel**: Principiante  
**👤 Autor**: [David Urrego](https://github.com/durregou)

