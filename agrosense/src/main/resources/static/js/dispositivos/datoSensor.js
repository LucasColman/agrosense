document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".medicion-sensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            const nombre = this.getAttribute("data-nombre");
            const modelo = this.getAttribute("data-modelo");
            const latitud = this.getAttribute("data-latitud");
            const longitud = this.getAttribute("data-longitud");
            const descripcion = this.getAttribute("data-descripcion");
            const tiposSensores = this.getAttribute("data-tipos-sensores") || "";


            document.getElementById("sensor-id-edit").value = id;
            document.getElementById("sensor-name-edit").value = nombre;
            document.getElementById("sensor-modelo-edit").value = modelo;
            document.getElementById("sensor-latitud-edit").value = latitud;
            document.getElementById("sensor-longitud-edit").value = longitud;
            document.getElementById("sensor-descripcion-edit").value = descripcion;

            // Asignar múltiples valores al select
            const select = document.getElementById("tipoSensor-edit");
            const idsSeleccionados = tiposSensores.split(","); // Convierte la cadena en un array

            Array.from(select.options).forEach(option => {
                option.selected = idsSeleccionados.includes(option.value);
            });


            // Establecer la acción del formulario
            document.getElementById("editSensorForm").setAttribute("action", `/sensores/edit/ ${id}`)
        });
    });
});




function enviarMedicion(sensorId, valor, unidad) {
    fetch('/dato-sensor/store', {
        method: 'POST',
        body: JSON.stringify({ sensorId, valor, unidad })
    })
        .then(response => response.text())
        .then(data => console.log("Respuesta:", data))
        .catch(error => console.error("Error al enviar medición:", error));
}

// Simulación de datos (Ejemplo: cada 10 segundos)
setInterval(() => {
    const sensorId = 1;  // ID del sensor
    const valor = (Math.random() * 100).toFixed(2);  // Valor aleatorio
    const unidad = "HUMEDAD";  // Unidad de medida
    enviarMedicion(sensorId, valor, unidad);
}, 10000);




