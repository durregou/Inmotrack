# 🔧 Guía de Desarrollo

Esta documentación está dirigida a desarrolladores que desean contribuir al proyecto o configurar un entorno de desarrollo local.

## 📋 Índice

- [🛠️ Configuración del Entorno](#️-configuración-del-entorno)
- [🏃 Ejecutar en Modo Desarrollo](#-ejecutar-en-modo-desarrollo)
- [🧪 Testing](#-testing)
- [📦 Build y Deployment](#-build-y-deployment)
- [🎨 Estándares de Código](#-estándares-de-código)
- [🔧 Herramientas de Desarrollo](#-herramientas-de-desarrollo)

---

## 🛠️ Configuración del Entorno

### Prerrequisitos

#### Herramientas Obligatorias

- **Java 17** - Runtime y compilación
- **Maven 3.8+** - Gestión de dependencias
- **Docker & Docker Compose** - Contenedores
- **PostgreSQL 15** - Base de datos (opcional si usas Docker)
- **Git** - Control de versiones

#### Herramientas Recomendadas

- **IntelliJ IDEA** / **Eclipse** / **VS Code** - IDEs
- **Postman** - Testing de APIs
- **DBeaver** / **pgAdmin** - Cliente de base de datos
- **Docker Desktop** - Interface para Docker

### Instalación Java 17

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# macOS con Homebrew
brew install openjdk@17

# Windows
# Descargar desde https://adoptium.net/
```

### Instalación Maven

```bash
# Ubuntu/Debian
sudo apt install maven

# macOS con Homebrew
brew install maven

# Windows
# Descargar desde https://maven.apache.org/
```

### Verificar Instalación

```bash
java -version
# openjdk version "17.0.16" 2025-07-15

mvn -version
# Apache Maven 3.9.0

docker --version
# Docker version 24.0.5
```

---

## 🏃 Ejecutar en Modo Desarrollo

### Opción 1: Con Docker (Recomendado)

```bash
# 1. Clonar repositorio
git clone <repository-url>
cd arrendamientoProyecto/microservicios

# 2. Compilar todos los servicios
./build-all.sh

# 3. Levantar entorno completo
docker-compose up -d

# 4. Ver logs en tiempo real
docker-compose logs -f
```

### Opción 2: Desarrollo Individual

#### Base de Datos Local

```bash
# Levantar solo PostgreSQL
docker run -d \
  --name postgres-arrendamiento \
  -e POSTGRES_DB=arrendamiento_db \
  -e POSTGRES_USER=arrendamiento_user \
  -e POSTGRES_PASSWORD=arrendamiento_pass \
  -p 5432:5432 \
  postgres:15

# Verificar conexión
psql -h localhost -U arrendamiento_user -d arrendamiento_db
```

#### Ejecutar Microservicio Individual

```bash
cd microservicios/propietarios-service

# Compilar
mvn clean compile

# Ejecutar con perfil de desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# O ejecutar JAR compilado
mvn clean package -DskipTests
java -jar target/propietarios-service-1.0.0.jar
```

### Variables de Entorno para Desarrollo

Crear archivo `.env` en cada microservicio:

```bash
# propietarios-service/.env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/arrendamiento_db
SPRING_DATASOURCE_USERNAME=arrendamiento_user
SPRING_DATASOURCE_PASSWORD=arrendamiento_pass
SPRING_PROFILES_ACTIVE=dev
LOGGING_LEVEL_COM_ARRENDAMIENTO=DEBUG
```

### Perfiles de Spring

#### application-dev.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/arrendamiento_db
    username: arrendamiento_user
    password: arrendamiento_pass
  
  jpa:
    hibernate:
      ddl-auto: create-drop  # Solo para desarrollo
    show-sql: true
    format_sql: true
  
  h2:
    console:
      enabled: true  # Para pruebas rápidas

logging:
  level:
    com.arrendamiento: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

---

## 🧪 Testing

### Estructura de Tests

```
src/
├── main/java/
├── test/java/
    ├── unit/           # Tests unitarios
    ├── integration/    # Tests de integración
    └── e2e/           # Tests end-to-end
```

### Tests Unitarios

```bash
# Ejecutar tests de un servicio
cd microservicios/propietarios-service
mvn test

# Con cobertura
mvn test jacoco:report

# Ver reporte de cobertura
open target/site/jacoco/index.html
```

#### Ejemplo de Test Unitario

```java
@ExtendWith(MockitoExtension.class)
class PropietarioServiceTest {
    
    @Mock
    private PropietarioRepository repository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private PropietarioService service;
    
    @Test
    @DisplayName("Debería registrar propietario correctamente")
    void shouldRegisterPropietarioSuccessfully() {
        // Given
        PropietarioRequest request = new PropietarioRequest();
        request.setCorreo("test@email.com");
        request.setContrasena("password123");
        
        when(repository.existsByCorreo("test@email.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encrypted");
        
        // When
        PropietarioResponse response = service.registrarPropietario(request);
        
        // Then
        assertThat(response).isNotNull();
        assertThat(response.getCorreo()).isEqualTo("test@email.com");
        verify(repository).save(any(Propietario.class));
    }
}
```

### Tests de Integración

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class PropietarioControllerIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_pass");
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldCreatePropietario() {
        // Given
        PropietarioRequest request = new PropietarioRequest();
        request.setNombre("Juan");
        request.setCorreo("juan@test.com");
        
        // When
        ResponseEntity<PropietarioResponse> response = restTemplate
                .postForEntity("/api/propietarios", request, PropietarioResponse.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getNombre()).isEqualTo("Juan");
    }
}
```

### Tests End-to-End

```bash
# Con Docker Compose para E2E
cd tests/e2e
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

---

## 📦 Build y Deployment

### Build Local

```bash
# Compilar todos los microservicios
./build-all.sh

# Compilar servicio específico
cd microservicios/propietarios-service
mvn clean package -DskipTests

# Con tests
mvn clean package
```

### Build con Docker

```bash
# Build imagen individual
cd microservicios/propietarios-service
docker build -t propietarios-service:latest .

# Build todas las imágenes
docker-compose build

# Build con cache optimizado
docker-compose build --no-cache
```

### Profiles de Build

```bash
# Desarrollo
mvn clean package -P dev

# Testing
mvn clean package -P test

# Producción
mvn clean package -P prod
```

---

## 🎨 Estándares de Código

### Java Code Style

Seguimos el **Google Java Style Guide**:

```bash
# Instalar plugin en IntelliJ
# File > Settings > Plugins > Google Java Format

# Configurar checkstyle
mvn checkstyle:check

# Auto-formatear código
mvn formatter:format
```

#### Configuración de Checkstyle

```xml
<!-- pom.xml -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
        <encoding>UTF-8</encoding>
        <consoleOutput>true</consoleOutput>
        <failsOnError>true</failsOnError>
    </configuration>
</plugin>
```

### Convenciones de Naming

#### Clases y Interfaces

```java
// ✅ Correcto
public class PropietarioService { }
public interface PropietarioRepository { }

// ❌ Incorrecto  
public class propietarioService { }
public class PropietarioSRV { }
```

#### Métodos y Variables

```java
// ✅ Correcto
public PropietarioResponse registrarPropietario(PropietarioRequest request) {
    String correoNormalizado = request.getCorreo().toLowerCase();
}

// ❌ Incorrecto
public PropietarioResponse RegistrarPropietario(PropietarioRequest Request) {
    String CorreoNormalizado = Request.getCorreo().toLowerCase();
}
```

#### Constantes

```java
// ✅ Correcto
public static final String DEFAULT_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
public static final int MAX_LOGIN_ATTEMPTS = 3;

// ❌ Incorrecto
public static final String defaultPasswordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
```

### Comentarios y Documentación

```java
/**
 * Servicio para gestionar operaciones relacionadas con propietarios.
 * 
 * <p>Este servicio maneja el registro, actualización y consulta de propietarios,
 * incluyendo validaciones de negocio y encriptación de contraseñas.</p>
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 * @since 1.0
 */
@Service
public class PropietarioService {
    
    /**
     * Registra un nuevo propietario en el sistema.
     * 
     * @param request datos del propietario a registrar
     * @return información del propietario registrado
     * @throws RuntimeException si el correo ya está registrado
     */
    public PropietarioResponse registrarPropietario(PropietarioRequest request) {
        // Implementación...
    }
}
```

### Manejo de Excepciones

```java
// ✅ Correcto - Excepciones específicas
@Service
public class PropietarioService {
    
    public PropietarioResponse registrar(PropietarioRequest request) {
        if (repository.existsByCorreo(request.getCorreo())) {
            throw new PropietarioYaExisteException(
                "El correo " + request.getCorreo() + " ya está registrado"
            );
        }
        
        try {
            return convertToResponse(repository.save(convertToEntity(request)));
        } catch (DataIntegrityViolationException ex) {
            throw new PropietarioValidationException("Error de integridad de datos", ex);
        }
    }
}

// ❌ Incorrecto - Excepciones genéricas
public PropietarioResponse registrar(PropietarioRequest request) {
    try {
        // lógica...
    } catch (Exception ex) {
        throw new RuntimeException("Error", ex);
    }
}
```

---

## 🔧 Herramientas de Desarrollo

### IntelliJ IDEA Plugins Recomendados

```bash
# Plugins esenciales
- Lombok Plugin
- Maven Helper
- SonarLint
- Google Java Format
- Docker Plugin
- Database Navigator

# Configuración
File > Settings > Build Tools > Maven
✅ Import Maven projects automatically
✅ Automatically download sources
✅ Automatically download documentation
```

### VS Code Extensions

```json
{
  "recommendations": [
    "vscjava.vscode-java-pack",
    "redhat.java",
    "vscjava.vscode-spring-boot-dashboard",
    "ms-vscode.vscode-json",
    "ms-azuretools.vscode-docker"
  ]
}
```

### Scripts Útiles para Desarrollo

#### `dev-setup.sh`

```bash
#!/bin/bash
echo "🚀 Configurando entorno de desarrollo..."

# Verificar herramientas
command -v java >/dev/null 2>&1 || { echo "Java 17 requerido"; exit 1; }
command -v mvn >/dev/null 2>&1 || { echo "Maven requerido"; exit 1; }
command -v docker >/dev/null 2>&1 || { echo "Docker requerido"; exit 1; }

# Configurar base de datos
docker run -d --name postgres-dev \
  -e POSTGRES_DB=arrendamiento_db \
  -e POSTGRES_USER=arrendamiento_user \
  -e POSTGRES_PASSWORD=arrendamiento_pass \
  -p 5432:5432 \
  postgres:15

# Compilar proyectos
./build-all.sh

echo "✅ Entorno listo para desarrollo"
```

#### `run-service.sh`

```bash
#!/bin/bash
SERVICE_NAME=$1

if [ -z "$SERVICE_NAME" ]; then
    echo "Uso: ./run-service.sh <service-name>"
    echo "Servicios disponibles: administracion, propietarios, inmuebles, contratos, pagos"
    exit 1
fi

echo "🏃 Ejecutando ${SERVICE_NAME}-service..."
cd "microservicios/${SERVICE_NAME}-service"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Debugging

#### Configuración Remote Debug

```bash
# Ejecutar con debug habilitado
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
     -jar target/propietarios-service-1.0.0.jar

# En IntelliJ
Run > Edit Configurations > Remote JVM Debug
Host: localhost
Port: 5005
```

#### Logs de Desarrollo

```yaml
# application-dev.yml
logging:
  level:
    com.arrendamiento: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
  pattern:
    console: "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"
```

### Performance Monitoring

```java
// Application metrics
@RestController
public class HealthController {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @GetMapping("/actuator/metrics/custom")
    public ResponseEntity<String> customMetrics() {
        Counter.Sample sample = Timer.start(meterRegistry);
        // ... lógica ...
        sample.stop(Timer.builder("custom.timer").register(meterRegistry));
        return ResponseEntity.ok("Metrics updated");
    }
}
```

---

## 🚀 Tips para Desarrollo Eficiente

### Hot Reload

```xml
<!-- Agregar en pom.xml para hot reload -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Profiles por Desarrollador

```yaml
# application-juan.yml
server:
  port: 8092  # Puerto diferente para evitar conflictos

logging:
  level:
    com.arrendamiento: TRACE  # Logs más detallados
```

### Database Seeding

```java
@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    
    @Override
    public void run(String... args) {
        if (propietarioRepository.count() == 0) {
            // Crear datos de prueba
            Propietario propietario = new Propietario();
            propietario.setNombre("Juan Test");
            propietario.setCorreo("juan@test.com");
            propietarioRepository.save(propietario);
        }
    }
}
```

### Comandos Útiles

```bash
# Limpiar y reiniciar todo
docker-compose down -v
docker system prune -f
./build-all.sh
docker-compose up -d

# Monitorear logs específicos
docker-compose logs -f propietarios-service | grep ERROR

# Conectar a base de datos
docker exec -it postgres-db psql -U arrendamiento_user -d arrendamiento_db

# Ejecutar un solo test
mvn test -Dtest=PropietarioServiceTest#shouldRegisterPropietarioSuccessfully
```

Con esta guía tendrás todo lo necesario para un desarrollo eficiente y productivo del sistema de arrendamientos. ¡Happy coding! 🚀
