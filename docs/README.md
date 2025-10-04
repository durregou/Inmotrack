# 📚 Documentación del Proyecto

Esta carpeta contiene toda la documentación complementaria del sistema.

**GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

## 📁 Estructura de Documentación

```
docs/
├── README.md                      # Este archivo (índice)
├── ARCHITECTURE.md                # Arquitectura completa del sistema
├── API.md                         # Documentación de 52 endpoints
├── DATABASE.md                    # Esquema de base de datos
├── DEPLOYMENT.md                  # Guía de despliegue
└── microservices/                 # Documentación por microservicio
    ├── README.md                  # Índice de microservicios
    ├── USUARIOS-SERVICE.md        # Servicio de usuarios (✅ Completo)
    ├── INMUEBLES-SERVICE.md       # Servicio de inmuebles
    ├── CONTRATOS-SERVICE.md       # Servicio de contratos
    ├── PAGOS-SERVICE.md           # Servicio de pagos
    ├── MANTENIMIENTO-SERVICE.md   # Servicio de mantenimiento
    └── NOTIFICACIONES-SERVICE.md  # Servicio de notificaciones
```

---

## 📄 Documentos Principales

### 1. [ARCHITECTURE.md](./ARCHITECTURE.md) ✨
**Arquitectura completa del sistema**:
- Diagrama de 8 microservicios
- Capa de Presentación (Java Swing)
- Modelo de datos con 7 tablas
- Patrones de diseño implementados
- Métricas del sistema (13,500 LOC, 52 endpoints, 45+ features)
- Roadmap de próximos pasos

### 2. [API.md](./API.md) ✨
**Documentación completa de API REST**:
- **52 endpoints** documentados
- Request/Response con ejemplos
- Validaciones de negocio
- Códigos de estado HTTP
- Ejemplos con curl
- Flujo completo de uso

### 3. [MICROSERVICIOS.md](./MICROSERVICIOS.md) ✨ **UNIFICADO**
**Documentación completa de todos los microservicios en un solo documento**:
- **6 microservicios** documentados completamente
- **41 endpoints** con ejemplos y casos de uso
- **Workflows completos**: Contratos, Pagos, Mantenimiento
- **Comunicación entre servicios** explicada
- **Convenciones y estándares** unificados
- **2,500+ líneas** de documentación técnica

**También disponible por servicio individual**: [microservices/](./microservices/)
- [Usuarios Service](./microservices/USUARIOS-SERVICE.md) ✅
- [Inmuebles Service](./microservices/INMUEBLES-SERVICE.md) ✅
- [Contratos Service](./microservices/CONTRATOS-SERVICE.md) ✅
- [Pagos Service](./microservices/PAGOS-SERVICE.md) ✅
- [Mantenimiento Service](./microservices/MANTENIMIENTO-SERVICE.md) ✅
- [Notificaciones Service](./microservices/NOTIFICACIONES-SERVICE.md) ✅

### 4. [DATABASE.md](./DATABASE.md)
**Esquema de base de datos PostgreSQL**:
- Modelo relacional completo
- Tablas y relaciones
- Índices y constraints
- Datos de ejemplo

### 5. [DEPLOYMENT.md](./DEPLOYMENT.md)
**Guía de despliegue**:
- Docker Compose
- Variables de entorno
- Configuración de producción
- Monitoreo

---

## 📡 Postman Collections

### 🔥 **NUEVO**: Colecciones de Postman disponibles!

Ubicación: **[../microservicios/postman-collections/](../microservicios/postman-collections/)**

**Archivos**:
1. **Inmotrack-Complete.postman_collection.json** - 52 endpoints listos para usar
2. **Inmotrack-Local.postman_environment.json** - Variables de entorno configuradas

**Incluye**:
- ✅ 52 endpoints completos con ejemplos
- ✅ Variables pre-configuradas (puertos, URLs, credenciales)
- ✅ Organizados por microservicio
- ✅ Listos para importar y usar

**Cómo usar**:
```bash
1. Abre Postman
2. Import → Selecciona los 2 archivos JSON
3. Selecciona environment "Inmotrack - Local"
4. ¡Listo para probar!
```

Ver guía completa: [../microservicios/postman-collections/README.md](../microservicios/postman-collections/README.md)

---

## 🎯 Guía de Consulta Rápida

### "¿Cómo funciona el sistema?"
→ [ARCHITECTURE.md](./ARCHITECTURE.md) - Arquitectura completa

### "¿Qué endpoints hay disponibles?"
→ [API.md](./API.md) - 52 endpoints documentados

### "¿Cómo probar los endpoints?"
→ [../microservicios/postman-collections/](../microservicios/postman-collections/) - Colecciones de Postman

### "¿Cómo funciona el servicio de usuarios?"
→ [microservices/USUARIOS-SERVICE.md](./microservices/USUARIOS-SERVICE.md) - Documentación detallada

### "¿Qué tablas hay en la BD?"
→ [DATABASE.md](./DATABASE.md) - Esquema completo

### "¿Cómo despliego el sistema?"
→ [DEPLOYMENT.md](./DEPLOYMENT.md) - Guía paso a paso

---

## 📖 Documentación Principal del Proyecto

- **[../README.md](../README.md)** - Punto de entrada principal
- **[../DOCUMENTACION.md](../DOCUMENTACION.md)** - Documentación técnica completa
- **[../INICIO_RAPIDO.md](../INICIO_RAPIDO.md)** - Guía de 5 minutos

---

## 🔗 Enlaces Útiles

### GitHub
- **Repositorio**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- **Autor**: [David Urrego](https://github.com/durregou)
- 🐛 [Reportar Bugs](https://github.com/durregou/Inmotrack/issues)
- 🔧 [Pull Requests](https://github.com/durregou/Inmotrack/pulls)
- ⭐ [Dale una estrella](https://github.com/durregou/Inmotrack)

### Scripts Útiles
- **[../scripts/](../scripts/)** - Scripts de automatización
- **[../scripts/README.md](../scripts/README.md)** - Guía de scripts

---

## 📊 Estadísticas de Documentación

| Documento | Líneas | Estado |
|-----------|--------|--------|
| ARCHITECTURE.md | 607 | ✅ Completo |
| API.md | 866 | ✅ Completo |
| USUARIOS-SERVICE.md | 450+ | ✅ Completo |
| Postman Collection | 52 endpoints | ✅ Completo |
| **Total** | **3,100+ líneas** | ✅ |

---

**Última actualización**: Octubre 2025  
**Versión del Sistema**: 2.0.0
