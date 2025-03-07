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
            window.location.href = "/dashboard";
        } else {
            window.location.href = "/";
        }
    } catch (error) {
        console.error("Error durante el auth", error);
        alert('Error durante el auth');
    }
}