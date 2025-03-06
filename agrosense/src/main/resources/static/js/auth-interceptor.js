// Guardar esto como auth-interceptor.js
document.addEventListener('DOMContentLoaded', function() {
    console.log('Interceptor de autenticación inicializado');

    // Interceptar el método fetch original
    const originalFetch = window.fetch;

    window.fetch = function(url, options = {}) {
        // Si no hay opciones, inicializar el objeto
        options = options || {};

        // Si no hay headers, inicializar el objeto
        if (!options.headers) {
            options.headers = {};
        }

        // Si los headers no son un objeto Headers, convertirlos
        if (!(options.headers instanceof Headers)) {
            const token = localStorage.getItem('authToken');
            if (token && !options.headers['Authorization']) {
                options.headers['Authorization'] = `Bearer ${token}`;
                console.log('Token añadido a fetch:', url);
            }
        } else {
            // Si es un objeto Headers, usar el método set
            const token = localStorage.getItem('authToken');
            if (token && !options.headers.has('Authorization')) {
                options.headers.set('Authorization', `Bearer ${token}`);
                console.log('Token añadido a fetch (Headers):', url);
            }
        }

        return originalFetch(url, options);
    };

    // Interceptar XMLHttpRequest
    const originalXhrOpen = XMLHttpRequest.prototype.open;
    const originalXhrSend = XMLHttpRequest.prototype.send;

    XMLHttpRequest.prototype.open = function() {
        this._url = arguments[1]; // Guardar la URL para usarla en send
        return originalXhrOpen.apply(this, arguments);
    };

    XMLHttpRequest.prototype.send = function() {
        const token = localStorage.getItem('authToken');
        if (token && this._url && !this._url.includes('/auth') && !this._url.includes('/auth')) {
            this.setRequestHeader('Authorization', `Bearer ${token}`);
            console.log('Token añadido a XHR:', this._url);
        }
        return originalXhrSend.apply(this, arguments);
    };

    // Interceptar enlaces de navegación
    document.addEventListener('click', function(e) {
        // Comprobar si el clic fue en un enlace
        const link = e.target.closest('a');
        if (link && link.href && link.href.startsWith(window.location.origin) &&
            !link.href.includes('/auth') && !link.href.includes('/auth')) {

            const token = localStorage.getItem('authToken');
            if (token) {
                console.log('Interceptando navegación a:', link.href);
                e.preventDefault();

                // Crear un formulario para enviar una solicitud POST con el token
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = link.href;
                form.style.display = 'none';

                // Añadir el token como campo del formulario
                const tokenInput = document.createElement('input');
                tokenInput.type = 'hidden';
                tokenInput.name = '_token';
                tokenInput.value = token;
                form.appendChild(tokenInput);

                // Añadir el formulario al documento y enviarlo
                document.body.appendChild(form);
                form.submit();
            }
        }
    });

    // Cuando se carga una página después del auth, verificar si necesitamos enviar el token
    const pathsThatNeedAuth = ['/dashboard', '/admin', '/perfil'];
    const currentPath = window.location.pathname;

    if (pathsThatNeedAuth.some(path => currentPath.startsWith(path))) {
        const token = localStorage.getItem('authToken');
        if (token) {
            console.log('Verificando autenticación para ruta protegida:', currentPath);

            // Verificar que estamos autenticados con una solicitud AJAX
            fetch('/api/check-auth', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .catch(error => {
                    console.error('Error verificando autenticación:', error);
                    window.location.href = '/auth';
                });
        } else {
            // Si no hay token, redirigir a auth
            console.log('No hay token, redirigiendo a auth');
            window.location.href = '/auth';
        }
    }
});