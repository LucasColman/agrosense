// Función para eliminar una cookie
function deleteCookie(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
}

// Función de logout
async function logout() {
    try {
        // Llama al endpoint de logout del backend
        const response = await fetch('/auth/logout', {
            method: 'POST'
        });
        if (!response.ok) {
            throw new Error('Error al realizar el logout');
        }
        // Elimina las cookies de autenticación
        deleteCookie('authToken');
        deleteCookie('role');
        // Redirige a la página de login
        window.location.href = '/login';
    } catch (error) {
        console.error('Error durante el logout:', error);
    }
}
