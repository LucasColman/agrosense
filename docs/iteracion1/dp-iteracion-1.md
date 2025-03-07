# Trabajo en equipo
Para el desarrollo de la iteracion 1 se dividió el equipo de la siguiente manera:
### **Desarrolladores BackEnd**
- Colman Lucas 
- Ivasiuta Mariano
### **Desarrolladores FrontEnd**
- Anton Belen
- Martinez Alejandro
# Diseño OO
![image](https://github.com/user-attachments/assets/e5ce348d-fdb1-43e4-8797-5a31e70ad968)

# Wireframe y caso de uso
## **Caso de Uso 1: Gestión de Sensores y Actuadores**

- **Actor**: Administrador del sistema
- **Descripción**: Permite al administrador registrar, editar y eliminar sensores o actuadores en el sistema.

### **Flujo Principal:**
1. El administrador accede al sistema con sus credenciales.
2. Navega a la sección de correspondiente del dispositivo".
3. Selecciona una de las siguientes opciones:
	- Registrar un nuevo sensor o actuador ingresando su tipo, ubicación (latitud y longitud) y modelo.
	- Editar un sensor o actuador existente para actualizar su información.
	- Eliminar un sensor o actuador obsoleto.
4. Confirma la acción realizada.
5. El sistema actualiza la base de datos y muestra un mensaje de confirmación.

 ### **Flujo Alternativo**:
- Si el administrador ingresa datos inválidos, el sistema muestra un mensaje de error y permite corregirlos.


## Caso de Uso 2: Gestión de Tipos de Sensores y Actuadores

- **Actor:** Administrador del sistema  
-  **Descripción:** Permite registrar, editar y eliminar tipos de sensores y actuadores en el sistema.

### Flujo Principal:

1. El administrador inicia sesión en el sistema.
    
2. Accede a la sección "Tipos de Sensores o Tipos de Actuadores".
    
3. Selecciona una de las siguientes opciones:
    
    - **Registrar** un nuevo tipo de sensor o actuador ingresando su descripción y unidad de medida o tipo.
        
    - **Editar** un tipo existente para actualizar su información.
        
    - **Eliminar** un tipo que ya no sea utilizado en el sistema.
        
4. Confirma la acción seleccionada.
    
5. El sistema actualiza la información y muestra un mensaje de confirmación.
    

### Flujo Alternativo:    
- Si los datos ingresados son inválidos, el sistema muestra un mensaje de error y solicita correcciones.

## Caso de Uso 3: Registro de Usuarios

- **Actor:** Usuario
-  **Descripción:** Permite que nuevos usuarios se registren en la plataforma y asignarles un rol dentro del sistema.

### Flujo Principal:

1. El usuario accede al la pagina de registro

2. Ingresa los datos del usuario (nombre, correo electrónico, contraseña, etc.).
    
5. Confirma el registro.
    
6. El sistema almacena la información y envía un correo de bienvenida al nuevo usuario.
    

### Flujo Alternativo:

- Si los datos ingresados son incorrectos o el correo ya está registrado, el sistema muestra un mensaje de error.

---

## Caso de Uso 4: Inicio de Sesión

- **Actor:** Usuario del sistema  
- **Descripción:** Permite a los usuarios iniciar sesión en la plataforma con sus credenciales y acceder según su rol.

### Flujo Principal:

1. El usuario accede a la página de inicio de sesión.
    
2. Ingresa su correo electrónico y contraseña.
    
3. El sistema valida las credenciales.
    
4. Si son correctas, el usuario accede a su panel según su rol.
    

### Flujo Alternativo:

- Si las credenciales son incorrectas, el sistema muestra un mensaje de error y permite reintentar.

# Backlog de iteraciones
## Iteración 1
### 1. **Gestión de Dispositivos** 
- **Como** administrador del sistema  
- **Quiero** registrar,editar y eliminar sensores o actuadores en el sistema  
- **Para** gestionar y monitorear los dispositivos utilizados en la producción hortícola vinculándolo a su tipo correspondiente, actualizar su ubicación, modelo o tipo en caso de cambios y mantener el sistema actualizado y libre de información obsoleta

### 2. **Gestión de Tipos de Sensores y Actuadores**
- **Como** administrador del sistema  
- **Quiero** registrar, editar o eliminar nuevos tipos de sensores o actuadores
- **Para** definir qué sensores o actuadores se pueden utilizar en el sistema, actualizar la información de un dispositivo sensor o actuador y evitar información obsoleta en el sistema.

### 3. **Gestión de Usuarios y Accesos**
#### Registro de usuarios  
- **Como** administrador del sistema  
- **Quiero** permitir que nuevos usuarios se registren en la plataforma  
- **Para** que diferentes personas dentro de la empresa puedan acceder al sistema según su rol  

#### Autenticación de usuarios  
- **Como** usuario del sistema  
- **Quiero** poder iniciar sesión con mis credenciales  
- **Para** acceder a la información relevante según mis permisos 
