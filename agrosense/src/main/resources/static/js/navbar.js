async function cargarPerfil(){
    try {
        const response = await fetch('/usuarios/perfil', {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Error al cargar los datos del perfil');
        }

        const perfil = await response.json();

        // Actualiza todos los elementos con la clase "nombre-usuario"
        document.querySelectorAll('.nombre-usuario').forEach(elemento => {
            elemento.textContent = perfil.username;
        });
        document.getElementById('email-usuario').textContent = perfil.email;
    } catch (error) {
        console.error('Error al cargar el perfil:', error);
        document.querySelectorAll('.nombre-usuario').forEach(elemento => {
            elemento.textContent = 'Error al cargar';
        });
    }
}
document.addEventListener('DOMContentLoaded', cargarPerfil);