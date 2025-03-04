// Permite registrar un usuario en la aplicación
async function signup() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    try {
        const response = await fetch('/usuarios/registro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password,
                email: email
            })

        });

        if (!response.ok) {
            alert('Error al registrar el usuario');
            return;
        }
        alert('Usuario registrado correctamente');
        window.location.href = '/';
    } catch (error) {
        console.error("Error durante el registro", error);
        alert('Error durante el registro');
    }
}