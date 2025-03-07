/*
document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("authToken");

    fetch("http://localhost:8080/dashboard", { // Asegúrate de usar la ruta correcta
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("No autorizado");
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("sensores-total").textContent = `Total: ${data.sensores}`;
            document.getElementById("actuadores-total").textContent = `Total: ${data.actuadores}`;
        })
        .catch(error => {
            console.error("Error:", error);
            window.location.href = "/login"; // Si hay error, redirigir al login
        });
});
*/


// Función para cargar datos de sensores y actuadores
async function loadDeviceSummary() {
    try {
        // Endpoints separados que devuelven un entero
        const sensorResponse = await fetch('http://localhost:8080/sensores/cantidad');
        const actuatorResponse = await fetch('http://localhost:8080/actuadores/cantidad');

        if (!sensorResponse.ok || !actuatorResponse.ok) {
            throw new Error('Error al cargar los datos');
        }

        const sensorCount = await sensorResponse.json();
        const actuatorCount = await actuatorResponse.json();

        // Actualizar tarjetas con los datos recibidos
        document.getElementById('sensores-total').textContent = `Total: ${sensorCount}`;
        document.getElementById('actuadores-total').textContent = `Total: ${actuatorCount}`;

    } catch (error) {
        console.error('Error:', error);

        // Mostrar mensaje de error en las tarjetas
        document.getElementById('sensorTotal').textContent = 'Error al cargar';
        document.getElementById('actuatorTotal').textContent = 'Error al cargar';
    }
}

// Cargar datos cuando la página se carga
document.addEventListener('DOMContentLoaded', loadDeviceSummary);





