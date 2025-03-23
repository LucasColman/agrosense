
// Editar actuador
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

//Elimminar actuador
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
/*
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
            saveButton.addEventListener('click', function () {
                actualizarEstados(actuadorId);
            });
            //document.getElementById('saveEstadoBtn').addEventListener('click', actualizarEstados);

        });
    });
});
*/


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
/*
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

*/


// Función para buscar en la tabla de actuadores
document.addEventListener('DOMContentLoaded', function () {
    // Función de búsqueda en la tabla
    document.getElementById('searchInput').addEventListener('keyup', function () {
        let searchText = this.value.toLowerCase();
        let table = document.getElementById('actuadoresTable');
        let rows = table.getElementsByTagName('tr');

        for (let i = 1; i < rows.length; i++) {
            let found = false;
            let cells = rows[i].getElementsByTagName('td');

            for (let j = 0; j < cells.length - 1; j++) {
                let cellText = cells[j].innerText.toLowerCase();
                if (cellText.indexOf(searchText) > -1) {
                    found = true;
                    break;
                }
            }

            rows[i].style.display = found ? '' : 'none';
        }
    });
});

/*
document.addEventListener("DOMContentLoaded", function () {
    const tipoActuadorSelect = document.getElementById("tipoActuador");
    const estadoActualDiv = document.getElementById("estadoActualDiv");

    tipoActuadorSelect.addEventListener("change", function () {
        if (this.value) {
            estadoActualDiv.style.display = "block"; // Mostrar el select de estado
        } else {
            estadoActualDiv.style.display = "none"; // Ocultar si no hay selección
        }
    });
});
*/

//BOTON ASOCIAR TIPOS
document.addEventListener("DOMContentLoaded", function () {
    const newTipoActuadorButtons = document.querySelectorAll(".new-tipo-actuador-btn");
    const actuadorIdInput = document.getElementById("actuador-id");

    newTipoActuadorButtons.forEach(button => {
        button.addEventListener("click", function () {
            const actuadorId = this.getAttribute("data-id");
            actuadorIdInput.value = actuadorId; // Asigna el ID al input oculto
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const tipoActuadorSelect = document.getElementById("tipoActuador");
    const estadoActualDiv = document.getElementById("estadoActualDiv");
    const estadoActualSelect = document.getElementById("estadoActual");

    tipoActuadorSelect.addEventListener("change", function () {
        const tipoActuadorId = this.value;

        if (tipoActuadorId) {
            // Hacer la petición AJAX
            fetch(`/actuadores/${tipoActuadorId}/estados`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al obtener los estados");
                    }
                    return response.json();
                })
                .then(estados => {
                    // Limpiar opciones previas
                    estadoActualSelect.innerHTML = '<option value="">Seleccione un estado</option>';

                    // Agregar las nuevas opciones
                    estados.forEach(estado => {
                        const option = document.createElement("option");
                        option.value = estado;
                        option.textContent = estado;
                        estadoActualSelect.appendChild(option);
                    });

                    // Mostrar el select de estados
                    estadoActualDiv.style.display = "block";
                })
                .catch(error => {
                    console.error("Error:", error);
                    estadoActualDiv.style.display = "none";
                });
        } else {
            estadoActualDiv.style.display = "none"; // Ocultar si no hay selección
        }
    });
});


//BOTON VER TIPOS DE ACTUADORES
document.addEventListener("DOMContentLoaded", function () {
    const verTipoActuadorButtons = document.querySelectorAll(".ver-tipo-actuador-btn");

    verTipoActuadorButtons.forEach(button => {
        button.addEventListener("click", function () {
            const actuadorId = this.getAttribute("data-actuador-id");

            fetch(`/actuadores/${actuadorId}/tipos`)
                .then(response => response.json())
                .then(tiposAsociados => {
                    const tbody = document.getElementById("tiposActuadoresBody");
                    tbody.innerHTML = ""; // Limpiar la tabla antes de agregar nuevos datos

                    if (tiposAsociados.length === 0) {
                        tbody.innerHTML = `
                            <tr>
                                <td colspan="4" class="empty-state">
                                    <i class="fas fa-info-circle fa-2x mb-3"></i>
                                    <p>No hay tipos de actuadores asociados a este actuador.</p>
                                </td>
                            </tr>`;
                        return;
                    }

                    // Agregar los nuevos datos
                    tiposAsociados.forEach(tipo => {
                        // Crear una fila con el botón de "Cambiar Estado" solo si el actuador tiene un tipo asignado
                        const row = `
                            <tr>
                                <td>${tipo.tipoActuadorId}</td>
                                <td>${tipo.descripcion}</td>
                                <td>${tipo.estadoActual}</td>
                                <td>
                                    <!-- Mostrar el botón solo si hay estado actual -->
                                    ${tipo.estadoActual ? `<button type="button" class="btn btn-warning btn-sm cambiarEstadoBtn" data-actuador-id="${actuadorId}" data-tipo-actuador-id="${tipo.tipoActuadorId}">Cambiar Estado</button>` : ''}
                                </td>
                            </tr>`;
                        tbody.innerHTML += row;
                    });
                })
                .catch(error => console.error("Error al obtener los tipos:", error));
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Modal de cambiar estado
    const cambiarEstadoModal = new bootstrap.Modal(document.getElementById('cambiarEstadoModal'));
    const nuevoEstadoSelect = document.getElementById("nuevoEstado");
    const guardarEstadoBtn = document.getElementById("guardarEstadoBtn");

    let actuadorIdGlobal = null; // Variable global para guardar el ID del actuador seleccionado
    let tipoActuadorIdGlobal = null; // Variable global para guardar el ID del tipo de actuador seleccionado

    // Función para cargar los estados posibles para un tipo de actuador
    function cargarEstados(tipoActuadorId) {
        fetch(`/actuadores/${tipoActuadorId}/estados`) // Obtener los estados por tipo de actuador
            .then(response => response.json())
            .then(estados => {
                nuevoEstadoSelect.innerHTML = '<option value="">Seleccione un estado</option>';
                estados.forEach(estado => {
                    const option = document.createElement("option");
                    option.value = estado;
                    option.textContent = estado;
                    nuevoEstadoSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error al cargar los estados:", error);
            });
    }

    // Función para abrir el modal de cambio de estado
    document.getElementById("tiposActuadoresBody").addEventListener("click", function (e) {
        if (e.target && e.target.classList.contains("cambiarEstadoBtn")) {
            actuadorIdGlobal = e.target.getAttribute("data-actuador-id");
            tipoActuadorIdGlobal = e.target.getAttribute("data-tipo-actuador-id");

            // Cargar los estados para el tipo de actuador
            cargarEstados(tipoActuadorIdGlobal);

            // Mostrar el modal
            cambiarEstadoModal.show();
        }
    });

    // Guardar el nuevo estado seleccionado
    guardarEstadoBtn.addEventListener("click", function () {
        const nuevoEstado = nuevoEstadoSelect.value;

        if (nuevoEstado && actuadorIdGlobal && tipoActuadorIdGlobal) {
            // Realizar el cambio de estado con una petición PUT
            fetch(`/actuadores/cambiar-estado/${actuadorIdGlobal}/${tipoActuadorIdGlobal}`, {
                method: 'PUT',
                body: JSON.stringify({ estado: nuevoEstado }) // Enviar JSON correctamente
            })
                .then(response => {
                    if (response.ok) {
                        alert('Estado actualizado correctamente');
                        cambiarEstadoModal.hide();
                        // Aquí puedes hacer algo para actualizar la tabla o el contenido mostrado
                    } else {
                        alert('Error al actualizar el estado');
                    }
                })
                .catch(error => {
                    console.error("Error al guardar el estado:", error);
                });
        } else {
            alert("Por favor, seleccione un estado.");
        }
    });
});


