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


/*
document.addEventListener('DOMContentLoaded', async function () {
    const token = sessionStorage.getItem('token');

    console.log("Token en dashboard:", token);

    if (!token) {
        alert("Acceso denegado");
        window.location.href = "/login";
    } else {
        const response= await fetch("/dashboard", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });
        if (!response.ok) {
            alert("No autorizado");
            window.location.href = "/login.html";  // Redirige al login si la autenticación falla
        } else {
            // Lógica para cargar el dashboard si la autenticación es exitosa
            await loadDeviceSummary();
        }

    }
});
*/









