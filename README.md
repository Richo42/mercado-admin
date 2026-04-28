# 🏪 Mercado Admin - Sistema de Gestión de Puestos y Cobros

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21%2B-ED8B00?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-Academic-2E8B57)]()

Backend robusto desarrollado con **Spring Boot** para la administración integral de un mercado. Gestiona dueños, puestos, emisión de recibos (generales e individuales), distribución automática de cuotas, registro de pagos con comprobantes numerados y reportes financieros filtrados por fecha.
Backend robusto desarrollado con **Spring Boot 3.2.4** para la administración integral de un mercado. Gestiona dueños, puestos, emisión de recibos (generales e individuales), distribución automática de cuotas, registro de pagos con comprobantes numerados y reportes financieros filtrados por fecha.

---

## ✨ Funcionalidades Implementadas
- ✅ **CRUD de Dueños y Puestos** con validaciones de unicidad y relaciones `@ManyToOne`
- ✅ **Transferencia segura de puestos**: bloqueada automáticamente si existen cuotas `PENDIENTE` o `VENCIDO`

### 📋 Gestión de Entidades
- ✅ **CRUD de Dueños**: Alta, baja, modificación y consulta con validaciones de unicidad (DNI/CUIL)
- ✅ **CRUD de Puestos**: Identificados por número/pasillo, con relación `@ManyToOne` hacia Dueños
- ✅ **Transferencia segura de puestos**: Bloqueada automáticamente si existen cuotas `PENDIENTE` o `VENCIDO`

### 💰 Sistema de Cobranzas
- ✅ **Emisión de Recibos**:
    - `GENERAL`: Distribuye el monto total equitativamente entre todos los puestos activos
    - `INDIVIDUAL`: Asigna el 100% del monto a un puesto específico
- ✅ **Generación automática de Cuotas** con estados (`PENDIENTE`, `PAGADO`, `VENCIDO`) y periodos mensuales
- ✅ **Registro de Pagos** con **Comprobante de Pago** correlativo seguro (bloqueo pesimista `SELECT ... FOR UPDATE` para evitar duplicados bajo concurrencia)
- ✅ **Registro de Pagos** con **Comprobante de Pago** correlativo seguro
    - Implementación de bloqueo pesimista (`SELECT ... FOR UPDATE`) para evitar duplicados bajo concurrencia
    - Generación de detalles individuales por cuota pagada

### 📊 Reportes y Consultas
- ✅ **Reportes de Recaudación** por periodo con filtros opcionales `?desde=&hasta=`
- ✅ **Consulta de Cuotas** por puesto, estado o período
- ✅ **Historial de Comprobantes** con desglose detallado

### 🔐 Seguridad
- ✅ **Autenticación JWT** con configuración de seguridad personalizada
- ✅ **Endpoints protegidos** para operaciones críticas
- ✅ **Gestión de Usuarios** con roles y permisos

### 🚀 Características Técnicas
- ✅ **DTOs Optimizados**: Respuestas JSON planas, sin anidamientos redundantes, alineadas a buenas prácticas REST
- ✅ **Carga automática de datos iniciales** al iniciar la aplicación (`CommandLineRunner`)
- ✅ **Manejo transaccional** con `@Transactional` para consistencia de datos
- ✅ **Tests de integración** configurados con base de datos H2 en memoria

---

## 🛠️ Stack Tecnológico

| Categoría | Tecnologías |
|-----------|-------------|
| **Lenguaje** | Java 17+ (Records, Var, Switch expressions) |
| **Framework** | Spring Boot 3.x, Spring Data JPA, Spring MVC |
| **Base de Datos** | MySQL 8.0+ |
| **Patrones** | DTO, Repository, Service Layer, Transactional (`@Transactional`) |
| **Herramientas** | Maven, Lombok, Postman/Thunder Client |
| **Lenguaje** | Java 21+ (Records, Var, Switch expressions, Pattern matching) |
| **Framework** | Spring Boot 3.2.4, Spring Data JPA, Spring MVC, Spring Security |
| **Base de Datos** | MySQL 8.0+ (Producción), H2 (Tests) |
| **Seguridad** | JWT (JSON Web Tokens) |
| **Patrones** | DTO, Repository, Service Layer, Controller, Transactional |
| **Herramientas** | Maven 3.8+, Lombok, Postman/Thunder Client |
| **Testing** | JUnit 5, Spring Boot Test, H2 Database |

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas** limpia y desacoplada:
📦 Controller → Recibe requests HTTP, valida entrada, retorna códigos de estado
📦 Service → Orquesta reglas de negocio, transacciones y generación de comprobantes
📦 Repository → Acceso a datos con Spring Data JPA (consultas derivadas, JPQL, SQL nativo)
📦 DTO → Contratos inmutables (record) para respuestas API optimizadas

```
📦 Controller Layer (7 controladores)
   └── AuthController, DuenoController, PuestoController, ReciboController, 
       CuotaController, PagoController, ReporteController

📦 Service Layer (5 servicios)
   └── TransferenciaService, CuotaService, PagoService, ReporteService, ReciboService

📦 Repository Layer (6 repositorios)
   └── DuenoRepository, PuestoRepository, ReciboRepository, CuotaRepository, 
       CorrelativoRepository, UsuarioRepository

📦 Entity Layer (6 entidades)
   └── Dueno, Puesto, Recibo, Cuota, CorrelativoComprobante, Usuario

📦 DTO Layer (8 DTOs inmutables)
   └── DuenoDTO, PuestoDTO, ReciboDTO, CuotaDTO, PagoRequestDTO, 
       ComprobantePagoDTO, DetalleComprobanteDTO, ReporteRecaudacionDTO

📦 Security Layer
   └── JWTAuthorizationFilter, WebSecurityConfig, JWTAuthenticationConfig, Constants

📦 Config Layer
   └── DataInitializer (carga de datos demo)
```

**Total**: 38 clases Java organizadas por responsabilidad

---

## 📁 Estructura del Código

### Controladores (Controllers)
| Controlador | Responsabilidad |
|------------|-----------------|
| `AuthController` | Autenticación y generación de tokens JWT |
| `DuenoController` | CRUD de dueños del mercado |
| `PuestoController` | CRUD de puestos y transferencias |
| `ReciboController` | Emisión de recibos generales e individuales |
| `CuotaController` | Consulta y gestión de cuotas |
| `PagoController` | Registro de pagos y generación de comprobantes |
| `ReporteController` | Reportes de recaudación filtrados por fecha |

### Servicios (Services)
| Servicio | Responsabilidad |
|----------|-----------------|
| `TransferenciaService` | Lógica de transferencia de puestos con validaciones |
| `CuotaService` | Generación y gestión automática de cuotas mensuales |
| `PagoService` | Proceso de pago con bloqueo pesimista de correlativos |
| `ReporteService` | Cálculo de recaudación por períodos |
| `ReciboService` | Creación de recibos con distribución de montos |

### Repositorios (Repositories)
| Repositorio | Entidad | Consultas Personalizadas |
|------------|---------|-------------------------|
| `DuenoRepository` | Dueno | Búsqueda por DNI/CUIL |
| `PuestoRepository` | Puesto | Validación de transferencias |
| `ReciboRepository` | Recibo | Búsqueda por tipo/fecha |
| `CuotaRepository` | Cuota | Filtrado por estado/período |
| `CorrelativoRepository` | CorrelativoComprobante | Bloqueo pesimista FOR UPDATE |
| `UsuarioRepository` | Usuario | Autenticación por username |

### Entidades (Entities)
| Entidad | Descripción |
|---------|-------------|
| `Dueno` | Propietarios del mercado con DNI/CUIL único |
| `Puesto` | Locales comerciales identificados por número/pasillo |
| `Recibo` | Comprobantes de emisión de cuotas (GENERAL/INDIVIDUAL) |
| `Cuota` | Cuotas mensuales con estado y monto |
| `CorrelativoComprobante` | Contador seguro de comprobantes de pago |
| `Usuario` | Usuarios del sistema con credenciales para autenticación |

### DTOs (Data Transfer Objects)
| DTO | Uso |
|-----|-----|
| `DuenoDTO` | Respuesta de dueños sin datos sensibles |
| `PuestoDTO` | Respuesta de puestos con información del dueño |
| `ReciboDTO` | Detalle de recibos emitidos |
| `CuotaDTO` | Información de cuotas para consultas |
| `PagoRequestDTO` | Request para registrar pagos |
| `ComprobantePagoDTO` | Respuesta con comprobante generado |
| `DetalleComprobanteDTO` | Detalle individual de cuotas pagadas |
| `ReporteRecaudacionDTO` | Reporte financiero de recaudación |

---

## 🚀 Instalación y Ejecución

### 1. Requisitos
- Java 17 o superior
- Maven 3.8+
- MySQL 8.0+ corriendo localmente
### 1. Requisitos Previos
- **Java 21+** (Recomendado: JDK 21 LTS)
- **Maven 3.8+**
- **MySQL 8.0+** corriendo localmente o en servidor remoto

### 2. Configuración de Base de Datos

Crea un schema vacío en MySQL:

```sql
CREATE DATABASE mercado_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Ajusta las credenciales en src/main/resources/application.properties:
Ajusta las credenciales en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mercado_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
./mvnw clean package

# Ejecutar la aplicación
./mvnw spring-boot:run

# O ejecutar el JAR generado
java -jar target/admin-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en `http://localhost:8080`

### 4. Datos Iniciales

Al iniciar, la aplicación carga automáticamente datos de prueba mediante `DataInitializer`:
- 3 dueños de ejemplo
- 5 puestos asignados
- 1 usuario administrador para autenticación

---

## 📡 Endpoints Principales

### Autenticación
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Autenticar usuario y obtener token JWT |

### Dueños
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/duenos` | Listar todos los dueños |
| GET | `/api/duenos/{id}` | Obtener dueño por ID |
| POST | `/api/duenos` | Crear nuevo dueño |
| PUT | `/api/duenos/{id}` | Actualizar dueño |
| DELETE | `/api/duenos/{id}` | Eliminar dueño |

### Puestos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/puestos` | Listar todos los puestos |
| GET | `/api/puestos/{id}` | Obtener puesto por ID |
| POST | `/api/puestos` | Crear nuevo puesto |
| PUT | `/api/puestos/{id}` | Actualizar puesto |
| POST | `/api/puestos/{id}/transferir` | Transferir puesto a otro dueño |

### Recibos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/recibos/general` | Emitir recibo general para todos los puestos |
| POST | `/api/recibos/individual` | Emitir recibo individual para un puesto |

### Cuotas
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cuotas` | Listar cuotas con filtros opcionales |
| GET | `/api/cuotas/puesto/{puestoId}` | Obtener cuotas de un puesto |

### Pagos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/pagos` | Registrar pago y generar comprobante |

### Reportes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/reportes/recaudacion` | Reporte de recaudación por período |
| GET | `/api/reportes/recaudacion?desde=YYYY-MM-DD&hasta=YYYY-MM-DD` | Reporte filtrado por fechas |

---

## 🔒 Consideraciones de Seguridad

- **JWT Token**: Todos los endpoints críticos requieren autenticación vía header `Authorization: Bearer <token>`
- **Bloqueo Pesimista**: El registro de pagos usa `SELECT ... FOR UPDATE` para garantizar unicidad de comprobantes bajo concurrencia
- **Validaciones**: Todas las entradas son validadas antes de procesarse
- **Transacciones**: Operaciones críticas están protegidas con `@Transactional`

---

## 🧪 Testing

El proyecto incluye tests de integración configurados con **H2 Database**:

```bash
# Ejecutar todos los tests
./mvnw test

# Ejecutar con reporte detallado
./mvnw test -Dsurefire.reportFormat=plain
```

---

## 🎯 Mejoras Futuras (Roadmap)

- [ ] Agregar más tests unitarios y de integración
- [ ] Documentar API con OpenAPI/Swagger
- [ ] Implementar manejo global de excepciones (@ControllerAdvice)
- [ ] Externalizar configuración sensible (variables de entorno)
- [ ] Agregar endpoints para baja lógica (soft delete)
- [ ] Implementar auditoría de operaciones (quién hizo qué y cuándo)
- [ ] Agregar exportación de reportes a PDF/Excel
- [ ] Implementar notificaciones por email para cuotas vencidas

---

## 📝 Notas Importantes

- **Java 21**: El proyecto está configurado para Java 21. Asegúrate de tener instalado el JDK 21 y configurado en tu IDE.
- **Concurrencia**: El sistema maneja correctamente múltiples solicitudes simultáneas gracias al bloqueo pesimista en la generación de comprobantes.
- **Datos Demo**: Al iniciar en modo desarrollo, se cargan datos de prueba automáticamente.

---

## 🤝 Contribuciones

Para contribuir al proyecto, por favor lee el archivo [CONTRIBUTING.md](CONTRIBUTING.md) para conocer los lineamientos.

---

## 📄 Licencia

Este proyecto es de uso académico. Ver archivo de licencia para más detalles.

---

## 👨‍💻 Autor

Desarrollado como proyecto académico para la gestión integral de mercados municipales.

---