# 🏢 Sistema de Gestión de Arrendamientos

![GitHub](https://img.shields.io/badge/GitHub-Inmotrack-blue?logo=github)
![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow)

Sistema completo de gestión de propiedades en arriendo con arquitectura de microservicios y interfaz gráfica Swing.

**🐙 GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

## 📋 **Tabla de Contenidos**

1. [Descripción General](#-descripción-general)
2. [Características Principales](#-características-principales)
3. [Arquitectura](#-arquitectura)
4. [Requisitos Previos](#-requisitos-previos)
5. [Instalación y Configuración](#-instalación-y-configuración)
6. [Uso del Sistema](#-uso-del-sistema)
7. [Credenciales de Prueba](#-credenciales-de-prueba)
8. [Estructura del Proyecto](#-estructura-del-proyecto)
9. [Documentación Adicional](#-documentación-adicional)
10. [Tecnologías Utilizadas](#-tecnologías-utilizadas)

---

## 🎯 **Descripción General**

Sistema integral para la gestión de inmuebles en arriendo que permite:
- Administrar propiedades, contratos y pagos
- Gestionar solicitudes de mantenimiento
- Enviar notificaciones automatizadas
- Generar reportes detallados
- Control total de usuarios y permisos

**Roles del Sistema:**
- **👤 Administrador**: Control total del sistema
- **🏠 Propietario**: Gestión de inmuebles y contratos propios
- **🔑 Arrendatario**: Gestión de pagos, mantenimiento y notificaciones

---

## ✨ **Características Principales**

### **Módulo de Administración**
- ✅ Dashboard con estadísticas en tiempo real
- ✅ CRUD completo de usuarios, inmuebles, contratos
- ✅ Gestión avanzada de pagos (cambiar estado, marcar pagado/vencido)
- ✅ Control total de mantenimientos (aprobar, iniciar, completar, rechazar)
- ✅ Envío de notificaciones masivas
- ✅ Finalización de contratos

### **Módulo de Propietario**
- ✅ Gestión completa de inmuebles (crear, editar, eliminar)
- ✅ Administración de contratos (crear, ver detalle, finalizar)
- ✅ Seguimiento de pagos recibidos con total calculado
- ✅ 5 tipos de reportes:
  - Reporte de Rentabilidad
  - Reporte de Ocupación
  - Reporte de Pagos
  - Reporte de Mantenimiento
  - Estado de Inmuebles

### **Módulo de Arrendatario**
- ✅ Visualización de contrato activo
- ✅ Registro y seguimiento de pagos
- ✅ Solicitud de mantenimientos
- ✅ Centro de notificaciones (con sistema de "leído")
- ✅ Gestión de perfil personal

### **Funcionalidades Generales**
- ✅ Login seguro con JWT
- ✅ Recuperación de contraseña
- ✅ Mi Perfil (todos los roles)
- ✅ Validaciones completas en formularios
- ✅ Interfaz moderna y responsive

---

## 🏗️ **Arquitectura**

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                      │
│                  Java Swing (Desktop App)                    │
│                          Port: N/A                           │
└────────────────────────┬────────────────────────────────────┘
                         │ REST API (HTTP)
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE MICROSERVICIOS                     │
├──────────────────┬──────────────────┬───────────────────────┤
│ Usuarios         │ Inmuebles        │ Contratos             │
│ Port: 8086       │ Port: 8083       │ Port: 8084            │
├──────────────────┼──────────────────┼───────────────────────┤
│ Pagos            │ Mantenimiento    │ Notificaciones        │
│ Port: 8085       │ Port: 8087       │ Port: 8088            │
├──────────────────┼──────────────────┼───────────────────────┤
│ Propietarios     │ Reportes         │                       │
│ Port: 8082       │ Port: 8089       │                       │
└──────────────────┴──────────────────┴───────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE DATOS                             │
│                  PostgreSQL (Docker)                         │
│                      Port: 5432                              │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 **Requisitos Previos**

### **Software Necesario:**
- ✅ **Java JDK 17+** ([Descargar](https://www.oracle.com/java/technologies/downloads/))
- ✅ **Docker Desktop** ([Descargar](https://www.docker.com/products/docker-desktop))
- ✅ **Maven 3.8+** ([Descargar](https://maven.apache.org/download.cgi))
- ✅ **Git** ([Descargar](https://git-scm.com/downloads))

### **Verificar Instalación:**
```bash
java -version     # Debe mostrar Java 17 o superior
docker --version  # Debe mostrar Docker 20.10 o superior
mvn --version     # Debe mostrar Maven 3.8 o superior
```

---

## 🚀 **Instalación y Configuración**

### **Paso 1: Clonar el Repositorio**
```bash
git clone [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
cd arrendamientoProyecto\ 2
```

### **Paso 2: Iniciar Microservicios**
```bash
cd microservicios
docker-compose up -d
```

**Espera 30-60 segundos** para que todos los servicios inicien correctamente.

### **Paso 3: Verificar que los Servicios Estén Activos**
```bash
docker-compose ps
```

Deberías ver todos los servicios con estado `Up`.

### **Paso 4: Crear Usuarios de Prueba**
```bash
cd ..
./scripts/crear-usuario-prueba.sh
```

### **Paso 5: Inicializar Datos de Prueba** (Opcional)
```bash
./scripts/inicializar-datos-microservicios.sh
./scripts/crear-notificaciones-demo-fixed.sh
```

### **Paso 6: Ejecutar la Aplicación**
```bash
./scripts/run-app.sh
```

**¡Listo!** La ventana de login debería aparecer.

---

## 💻 **Uso del Sistema**

### **Inicio de Sesión**

Usa las credenciales de prueba (ver sección siguiente).

### **Navegación**

Cada panel tiene pestañas en la parte superior:
- **Admin**: Dashboard → Usuarios → Contratos → Pagos → Inmuebles → Mantenimiento → Mi Perfil
- **Propietario**: Mis Inmuebles → Contratos → Pagos Recibidos → Reportes → Mi Perfil
- **Arrendatario**: Mi Contrato → Mis Pagos → Mantenimiento → Notificaciones → Mi Perfil

### **Funciones Principales**

| Acción | Ubicación | Rol |
|--------|-----------|-----|
| Registrar inmueble | Propietario → Mis Inmuebles → Botón "Registrar Inmueble" | Propietario |
| Crear contrato | Propietario → Contratos → Botón "Crear Contrato" | Propietario |
| Realizar pago | Arrendatario → Mis Pagos → Botón "Realizar Pago" | Arrendatario |
| Solicitar mantenimiento | Arrendatario → Mantenimiento → Formulario | Arrendatario |
| Aprobar mantenimiento | Admin → Mantenimiento → Seleccionar → Botón "Aprobar" | Admin |
| Enviar notificación masiva | Admin → Dashboard → Botón "Enviar Notificación Masiva" | Admin |

---

## 🔑 **Credenciales de Prueba**

| Rol | Email | Contraseña |
|-----|-------|------------|
| 👤 **Administrador** | `admin@sistema.com` | `admin123` |
| 🏠 **Propietario** | `propietario@test.com` | `prop123` |
| 🔑 **Arrendatario** | `inquilino@test.com` | `inqui123` |

> 📝 **Nota**: Estos usuarios ya tienen datos de prueba asociados (inmuebles, contratos, pagos, etc.)

---

## 📁 **Estructura del Proyecto**

```
arrendamientoProyecto 2/
├── 📂 microservicios/          # Backend (Spring Boot)
│   ├── usuarios-service/
│   ├── inmuebles-service/
│   ├── contratos-service/
│   ├── pagos-service/
│   ├── mantenimiento-service/
│   ├── notificaciones-service/
│   ├── propietarios-service/
│   ├── reportes-service/
│   └── docker-compose.yml
│
├── 📂 src/Principal/           # Frontend (Java Swing)
│   ├── frmlogin.java          # Ventana de login
│   ├── frmadministrador.java # Panel de administrador
│   ├── frmpropietario.java   # Panel de propietario
│   ├── frmarrendatario.java  # Panel de arrendatario
│   ├── ApiClient.java         # Cliente HTTP para microservicios
│   └── SesionUsuario.java     # Gestión de sesión
│
├── 📂 scripts/                 # Scripts de utilidad
│   ├── run-app.sh             # Ejecutar aplicación
│   ├── crear-usuario-prueba.sh
│   ├── inicializar-datos-microservicios.sh
│   └── crear-notificaciones-demo-fixed.sh
│
├── 📂 docs/                    # Documentación adicional
│   ├── CREDENCIALES_PRUEBA.md
│   ├── GUIA_RAPIDA_USUARIO.md
│   ├── INTEGRACION_VISUAL_MICROSERVICIOS.md
│   └── RESUMEN_FINAL.md
│
├── 📂 build/                   # Archivos compilados
├── 📄 README.md               # Este archivo
└── 📄 DOCUMENTACION.md        # Documentación técnica completa
```

---

## 📚 **Documentación Adicional**

- **[DOCUMENTACION.md](./DOCUMENTACION.md)** - Documentación técnica completa del sistema
- **[docs/CREDENCIALES_PRUEBA.md](./docs/CREDENCIALES_PRUEBA.md)** - Lista completa de credenciales
- **[docs/GUIA_RAPIDA_USUARIO.md](./docs/GUIA_RAPIDA_USUARIO.md)** - Guía de uso detallada
- **[docs/INTEGRACION_VISUAL_MICROSERVICIOS.md](./docs/INTEGRACION_VISUAL_MICROSERVICIOS.md)** - Detalles de integración
- **[microservicios/README.md](./microservicios/README.md)** - Documentación de microservicios

---

## 🛠️ **Tecnologías Utilizadas**

### **Frontend**
- Java Swing
- JSON (org.json)
- HttpURLConnection

### **Backend**
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL 15
- Docker & Docker Compose
- JWT (JSON Web Tokens)
- BCrypt (Hashing de contraseñas)

### **Arquitectura**
- Microservicios
- REST API
- Patrón Repository
- DTOs (Data Transfer Objects)

---

## 🐛 **Solución de Problemas**

### **Error: "Puerto ya en uso"**
```bash
# Detener todos los contenedores
cd microservicios
docker-compose down

# Reiniciar
docker-compose up -d
```

### **Error: "No se puede conectar al servicio"**
```bash
# Verificar logs de un servicio específico
docker-compose logs usuarios-service

# Reiniciar un servicio específico
docker-compose restart usuarios-service
```

### **Error: "La aplicación no inicia"**
```bash
# Verificar Java
java -version

# Recompilar
cd src
javac -cp "../sqlite-jdbc-3.7.2.jar:../json-20231013.jar" -d ../build/classes Principal/*.java
```

### **Error: "No aparecen datos"**
```bash
# Reinicializar datos
./scripts/crear-usuario-prueba.sh
./scripts/inicializar-datos-microservicios.sh
```

---

## 📞 **Soporte y Contribución**

Para reportar bugs o solicitar nuevas funcionalidades, por favor consulta la documentación en `docs/`.

---

## 📄 **Licencia**

Este proyecto fue desarrollado como parte de un proyecto académico.

---

## 👥 **Autor**

**David Urrego**  
GitHub: [@durregou](https://github.com/durregou)  
Repositorio: [Inmotrack](https://github.com/durregou/Inmotrack)  
Proyecto de Sistema de Gestión de Arrendamientos  
2025

---

## 🔗 **Enlaces Importantes**

- 🐙 **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)
- 📚 **Documentación Completa**: [DOCUMENTACION.md](./DOCUMENTACION.md)
- 🚀 **Inicio Rápido**: [INICIO_RAPIDO.md](./INICIO_RAPIDO.md)
- 📁 **Scripts**: [scripts/README.md](./scripts/README.md)
- 📖 **Documentos**: [docs/README.md](./docs/README.md)

---

## 🎉 **¡Gracias por usar nuestro sistema!**

Si tienes preguntas, consulta la [documentación completa](./DOCUMENTACION.md) o los archivos en la carpeta `docs/`.

**⭐ ¡No olvides darle una estrella al proyecto en [GitHub](https://github.com/durregou/Inmotrack)!**
