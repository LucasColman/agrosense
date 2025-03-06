# AGROSENSE

AGROSENSE es un sistema de control y monitoreo diseñado para la producción hortícola. Permite gestionar y almacenar información de dispositivos sensores y actuadores, registrando datos relevantes como la ubicación (latitud y longitud), el modelo del dispositivo, y los registros de sus mediciones y cambios de estado. La aplicación facilita la administración de tipos de sensores (con descripción y unidad de medida) y de actuadores (con descripción y clasificación: on-off o multinivel).

---

## Características

- **Gestión de Dispositivos:** Registro y administración de sensores y actuadores.
- **Información de Ubicación:** Almacena latitud y longitud de cada dispositivo.
- **Modelos y Tipos:** Registro detallado de modelos de dispositivos, tipos de sensores (descripción y unidad de medida) y tipos de actuadores (descripción y tipo: on-off, multinivel).
- **Historial de Datos:** Conservación de datos de las mediciones de sensores y de los cambios en los actuadores.
- **Interfaz Web:** Desarrollo frontend con HTML, CSS y JavaScript para una experiencia de usuario intuitiva.

---

## Tecnologías Utilizadas

- **Backend:** Java Spring Boot
- **Base de Datos:** PostgreSQL
- **Frontend:** HTML, CSS, Bootstrap, Thymeleaf y JavaScript

---

## Requisitos Previos

- **Java Development Kit (JDK) 11 o superior**
- **Maven** (para la gestión de dependencias y la construcción del proyecto)
- **PostgreSQL 10 o superior**
---

## Instalación y Ejecución

### 1. Clonar el Repositorio

```bash
git clone https://github.com/MarianoIvasiuta26/agrosense.git
cd agrosense
```

### 2. Configurar la Base de Datos PostgreSQL
**Crear la Base de Datos**:
Inicia sesión en PostgreSQL y crea una base de datos (por ejemplo, agrosense_db).

**Configurar Credenciales**:
Configura un usuario y contraseña en PostgreSQL.

**Actualizar Configuración en Spring Boot**:
Modifica el archivo src/main/resources/application.properties (o application.yml) con los datos de conexión:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/agrosense_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### 3. Construir el Proyecto con Maven
```bash
mvn spring-boot:run
```
La aplicación se iniciará en http://localhost:8080.

---
## Integrantes del Equipo
- Belen Anton  – Desarrollador FrontEnd 
- Colman Lucas – Desarrollador BackEnd 
- Ivasiuta Mariano – Desarrollador BackEnd 
- Martinez Alejandro – Desarrollador FrontEnd 

### Contacto
- belenantonok@gmail.com
- lukasscolman@gmail.com
- ivasiuta.mariano@gmail.com
- martinezalejandrouni10@gmail.com





