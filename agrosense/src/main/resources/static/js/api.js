console.log("api.js se está ejecutando");

document.addEventListener('DOMContentLoaded', async function () {
    const token = localStorage.getItem('authToken');

    if (!token) {
        console.warn("No hay token. Redirigiendo a login...");
        window.location.href = '/login';
        return;
    }

    console.log("Token antes de enviar la petición:", token);

    fetch('/', {
        method: 'GET',
        headers: new Headers({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })
    })
        .then(response => {
            console.log("Código de estado:", response.status);
            return response.text();
        })
        .then(data => console.log("Respuesta del servidor:", data))
        .catch(error => console.error("Error en la petición:", error));

    fetch('/dashboard', {
        method: 'GET',
        headers: new Headers({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        })
    })
        .then(response => {
            console.log("Código de estado:", response.status);
            return response.text();
        })
        .then(data => console.log("Respuesta del servidor:", data))
        .catch(error => console.error("Error en la petición:", error));
});






document.getElementById("logout").addEventListener("click", () => {
    localStorage.removeItem("authTtoken");
    window.location.href = "/login";
});



async function verificarAcceso(url) {
    try {
        const token = localStorage.getItem('authToken');
        const role = localStorage.getItem('role');

        console.log('Verificando acceso:', url);
        console.log('Token:', token);
        console.log('Rol:', role);


        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'}
        });

        console.log('Respuesta del servidor:', response.status);

        if (response.status === 403) {
            console.error('Acceso denegado - 403 Forbidden');
            alert("No tienes permisos para acceder a esta página");
            return false;
        }

        if (response.status === 401) {
            alert("Sesión expirada, vuelve a iniciar sesión");
            localStorage.removeItem('authToken');
            window.location.href = '/login';
            return false;
        }
    } catch (error) {
        console.error(`Error al acceder a ${url}:`, error);
    }
}