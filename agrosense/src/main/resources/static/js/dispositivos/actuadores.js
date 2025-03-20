document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".edit-actuador-btn").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            const nombre = this.getAttribute("data-nombre");
            const modelo = this.getAttribute("data-modelo");
            const latitud = this.getAttribute("data-latitud");
            const longitud = this.getAttribute("data-longitud");
            const descripcion = this.getAttribute("data-descripcion");
            const tipoActuadores = this.getAttribute("data-tipos-actuadores");

            document.getElementById("actuador-name-edit").value = nombre;
            document.getElementById("actuador-modelo-edit").value = modelo;
            document.getElementById("actuador-latitud-edit").value = latitud;
            document.getElementById("actuador-longitud-edit").value = longitud;
            document.getElementById("actuador-descripcion-edit").value = descripcion;

            // Asignar múltiples valores al select
            const select = document.getElementById("tipoActuador-edit");
            const idsSeleccionados = tipoActuadores.split(","); // Convierte la cadena en un array

            Array.from(select.options).forEach(option => {
                option = idsSeleccionados.includes(option.value);
            });

            // Establecer la acción del formulario
            document.getElementById("editActuadorForm").setAttribute("action", `/actuadores/edit/${id}`);
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    let actuadorIdToDelete = null;

    // Capturar el clic en los botones de eliminar y abrir el modal
    document.querySelectorAll(".delete-actuador-btn").forEach(button => {
        button.addEventListener("click", function () {
            actuadorIdToDelete = this.getAttribute("data-id");
            document.getElementById("actuador-id-delete").value = actuadorIdToDelete;
        });
    });

    // Confirmar eliminación cuando el usuario haga clic en el botón del modal
    document.getElementById("confirm-delete-btn").addEventListener("click", function () {
        if (actuadorIdToDelete) {
            fetch(`/actuadores/delete/${actuadorIdToDelete}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        location.reload(); // Recargar la página si la eliminación es exitosa
                    } else {
                        alert("Error al eliminar el actuador");
                    }
                })
                .catch(error => console.error("Error:", error));
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('.change-actuador-btn').forEach(button => {
        button.addEventListener('click', function () {
            // Obtener el ID del actuador y los estados asociados (ya en formato JSON)
            const actuadorId = this.getAttribute('data-id');
            const estadosJson = this.getAttribute('data-estados');
            const estados = JSON.parse(estadosJson).flat();  // Aplanar la lista de estados

            console.log("Estados parseados: ", estados);
            console.log("Estados JSON: ", estadosJson);

            // Mostrar el ID del actuador en el campo oculto del modal
            document.getElementById('actuador-id-change').value = actuadorId;

            // Obtener el select de estados
            const estadoSelect = document.getElementById('estado-actuador');
            estadoSelect.innerHTML = '';  // Limpiar las opciones actuales

            // Verificar si la lista de estados está vacía
            if (estados && estados.length > 0) {
                // Agregar los estados como opciones del select
                estados.forEach(estado => {
                    const option = document.createElement('option');
                    option.value = estado;    // El valor del option es el estado
                    option.textContent = estado;  // El texto del option también es el estado
                    estadoSelect.appendChild(option);  // Agregar el option al select
                });
            } else {
                // Si no hay estados, puedes agregar una opción por defecto
                const option = document.createElement('option');
                option.value = '';
                option.textContent = 'No hay estados disponibles';
                estadoSelect.appendChild(option);
            }

            const saveButton = document.getElementById('saveEstadoBtn');
            saveButton.removeEventListener('click', actualizarEstados); // Eliminar cualquier listener previo
            saveButton.addEventListener('click', function() {
                actualizarEstados(actuadorId);
            });
            //document.getElementById('saveEstadoBtn').addEventListener('click', actualizarEstados);

        });
    });
});

/*
async function actualizarEstados() {
    const actuadorId = document.getElementById('actuador-id-change').value;
    const estado = document.getElementById('estado-actuador').value;

    if (actuadorId && estado) {
        const response = await fetch(`/actuadores/nuevo-estado/${actuadorId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: estado
        });

        console.log("Respuesta del servidor: ", response);

        if (response.ok) {
            //location.reload();
            console.log("Estado actualizado correctamente");
        } else {
            alert('Error al actualizar el estado del actuador');
        }
    }
}*/

function actualizarEstados(actuadorId) {
    const estado = document.getElementById('estado-actuador').value;

    if (actuadorId && estado) {
        console.log("Estado a enviar: ", estado);

        // Regresamos una Promesa con la petición fetch
        return new Promise((resolve, reject) => {
            fetch(`/actuadores/nuevo-estado/${actuadorId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: estado
            })
                .then(response => {
                    console.log("Respuesta del servidor: ", response);
                    if (response.ok) {
                        console.log("Estado actualizado correctamente");
                        resolve();  // Resolver la promesa si todo sale bien
                    } else {
                        reject('Error al actualizar el estado del actuador');
                    }
                })
                .catch(error => {
                    console.error("Error en la petición:", error);
                    reject(error);
                });
        })
            .then(() => {
                // Si la promesa se resuelve, recargar la página o cualquier otra acción
                location.reload();
            })
            .catch(error => {
                alert(error); // En caso de error, mostramos el mensaje de error
            });
    }
}