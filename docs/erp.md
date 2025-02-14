# Especificación de requisitos de software
## Integrantes
* Colman Hernan Lucas
* Ivasiuta Mariano
* Martinez Alejandro
* Anton Belen

---
## **Enunciado del problema**
Una empresa dedicada a la producción agrícola quiere mantener información sobre el sistema de control de su producción hortícola,  se debe considerar que se llevan datos de los dispositivos sensores y actuadores, entre los datos que se llevan son su ubicación (latitud y longitud) y modelo de dispositivo. Se mantiene información de tipos de sensores y actuadores, de los tipos de sensores se lleva su descripción y unidad de medida, y de los tipos de actuadores se lleva su descripción y tipo (on-off y multinivel). Aparte de ello se debe llevar por cada dispositivo los datos obtenidos de los sensores y los cambios de los actuadores.

---

## **Clientes potenciales**
Los clientes potenciales del sistema son los siguientes:
* Agricultor
* Ingeniero agrónomo

---
## **Solución propuesta**
La solución propuesta es el desarrollo de un sistema de control y monitoreo agrícola que permitirá a la empresa registrar, gestionar y analizar la información proveniente de sensores y actuadores instalados en los cultivos.

**Funcionalidades del Sistema**
El sistema contará con diversas funcionalidades clave para garantizar un control eficiente:
* Gestión de dispositivos: Registro, actualización y consulta de sensores y actuadores.
* Historial de datos: Almacenamiento y consulta de mediciones para análisis a largo plazo.

Este sistema permitirá a la empresa mejorar su producción hortícola mediante el uso del sistema, asegurando un control más preciso de las condiciones del cultivo y maximizando la eficiencia operativa.

---


## **Requisitos**
## **Gestión de Dispositivos**
### 1. Registro de dispositivos  
- **Como** administrador del sistema  
- **Quiero** registrar sensores y actuadores en el sistema  
- **Para** gestionar y monitorear los dispositivos utilizados en la producción hortícola  

### 2. Edición de dispositivos  
- **Como** administrador del sistema  
- **Quiero** editar la información de los sensores y actuadores  
- **Para** actualizar su ubicación, modelo o tipo en caso de cambios  

### 3. Eliminación de dispositivos  
- **Como** administrador del sistema  
- **Quiero** eliminar sensores y actuadores obsoletos  
- **Para** mantener el sistema actualizado y libre de información innecesaria  

### 4. Ubicación geográfica de dispositivos  
- **Como** administrador del sistema  
- **Quiero** almacenar la latitud y longitud de cada dispositivo  
- **Para** conocer su ubicación exacta dentro del cultivo  

### 5. Registro del modelo y tipo de dispositivo  
- **Como** administrador del sistema  
- **Quiero** almacenar el modelo y tipo de cada dispositivo (sensor o actuador)  
- **Para** identificar y clasificar correctamente los dispositivos en uso  

---

## **Gestión de Tipos de Sensores y Actuadores**
### 6. Registro de tipos de sensores  
- **Como** administrador del sistema  
- **Quiero** registrar nuevos tipos de sensores  
- **Para** definir qué sensores se pueden utilizar en el sistema  

### 7. Gestión de atributos de sensores  
- **Como** administrador del sistema  
- **Quiero** asociar a cada tipo de sensor una descripción y una unidad de medida  
- **Para** asegurarme de que las mediciones sean comprensibles y correctamente interpretadas  

### 8. Registro de tipos de actuadores  
- **Como** administrador del sistema  
- **Quiero** registrar nuevos tipos de actuadores  
- **Para** definir los dispositivos que pueden ser controlados en el sistema  

### 9. Gestión de atributos de actuadores  
- **Como** administrador del sistema  
- **Quiero** asociar a cada tipo de actuador una descripción y su tipo (On-Off o Multinivel)  
- **Para** identificar correctamente sus funcionalidades en el sistema  

---

## **Monitoreo y Almacenamiento de Datos**
### 10. Captura y almacenamiento de mediciones  
- **Como** usuario del sistema  
- **Quiero** que las mediciones de los sensores se capturen y almacenen automáticamente  
- **Para** poder analizarlas y tomar decisiones informadas sobre la producción hortícola  

### 11. Registro de fecha y hora de medición  
- **Como** usuario del sistema  
- **Quiero** que cada medición incluya la fecha y hora de registro  
- **Para** conocer cuándo se realizó la medición y analizar tendencias en el tiempo  

### 12. Registro de cambios en los actuadores  
- **Como** usuario del sistema  
- **Quiero** que cada cambio en los actuadores registre el estado anterior y el nuevo estado  
- **Para** tener un historial de su comportamiento y evaluar su impacto en el cultivo  

---

## **Visualización de Datos**
### 13. Dashboard de monitoreo  
- **Como** usuario del sistema  
- **Quiero** visualizar un dashboard con datos de sensores y actuadores en tiempo real  
- **Para** monitorear el estado actual de la producción hortícola  

---

## **Gestión de Usuarios y Accesos**
### 14. Registro de usuarios  
- **Como** administrador del sistema  
- **Quiero** permitir que nuevos usuarios se registren en la plataforma  
- **Para** que diferentes personas dentro de la empresa puedan acceder al sistema según su rol  

### 15. Autenticación de usuarios  
- **Como** usuario del sistema  
- **Quiero** poder iniciar sesión con mis credenciales  
- **Para** acceder a la información relevante según mis permisos 

---
## **Arquitectura de software**
El proyecto presentado se trata de una aplicación web, la cuál se ajusta a la arquitectura cliente-servidor. La aplicacion sera desarrollada con el lenguaje de programacion Java

* **Base de Datos:** PostgreSQL 
* **FrontEnd:** HTML, CSS, Bootstrap, JavaScript. 
* **BackEnd:** Java/Spring.

