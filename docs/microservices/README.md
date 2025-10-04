# 📚 Documentación de Microservicios

Documentación detallada de cada microservicio del sistema Inmotrack.

**GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

## 📋 Microservicios Disponibles

| Servicio | Puerto | Documentación | Endpoints | Estado |
|----------|--------|---------------|-----------|--------|
| **Usuarios** | 8086 | [USUARIOS-SERVICE.md](./USUARIOS-SERVICE.md) | 6 | ✅ Completo |
| **Inmuebles** | 8083 | [INMUEBLES-SERVICE.md](./INMUEBLES-SERVICE.md) | 7 | ✅ Completo |
| **Contratos** | 8084 | [CONTRATOS-SERVICE.md](./CONTRATOS-SERVICE.md) | 9 | ✅ Completo |
| **Pagos** | 8085 | [PAGOS-SERVICE.md](./PAGOS-SERVICE.md) | 6 | ✅ Completo |
| **Mantenimiento** | 8087 | [MANTENIMIENTO-SERVICE.md](./MANTENIMIENTO-SERVICE.md) | 9 | ✅ Completo |
| **Notificaciones** | 8088 | [NOTIFICACIONES-SERVICE.md](./NOTIFICACIONES-SERVICE.md) | 4 | ✅ Completo |
| **Propietarios** | 8082 | Legacy (no documentado) | 2 | ⚠️ Deprecado |
| **Reportes** | 8089 | Se calcula en UI | 0 | ⚠️ Sin API |

**Total**: **43 endpoints documentados** en 6 microservicios principales

---

## 📖 Qué Contiene Cada Documento

Cada documentación de microservicio incluye:

### 1. **Descripción General**
- Propósito del servicio
- Responsabilidades
- Puerto y configuración

### 2. **Modelo de Datos**
- Esquema de la tabla principal
- Tipos de datos
- Relaciones con otros servicios

### 3. **Endpoints Completos**
- Método HTTP
- Ruta
- Request/Response examples
- Códigos de estado
- Validaciones
- Lógica de negocio

### 4. **Seguridad**
- Validaciones implementadas
- Manejo de errores
- Best practices

### 5. **Casos de Uso**
- Ejemplos con curl
- Flujos completos
- Integración con otros servicios

### 6. **Testing**
- Datos de prueba
- Casos de prueba comunes
- Errores conocidos

### 7. **Estructura del Proyecto**
- Organización de carpetas
- Archivos principales
- Dependencias

---

## 🔗 API Completa

Para ver todos los endpoints en un solo documento:
- **[docs/API.md](../API.md)** - Documentación completa de API

---

## 📡 Postman Collections

Para testing y pruebas:
- **[microservicios/postman-collections/](../../microservicios/postman-collections/)** - Colecciones de Postman

---

## 🏗️ Arquitectura

Para entender cómo se comunican los microservicios:
- **[docs/ARCHITECTURE.md](../ARCHITECTURE.md)** - Arquitectura del sistema

---

## 🔗 GitHub

- **Repositorio**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Issues**: [Reportar Bugs](https://github.com/durregou/Inmotrack/issues)
- **Pull Requests**: [Contribuir](https://github.com/durregou/Inmotrack/pulls)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Última actualización**: Octubre 2025

