

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

        // Al recibir la respuesta del backend:
        setCookie('authToken', data.token, 1);  // Guarda el token por 1 día (ajústalo según convenga)
        setCookie('role', data.rol, 1);

        // Esperar un poco antes de redirigir
        if (data.rol === "ADMIN") {
            window.location.href = "/dashboard";
        } else if(data.rol === "USER") {
            window.location.href = "/";
        }
    } catch (error) {
        console.error("Error durante el auth", error);
        alert('Error durante el auth');
    }
}

function setCookie(name, value, days) {
    let expires = "";
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}
