console.log("api.js se está ejecutando");

document.addEventListener('DOMContentLoaded', async function () {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login"; // Redirige al login si no hay sesión
    }
});






document.getElementById("logout").addEventListener("click", () => {
    localStorage.removeItem("authTtoken");
    window.location.href = "/login";
});


