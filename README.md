# 🏪 Mercado Admin - Sistema de Gestión de Puestos y Cobros

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-Academic-2E8B57)]()

Backend robusto desarrollado con **Spring Boot** para la administración integral de un mercado. Gestiona dueños, puestos, emisión de recibos (generales e individuales), distribución automática de cuotas, registro de pagos con comprobantes numerados y reportes financieros filtrados por fecha.

---

## ✨ Funcionalidades Implementadas
- ✅ **CRUD de Dueños y Puestos** con validaciones de unicidad y relaciones `@ManyToOne`
- ✅ **Transferencia segura de puestos**: bloqueada automáticamente si existen cuotas `PENDIENTE` o `VENCIDO`
- ✅ **Emisión de Recibos**:
    - `GENERAL`: Distribuye el monto total equitativamente entre todos los puestos activos
    - `INDIVIDUAL`: Asigna el 100% del monto a un puesto específico
- ✅ **Generación automática de Cuotas** con estados (`PENDIENTE`, `PAGADO`, `VENCIDO`) y periodos mensuales
- ✅ **Registro de Pagos** con **Comprobante de Pago** correlativo seguro (bloqueo pesimista `SELECT ... FOR UPDATE` para evitar duplicados bajo concurrencia)
- ✅ **Reportes de Recaudación** por periodo con filtros opcionales `?desde=&hasta=`
- ✅ **DTOs Optimizados**: Respuestas JSON planas, sin anidamientos redundantes, alineadas a buenas prácticas REST
- ✅ **Carga automática de datos iniciales** al iniciar la aplicación (`CommandLineRunner`)

---

## 🛠️ Stack Tecnológico
| Categoría | Tecnologías |
|-----------|-------------|
| **Lenguaje** | Java 17+ (Records, Var, Switch expressions) |
| **Framework** | Spring Boot 3.x, Spring Data JPA, Spring MVC |
| **Base de Datos** | MySQL 8.0+ |
| **Patrones** | DTO, Repository, Service Layer, Transactional (`@Transactional`) |
| **Herramientas** | Maven, Lombok, Postman/Thunder Client |

---

## 🏗️ Arquitectura
El proyecto sigue una **arquitectura en capas** limpia y desacoplada:
📦 Controller → Recibe requests HTTP, valida entrada, retorna códigos de estado
📦 Service → Orquesta reglas de negocio, transacciones y generación de comprobantes
📦 Repository → Acceso a datos con Spring Data JPA (consultas derivadas, JPQL, SQL nativo)
📦 DTO → Contratos inmutables (record) para respuestas API optimizadas

---
## 🚀 Instalación y Ejecución

### 1. Requisitos
- Java 17 o superior
- Maven 3.8+
- MySQL 8.0+ corriendo localmente

### 2. Configuración de Base de Datos
Crea un schema vacío en MySQL:
```sql
CREATE DATABASE mercado_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Ajusta las credenciales en src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/mercado_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

### 3. Ejecutar