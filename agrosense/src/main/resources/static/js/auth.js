

async function login(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!username || !password) {
        alert('Por favor, ingrese usuario y contraseña');
        return;
    }
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
        setCookie('authToken', data.token, 1);
        setCookie('role', data.rol, 1);

        if (data.rol === "ADMIN" || data.rol === "USER") {
            window.location.href = "/dashboard";
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
