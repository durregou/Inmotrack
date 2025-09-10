# 🏢 Sistema de Gestión de Arrendamientos

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)](https://docs.docker.com/compose/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Un sistema completo de gestión de arrendamientos desarrollado con **arquitectura de microservicios** usando Spring Boot, diseñado para administrar propietarios, inmuebles, contratos y pagos de manera eficiente y escalable.

## 📋 Tabla de Contenido

- [🎯 Características](#-características)
- [🏗️ Arquitectura](#️-arquitectura)
- [⚡ Inicio Rápido](#-inicio-rápido)
- [🛠️ Instalación](#️-instalación)
- [📚 Documentación](#-documentación)
- [🧪 Testing](#-testing)
- [🚀 Despliegue](#-despliegue)
- [🤝 Contribución](#-contribución)

## 🎯 Características

### ✨ Funcionalidades Principales

- **Gestión de Propietarios**: Registro y administración de propietarios de inmuebles
- **Catálogo de Inmuebles**: Gestión completa de propiedades con características detalladas
- **Contratos de Arrendamiento**: Creación y seguimiento de contratos con validaciones automáticas
- **Sistema de Pagos**: Registro y seguimiento de pagos con múltiples estados
- **Autenticación Segura**: Sistema de login con JWT para administradores
- **APIs RESTful**: Endpoints bien documentados siguiendo estándares REST

### 🔧 Características Técnicas

- **Arquitectura de Microservicios**: 5 servicios independientes y escalables
- **Base de Datos**: PostgreSQL con esquema optimizado
- **Contenedores**: Despliegue completo con Docker Compose
- **Validaciones**: Validación robusta de datos en todos los endpoints
- **Comunicación Inter-servicios**: WebClient para llamadas entre microservicios
- **Logging**: Sistema de logs detallado para debugging y monitoreo

## 🏗️ Arquitectura

### Diagrama de Microservicios

```
┌─────────────────────────────────────────────────────────────┐
│                    Cliente (Frontend/Postman)               │
└─────────────────────┬───────────────────────────────────────┘
                      │
              ┌───────▼───────┐
              │   API Gateway │  (Futuro)
              │   (Opcional)  │
              └───────┬───────┘
                      │
        ┌─────────────┼─────────────┐
        │             │             │
┌───────▼──┐ ┌────────▼──┐ ┌────────▼──┐
│Administr.│ │Propietar. │ │ Inmuebles │
│Service   │ │Service    │ │ Service   │
│:8081     │ │:8082      │ │:8083      │
└──────────┘ └───────────┘ └───────────┘
                      │
              ┌───────┼───────┐
              │       │       │
      ┌───────▼──┐ ┌──▼───────▼──┐
      │Contratos │ │    Pagos    │
      │Service   │ │   Service   │
      │:8084     │ │   :8085     │
      └──────────┘ └─────────────┘
                      │
              ┌───────▼───────┐
              │  PostgreSQL   │
              │     :5432     │
              └───────────────┘
```

### Microservicios

| Servicio | Puerto | Responsabilidad | Base de Datos |
|----------|--------|-----------------|---------------|
| **Administración** | 8081 | Autenticación de administradores | `administradores` |
| **Propietarios** | 8082 | Gestión de propietarios | `propietarios` |
| **Inmuebles** | 8083 | Catálogo de propiedades | `inmuebles` |
| **Contratos** | 8084 | Contratos de arrendamiento | `contratos` |
| **Pagos** | 8085 | Sistema de pagos | `pagos` |

## ⚡ Inicio Rápido

### Prerrequisitos

- [Docker](https://www.docker.com/get-started) y Docker Compose
- [Java 17](https://openjdk.java.net/projects/jdk/17/) (para desarrollo)
- [Maven 3.6+](https://maven.apache.org/) (para desarrollo)

### 🚀 Ejecutar en 3 pasos

```bash
# 1. Clonar el repositorio
git clone <repository-url>
cd arrendamientoProyecto

# 2. Compilar microservicios
cd microservicios
./build-all.sh

# 3. Levantar todos los servicios
docker-compose up -d
```

### ✅ Verificar instalación

```bash
# Verificar que todos los servicios están ejecutándose
docker-compose ps

# Probar endpoint de salud
curl http://localhost:8082/api/propietarios
curl http://localhost:8083/api/inmuebles
```

## 🛠️ Instalación

### Desarrollo Local

```bash
# 1. Instalar dependencias
cd microservicios/administracion-service
mvn clean install

# 2. Configurar base de datos
docker run -d \
  --name postgres-arrendamiento \
  -e POSTGRES_DB=arrendamiento_db \
  -e POSTGRES_USER=arrendamiento_user \
  -e POSTGRES_PASSWORD=arrendamiento_pass \
  -p 5432:5432 \
  postgres:15

# 3. Ejecutar microservicio individual
mvn spring-boot:run
```

### Configuración de Base de Datos

El sistema creará automáticamente las siguientes tablas:

- `administradores` - Datos de administradores
- `propietarios` - Información de propietarios
- `inmuebles` - Catálogo de propiedades
- `contratos` - Contratos de arrendamiento
- `pagos` - Registro de transacciones

### Variables de Entorno

Crear archivo `.env` basado en `.env.example`:

```bash
cp .env.example .env
# Editar las variables según el entorno
```

## 📚 Documentación

### 📖 Documentación Disponible

- [📋 **API Documentation**](docs/API.md) - Especificación completa de endpoints
- [🏛️ **Architecture Guide**](docs/ARCHITECTURE.md) - Guía detallada de arquitectura
- [💾 **Database Schema**](docs/DATABASE.md) - Esquema y relaciones de BD
- [🐳 **Docker Guide**](docs/DOCKER.md) - Guía de contenedores
- [🔧 **Development Setup**](docs/DEVELOPMENT.md) - Configuración para desarrolladores
- [📮 **Postman Collection**](microservicios/postman-config/) - Colección de pruebas

### 🔗 APIs Principales

| Endpoint | Método | Descripción |
|----------|--------|-------------|
| `/api/admin/login` | POST | Autenticación de administrador |
| `/api/propietarios` | POST, GET | Gestión de propietarios |
| `/api/inmuebles` | POST, GET | Gestión de inmuebles |
| `/api/contratos` | POST, GET | Gestión de contratos |
| `/api/pagos` | POST, GET | Gestión de pagos |

Ver [documentación completa de APIs](docs/API.md) para detalles.

## 🧪 Testing

### Postman Collection

```bash
# Importar colección pre-configurada
cd microservicios/postman-config/
# Importar: Arrendamiento_Microservicios.postman_collection.json
# Importar: Arrendamiento_Local.postman_environment.json
```

### Tests Unitarios

```bash
# Ejecutar tests de un microservicio
cd microservicios/propietarios-service
mvn test

# Ejecutar todos los tests
./run-all-tests.sh
```

### Tests de Integración

```bash
# Tests end-to-end
cd tests/
npm install
npm run test:e2e
```

## 🚀 Despliegue

### Producción con Docker

```bash
# Configurar variables de producción
cp .env.example .env.production

# Desplegar en modo producción
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

### Kubernetes

```bash
# Desplegar en cluster K8s
kubectl apply -f k8s/
```

### CI/CD Pipeline

El proyecto incluye configuración para:
- **GitHub Actions** - CI/CD automatizado
- **Docker Hub** - Registro de imágenes
- **Health Checks** - Monitoreo de servicios

## 📊 Monitoreo y Logs

### Ver Logs

```bash
# Logs de todos los servicios
docker-compose logs -f

# Logs de un servicio específico
docker-compose logs -f propietarios-service
```

### Métricas

- **Health Endpoints**: `/actuator/health` en cada servicio
- **Prometheus**: Métricas exportadas para monitoreo
- **Grafana**: Dashboards pre-configurados

## 🔒 Seguridad

### Características de Seguridad

- **JWT Authentication**: Tokens seguros para administradores
- **Password Hashing**: BCrypt para encriptación de contraseñas
- **Input Validation**: Validación robusta en todos los endpoints
- **CORS Configuration**: Configuración CORS para frontend
- **Environment Variables**: Secrets en variables de entorno

### Configuración de Seguridad

```yaml
# application.yml
spring:
  security:
    jwt:
      secret: ${JWT_SECRET}
      expiration: 86400
```

## 🤝 Contribución

### Cómo Contribuir

1. **Fork** el proyecto
2. Crear **feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit** cambios (`git commit -m 'Add AmazingFeature'`)
4. **Push** al branch (`git push origin feature/AmazingFeature`)
5. Abrir **Pull Request**

### Estándares de Código

- **Java Code Style**: Google Java Style Guide
- **Commit Messages**: Conventional Commits
- **Documentation**: JavaDoc para todas las clases públicas
- **Testing**: Cobertura mínima del 80%

### Estructura de Branches

```
main/
├── develop/
├── feature/nueva-funcionalidad
├── hotfix/bug-critico
└── release/v1.2.0
```

## 📈 Roadmap

### Version 1.1.0 (Q1 2024)
- [ ] API Gateway con Spring Cloud Gateway
- [ ] Service Discovery con Eureka
- [ ] Circuit Breaker con Resilience4j
- [ ] Distributed Tracing con Zipkin

### Version 1.2.0 (Q2 2024)
- [ ] Frontend React/Angular
- [ ] Notificaciones en tiempo real
- [ ] Reportes y Analytics
- [ ] Multi-tenancy

### Version 2.0.0 (Q3 2024)
- [ ] Event Sourcing
- [ ] CQRS Pattern
- [ ] Kubernetes Deployment
- [ ] GraphQL APIs

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver [LICENSE](LICENSE) para detalles.

## 👥 Equipo

- **Arquitecto de Software**: [@tu-usuario](https://github.com/tu-usuario)
- **Desarrollador Backend**: [@contribuidor1](https://github.com/contribuidor1)
- **DevOps Engineer**: [@contribuidor2](https://github.com/contribuidor2)

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/tu-repo/issues)
- **Discussions**: [GitHub Discussions](https://github.com/tu-repo/discussions)
- **Email**: soporte@tuempresa.com

---

<div align="center">

**⭐ ¡Dale una estrella si te gusta este proyecto! ⭐**

![Made with ❤️](https://img.shields.io/badge/Made%20with-❤️-red.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)

</div>
