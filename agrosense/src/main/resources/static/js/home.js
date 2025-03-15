document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await fetch("/", {
            method: "GET"
        });

        if (!response.ok) {
            alert("No autorizado");
            window.location.href = "/login.html";  // Redirige al login si la autenticación falla
        }
    } catch (error) {
        console.error("Error al obtener los datos:", error);
        window.location.href = "/login.html";  // Redirige al login en caso de error
    }
});