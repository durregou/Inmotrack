# 🤝 Guía de Contribución

¡Gracias por tu interés en contribuir al Sistema de Gestión de Arrendamientos! Esta guía te ayudará a participar efectivamente en el desarrollo del proyecto.

## 📋 Tabla de Contenido

- [🎯 Cómo Contribuir](#-cómo-contribuir)
- [🐛 Reportar Bugs](#-reportar-bugs)
- [💡 Sugerir Nuevas Funcionalidades](#-sugerir-nuevas-funcionalidades)
- [🔧 Configurar Entorno de Desarrollo](#-configurar-entorno-de-desarrollo)
- [📝 Estándares de Código](#-estándares-de-código)
- [🔀 Proceso de Pull Request](#-proceso-de-pull-request)
- [📚 Convenciones](#-convenciones)
- [👥 Comunidad](#-comunidad)

---

## 🎯 Cómo Contribuir

Hay muchas formas de contribuir a este proyecto:

### 💻 Código
- Implementar nuevas funcionalidades
- Corregir bugs existentes
- Mejorar rendimiento
- Refactorizar código
- Escribir tests

### 📖 Documentación
- Mejorar documentación existente
- Escribir tutoriales
- Traducir documentación
- Crear ejemplos de uso

### 🧪 Testing
- Escribir tests unitarios
- Crear tests de integración
- Realizar pruebas manuales
- Reportar bugs encontrados

### 🎨 Diseño
- Mejorar interfaces de usuario
- Diseñar nuevos componentes
- Crear diagramas y mockups
- Optimizar experiencia de usuario

---

## 🐛 Reportar Bugs

### Antes de Reportar

1. **Busca issues existentes** para evitar duplicados
2. **Verifica en la última versión** del proyecto
3. **Reproduce el problema** consistentemente

### Cómo Reportar un Bug

Crea un nuevo issue usando la plantilla de bug report:

```markdown
## 🐛 Descripción del Bug
Descripción clara y concisa del problema.

## 🔄 Pasos para Reproducir
1. Ve a '...'
2. Haz clic en '...'
3. Desplázate hasta '...'
4. Ver error

## ✅ Comportamiento Esperado
Descripción de lo que esperabas que ocurriera.

## 📱 Comportamiento Actual
Descripción de lo que realmente ocurre.

## 🖥️ Entorno
- OS: [ej. macOS 14.0]
- Java Version: [ej. 17.0.8]
- Docker Version: [ej. 24.0.5]
- Browser: [ej. Chrome 120.0]

## 📄 Logs
```
Incluir logs relevantes aquí
```

## 📷 Screenshots
Si aplica, agregar screenshots para explicar el problema.

## ℹ️ Información Adicional
Cualquier otro contexto sobre el problema.
```

---

## 💡 Sugerir Nuevas Funcionalidades

### Antes de Sugerir

1. **Revisa el roadmap** en el README
2. **Busca sugerencias existentes**
3. **Considera el scope** del proyecto

### Plantilla de Feature Request

```markdown
## ✨ Resumen de la Funcionalidad
Descripción breve de la funcionalidad sugerida.

## 🎯 Problema que Resuelve
Explica qué problema soluciona esta funcionalidad.

## 💡 Solución Propuesta
Descripción detallada de cómo implementarías la solución.

## 🔄 Alternativas Consideradas
Otras soluciones que consideraste.

## 📊 Criterios de Aceptación
- [ ] Criterio 1
- [ ] Criterio 2
- [ ] Criterio 3

## 🖼️ Mockups/Diagramas
Si aplica, incluir diseños visuales.
```

---

## 🔧 Configurar Entorno de Desarrollo

### 1. Fork y Clone

```bash
# Fork el repositorio en GitHub
# Luego clona tu fork
git clone https://github.com/tu-usuario/arrendamientoProyecto.git
cd arrendamientoProyecto

# Agrega el repositorio original como upstream
git remote add upstream https://github.com/original-repo/arrendamientoProyecto.git
```

### 2. Configurar Entorno

```bash
# Verificar herramientas
java -version   # Debe ser Java 17+
mvn -version   # Debe ser Maven 3.8+
docker --version # Para contenedores

# Configurar entorno de desarrollo
cd microservicios
chmod +x build-all.sh
./build-all.sh

# Levantar servicios
docker-compose up -d
```

### 3. Verificar Setup

```bash
# Verificar que todos los servicios estén funcionando
curl http://localhost:8082/api/propietarios
curl http://localhost:8083/api/inmuebles
```

---

## 📝 Estándares de Código

### Java Style Guide

Seguimos el **Google Java Style Guide** con algunas adaptaciones:

#### Formato de Código

```java
// ✅ Correcto
public class PropietarioService {
    
    private final PropietarioRepository repository;
    
    public PropietarioService(PropietarioRepository repository) {
        this.repository = repository;
    }
    
    public PropietarioResponse crear(PropietarioRequest request) {
        // Validaciones
        if (repository.existsByCorreo(request.getCorreo())) {
            throw new PropietarioYaExisteException("Correo ya registrado");
        }
        
        // Lógica de negocio
        Propietario propietario = convertirAEntidad(request);
        Propietario guardado = repository.save(propietario);
        
        return convertirAResponse(guardado);
    }
}
```

#### Naming Conventions

- **Clases**: PascalCase (`PropietarioService`)
- **Métodos**: camelCase (`registrarPropietario`)
- **Variables**: camelCase (`nombreCompleto`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_INTENTOS_LOGIN`)
- **Packages**: lowercase (`com.arrendamiento.propietarios`)

#### Documentación

```java
/**
 * Servicio para gestionar operaciones de propietarios.
 * 
 * <p>Este servicio maneja el CRUD completo de propietarios, incluyendo
 * validaciones de negocio, encriptación de contraseñas y comunicación
 * con otros microservicios.</p>
 * 
 * @author Tu Nombre
 * @since 1.0.0
 */
@Service
public class PropietarioService {
    
    /**
     * Registra un nuevo propietario en el sistema.
     * 
     * @param request datos del propietario a registrar, no debe ser null
     * @return respuesta con información del propietario creado
     * @throws PropietarioYaExisteException si el correo ya está registrado
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public PropietarioResponse registrar(PropietarioRequest request) {
        // implementación
    }
}
```

### Testing Standards

#### Tests Unitarios

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("PropietarioService - Tests Unitarios")
class PropietarioServiceTest {
    
    @Mock
    private PropietarioRepository repository;
    
    @InjectMocks
    private PropietarioService service;
    
    @Test
    @DisplayName("Debe crear propietario cuando los datos son válidos")
    void debeCrearPropietarioCuandoDatosSonValidos() {
        // Given
        PropietarioRequest request = PropietarioRequestTestBuilder
            .unPropietarioRequest()
            .conCorreo("test@example.com")
            .build();
        
        when(repository.existsByCorreo("test@example.com")).thenReturn(false);
        
        // When
        PropietarioResponse response = service.registrar(request);
        
        // Then
        assertThat(response)
            .isNotNull()
            .extracting("correo")
            .isEqualTo("test@example.com");
            
        verify(repository).save(any(Propietario.class));
    }
}
```

#### Tests de Integración

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class PropietarioControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void debeCrearPropietarioViaAPI() {
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

---

## 🔀 Proceso de Pull Request

### 1. Crear Branch

```bash
# Actualizar main
git checkout main
git pull upstream main

# Crear branch descriptiva
git checkout -b feature/nombre-funcionalidad
# o
git checkout -b bugfix/descripcion-bug
# o  
git checkout -b docs/actualizar-readme
```

### 2. Desarrollar

```bash
# Hacer commits frecuentes con mensajes descriptivos
git add .
git commit -m "feat(propietarios): agregar validación de correo único"

# Seguir convención de commits convencionales
git commit -m "fix(contratos): corregir validación de fechas"
git commit -m "docs(api): actualizar documentación de endpoints"
```

### 3. Testing

```bash
# Ejecutar tests antes de push
mvn test

# Verificar coverage
mvn test jacoco:report

# Ejecutar tests de integración
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

### 4. Push y Pull Request

```bash
# Push del branch
git push origin feature/nombre-funcionalidad

# Crear Pull Request en GitHub
# Usar la plantilla de PR
```

### Plantilla de Pull Request

```markdown
## 📝 Descripción
Descripción breve de los cambios realizados.

## 🎯 Tipo de Cambio
- [ ] Bug fix (cambio que corrige un issue)
- [ ] Nueva funcionalidad (cambio que agrega funcionalidad)
- [ ] Breaking change (cambio que rompe compatibilidad)
- [ ] Documentación
- [ ] Refactoring
- [ ] Test

## 🧪 Testing
- [ ] He agregado tests que cubren mis cambios
- [ ] Todos los tests nuevos y existentes pasan
- [ ] He actualizado la documentación si es necesario

## ✅ Checklist
- [ ] Mi código sigue las convenciones del proyecto
- [ ] He realizado self-review de mi código
- [ ] He comentado mi código, especialmente en áreas complejas
- [ ] He actualizado la documentación
- [ ] Mis cambios no generan nuevas warnings
- [ ] He agregado tests que demuestran que el fix/feature funciona
- [ ] Los tests unitarios e integración pasan localmente

## 🔗 Issues Relacionados
Fixes #123
Relates to #456

## 📷 Screenshots
Si aplica, incluir screenshots o GIFs.
```

---

## 📚 Convenciones

### Git Commit Messages

Usamos **Conventional Commits**:

```bash
# Formato
<tipo>(<scope>): <descripción>

# Ejemplos
feat(propietarios): agregar endpoint de búsqueda por ciudad
fix(contratos): corregir validación de fechas solapadas
docs(api): actualizar documentación de autenticación
test(pagos): agregar tests unitarios para PagoService
refactor(inmuebles): extraer lógica de validación a clase separada
```

#### Tipos de Commit

- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Documentación
- `style`: Formato, falta punto y coma, etc.
- `refactor`: Refactoring de código
- `test`: Agregar tests
- `chore`: Mantenimiento

#### Scopes

- `admin`: Microservicio de administración
- `propietarios`: Microservicio de propietarios
- `inmuebles`: Microservicio de inmuebles
- `contratos`: Microservicio de contratos
- `pagos`: Microservicio de pagos
- `docker`: Configuración Docker
- `api`: Documentación de APIs
- `db`: Base de datos

### Branch Naming

```bash
# Funcionalidades
feature/descripcion-corta
feature/agregar-filtros-inmuebles
feature/notificaciones-email

# Bug fixes
bugfix/descripcion-problema
bugfix/validacion-fechas-contratos
bugfix/conexion-base-datos

# Documentación
docs/descripcion-cambio
docs/actualizar-api-propietarios
docs/guia-instalacion

# Refactoring
refactor/descripcion-cambio
refactor/extraer-validaciones-comunes
refactor/optimizar-consultas-db
```

---

## 👥 Comunidad

### Comunicación

- **GitHub Issues**: Para bugs y feature requests
- **GitHub Discussions**: Para preguntas y discusiones generales
- **Pull Requests**: Para revisiones de código
- **Email**: equipo-dev@empresa.com para temas sensibles

### Código de Conducta

Este proyecto adhiere al [Contributor Covenant](https://www.contributor-covenant.org/es/version/2/0/code_of_conduct/). Al participar, se espera que mantengas este código.

#### Resumen

- **Sé respetuoso**: Trata a todos con respeto y profesionalismo
- **Sé colaborativo**: Ayuda a otros y acepta ayuda constructiva
- **Sé inclusivo**: Todos son bienvenidos independientemente de su experiencia
- **Sé paciente**: Todos estamos aprendiendo

### Reconocimientos

Los contribuidores serán reconocidos en:
- README del proyecto
- Changelog de releases
- Hall of Fame en la documentación

---

## 🎉 ¡Gracias por Contribuir!

Tu contribución hace que este proyecto sea mejor para todos. No importa si es tu primer commit o si eres un desarrollador experimentado, tu ayuda es valiosa.

### Primeros Pasos Recomendados

1. **Lee toda la documentación** en el directorio `docs/`
2. **Configura tu entorno** siguiendo la guía de desarrollo
3. **Busca issues etiquetados** con `good first issue` o `help wanted`
4. **Pregunta si necesitas ayuda** - estamos aquí para apoyarte

### Recursos Adicionales

- [Documentación de APIs](docs/API.md)
- [Guía de Arquitectura](docs/ARCHITECTURE.md)
- [Guía de Desarrollo](docs/DEVELOPMENT.md)
- [Colección de Postman](microservicios/postman-config/)

---

**¡Happy Coding! 🚀**
