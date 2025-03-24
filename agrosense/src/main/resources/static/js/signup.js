// Permite registrar un usuario en la aplicación

async function signup(event) {
    event.preventDefault(); // Evita que el formulario se envíe

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const email = document.getElementById('email').value.trim();

    // Verificar si la contraseña cumple con el patróna
    let regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;

    if (!username || !password || !email) {
        alert('Debes completar todos los campos');
        return;
    }

    if (!regex.test(password)) {
        passwordError.style.display = "block";
    } else {
        passwordError.style.display = "none";
    }

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
        window.location.href = '/dashboard';
    } catch (error) {
        console.error("Error durante el registro", error);
        alert('Error durante el registro');
    }





}









