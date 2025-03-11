# Trabajo en equipo
Para el desarrollo de la iteracion 2 se dividió el equipo de la siguiente manera:
### **Desarrolladores BackEnd**
- Colman Lucas 
- Ivasiuta Mariano
### **Desarrolladores FrontEnd**
- Anton Belen
- Martinez Alejandro
# Diseño OO
![Agrosense Integrador](https://github.com/user-attachments/assets/5798f2b4-6306-4c8a-a1c6-634756fdcff2)

# Wireframe y caso de uso
## **Caso de Uso 1: Captura y Almacenamiento de Mediciones**  
### **Descripción:**  
Permitir que el sistema capture y almacene las mediciones de los sensores en una base de datos.  

### **Actores:**  
- Usuario del sistema  
- Sensores  

### **Flujo Principal:**  
1. El sensor toma una medición (ej., temperatura, humedad, etc.).  
2. El usuario obtiene los datos del sensor.  
3. El usuario registra en el sistema la medición obtenida.  
4. El usuario puede acceder a las mediciones almacenadas en el sistema.  

### **Flujo Alternativo:**  
- **3a.** Si la medición no se puede almacenar por un error de conexión, el sistema la reintenta o la almacena temporalmente.  

### **Precondiciones:**  
- Los sensores deben estar correctamente configurados y conectados al sistema.  

### **Postcondiciones:**  
- La medición queda almacenada en la base de datos y disponible para su consulta.  

---

## **Caso de Uso 2: Registro de Cambios en los Actuadores**  
### **Descripción:**  
Registrar cada cambio de estado en los actuadores, incluyendo el estado anterior y el nuevo estado.  

### **Actores:**  
- Usuario del sistema  
- Actuadores  

### **Flujo Principal:**  
1. El usuario detecta el cambio de estado de un actuador.  
2. El usuario registra el estado anterior y el nuevo estado.  
3. El sistema almacena el cambio en el historial de la base de datos.  
4. El usuario puede consultar el historial de cambios en los actuadores.  

### **Flujo Alternativo:**  
- **2a.** Si hay un error en la actualización del estado, el sistema genera una alerta.  

### **Precondiciones:**  
- El actuador debe estar correctamente configurado y conectado al sistema.  

### **Postcondiciones:**  
- Se almacena un registro con los cambios de estado del actuador.  

---

## **Caso de Uso 3: Visualización de Datos en el Dashboard**  
### **Descripción:**  
Permitir que el usuario visualice datos en un dashboard que muestre mediciones de sensores y estados de actuadores.  

### **Actores:**  
- Usuario del sistema  

### **Flujo Principal:**  
1. El usuario accede al sistema y abre el dashboard de monitoreo.  
2. El sistema recupera los datos más recientes de los sensores y actuadores.  
3. El sistema presenta la información en gráficos y tablas en el dashboard.  
4. El usuario analiza la información para tomar decisiones.  

### **Flujo Alternativo:**  
- **2a.** Si hay un error en la recuperación de datos, el sistema muestra un mensaje de error y sugiere reintentar.  

### **Precondiciones:**  
- Deben existir mediciones y estados de actuadores en la base de datos.  

### **Postcondiciones:**  
- El usuario puede visualizar los datos y tomar decisiones en tiempo real.  

# Backlog de iteraciones
## Iteración 2
### 1. **Monitoreo y Almacenamiento de Datos**
- **Como** usuario del sistema  
- **Quiero** que las mediciones de los sensores se capturen y almacenen  
- **Para** poder analizarlas y tomar decisiones informadas sobre la producción hortícola

### 2. **Registro de cambios en los actuadores** 
- **Como** usuario del sistema  
- **Quiero** que cada cambio en los actuadores registre el estado anterior y el nuevo estado  
- **Para** tener un historial de su comportamiento y evaluar su impacto en el cultivo  

### 3. **Visualización de Datos**
- **Como** usuario del sistema  
- **Quiero** visualizar datos de sensores y actuadores
- **Para** monitorear el estado actual de la producción hortícola

# Tareas
- CRUD de cambios de actuadores
- CRUD de datos obtenidos de sensores.
- Front de cambios de actuadores
- Front de datos de sensores