
async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/auth', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            alert('Credenciales inválidas');
            return;
        }

        const data = await response.json();

        // Guardar el token
        localStorage.setItem('authToken', data.token);
        localStorage.setItem('role', data.rol);

        // Redirigir según el rol
        if (data.rol === "ADMIN") {
            // Agregar token al header para la solicitud de redirección
            const dashboardResponse = await fetch('/dashboard', {
                headers: {
                    'Authorization': `Bearer ${data.token}`
                }
            });
            window.location.href = "/dashboard";
        } else {
            // Agregar token al header para la solicitud de redirección
            const indexResponse = await fetch('/', {
                headers: {
                    'Authorization': `Bearer ${data.token}`
                }
            });
            window.location.href = "/";
        }

    } catch (error) {
        console.error("Error durante el login", error);
        alert('Error durante el login');
    }
}


document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');

    if (token) {
        // Sobrescribir fetch global para incluir token
        const originalFetch = window.fetch;
        window.fetch = function(url, options = {}) {
            const newOptions = {...options};
            newOptions.headers = {
                ...options.headers,
                'Authorization': `Bearer ${token}`
            };
            return originalFetch(url, newOptions);
        };
    }
});




