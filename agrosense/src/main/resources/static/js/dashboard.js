// Hacer una solicitud GET al dashboard
document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await fetch("/dashboard", {
            method: "GET"
        });

        if (!response.ok) {
            alert("No autorizado");
            window.location.href = "/login.html";  // Redirige al login si la autenticación falla
        } else {
            await loadDeviceSummary();
            await cargarPerfil();

        }
    } catch (error) {
        console.error("Error al obtener los datos del dashboard:", error);
        window.location.href = "/login.html";  // Redirige al login en caso de error
    }
});

// Función para cargar la cantidad de sensores y actuadores
async function loadDeviceSummary() {
    try {
        const sensorResponse = await fetch('http://localhost:8080/sensores/cantidad');
        const actuatorResponse = await fetch('http://localhost:8080/actuadores/cantidad');

        if (!sensorResponse.ok || !actuatorResponse.ok) {
            throw new Error('Error al cargar los datos');
        }

        const sensorCount = await sensorResponse.json();
        const actuatorCount = await actuatorResponse.json();

        document.getElementById('sensores-total').textContent = `Total: ${sensorCount}`;
        document.getElementById('actuadores-total').textContent = `Total: ${actuatorCount}`;
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('sensores-total').textContent = 'Error al cargar';
        document.getElementById('actuadores-total').textContent = 'Error al cargar';
    }
}

async function cargarPerfil(){
    try {
        const response = await fetch('/usuarios/perfil', {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Error al cargar los datos del perfil');
        }

        const perfil = await response.json();

        // Actualiza todos los elementos con la clase "nombre-usuario"
        document.querySelectorAll('.nombre-usuario').forEach(elemento => {
            elemento.textContent = perfil.username;
        });
        document.getElementById('email-usuario').textContent = perfil.email;
    } catch (error) {
        console.error('Error al cargar el perfil:', error);
        document.querySelectorAll('.nombre-usuario').forEach(elemento => {
            elemento.textContent = 'Error al cargar';
        });
    }
}









