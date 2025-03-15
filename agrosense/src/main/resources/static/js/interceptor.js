// interceptor.js

// Guarda la referencia a la función fetch original
const originalFetch = window.fetch;

// Sobrescribe la función fetch global
window.fetch = async function(url, options = {}) {
    // Recupera el token desde sessionStorage
    const token = sessionStorage.getItem('token');

    // Si no hay opciones, inicialízalas
    options = options || {};

    // Asegúrate de que existe el objeto headers y agrega la cabecera Authorization
    options.headers = {
        ...options.headers,
        "Authorization": token ? `Bearer ${token}` : "",
        "Content-Type": "application/json"
    };

    // Llama a la función fetch original con los parámetros actualizados
    return originalFetch(url, options);
};
