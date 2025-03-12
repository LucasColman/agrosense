document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".edit-sensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            const comportamiento = this.getAttribute("data-comportamiento");
            const descripcion = this.getAttribute("data-descripcion");
            const estados = this.getAttribute("data-estados");
            console.log("Estados recibidos en edición:", estados);

            // Limpiar los corchetes si es necesario
            const estadosLimpiados = estados.replace(/[\[\]']+/g, '').split(',');

            document.getElementById("tipo-descripcion-edit").value = descripcion;
            document.getElementById("comportamiento-edit").value = comportamiento;

            const estadosList = document.getElementById("edit-estados-list");
            const estadosHidden = document.getElementById("edit-estados-hidden");

            estadosList.innerHTML = ""; // Limpiar lista de estados
            estadosHidden.value = estados; // Asignar al input hidden

            if (comportamiento === "MULTINIVEL") {
                document.getElementById("edit-estados-container").style.display = "block";
                if (estadosLimpiados.length > 0) {
                    estadosLimpiados.forEach(estado => {
                        agregarEstadoBadge(estado, estadosList, estadosHidden);
                    });
                }
            } else {
                document.getElementById("edit-estados-container").style.display = "none";
                estadosHidden.value = "On,Off"; // Set to default "On,Off"
            }

            document.getElementById("editTipoActuadorForm").setAttribute("action", `/tipos-actuadores/edit/${id}`);
        });
    });
});


document.getElementById("comportamiento-edit").addEventListener("change", function () {
    let estadosContainer = document.getElementById("edit-estados-container");
    let estadosList = document.getElementById("edit-estados-list");
    let estadosHidden = document.getElementById("edit-estados-hidden");

    if (this.value === "ON_OFF") {
        estadosContainer.style.display = "none";
        estadosHidden.value = "On,Off"; // Set to default "On,Off"
    } else if (this.value === "MULTINIVEL") {
        estadosContainer.style.display = "block";
        estadosList.innerHTML = "";
        estadosHidden.value = "";
    } else {
        estadosContainer.style.display = "none";
    }
});


// Función para agregar un estado al formulario de edición
function agregarEstadoEdit() {
    const nuevoEstado = document.getElementById("edit-nuevo-estado").value.trim();
    const estadosList = document.getElementById("edit-estados-list");
    const estadosHidden = document.getElementById("edit-estados-hidden");

    if (nuevoEstado) {
        agregarEstadoBadge(nuevoEstado, estadosList, estadosHidden);
        document.getElementById("edit-nuevo-estado").value = "";
    }
}

function agregarEstadoBadge(estado, estadosList, estadosHidden) {
    let span = document.createElement("span");
    span.className = "badge bg-secondary me-2 p-2 position-relative";
    span.textContent = estado;
    span.style.paddingRight = "24px"; // Espacio para la X

    let btnEliminar = document.createElement("button");
    btnEliminar.type = "button";
    btnEliminar.className = "position-absolute top-0 start-100 translate-middle btn btn-danger btn-sm rounded-circle";
    btnEliminar.style.width = "18px";
    btnEliminar.style.height = "18px";
    btnEliminar.style.fontSize = "10px";
    btnEliminar.style.padding = "0"; // Evitar que el padding distorsione el botón
    btnEliminar.style.textAlign = "center"; // Aseguramos que el texto esté centrado
    btnEliminar.style.lineHeight = "18px"; // Centrar la X verticalmente
    btnEliminar.textContent = "✕";
    btnEliminar.onclick = function () {
        span.remove();
        actualizarEstadosHidden(estadosList, estadosHidden);
    };

    span.appendChild(btnEliminar);
    estadosList.appendChild(span);

    actualizarEstadosHidden(estadosList, estadosHidden);
}


document.addEventListener("DOMContentLoaded", function () {
    let tipoActuadorIdToDelete = null;

    // Capturar el clic en los botones de eliminar y abrir el modal
    document.querySelectorAll(".delete-sensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            tipoActuadorIdToDelete = this.getAttribute("data-id");
            document.getElementById("tipoActuador-id-delete").value = tipoActuadorIdToDelete;
        });
    });

    // Confirmar eliminación cuando el usuario haga clic en el botón del modal
    document.getElementById("confirm-delete-btn").addEventListener("click", function () {
        if (tipoActuadorIdToDelete) {
            fetch(`/tipos-actuadores/delete/${tipoActuadorIdToDelete}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        location.reload(); // Recargar la página si la eliminación es exitosa
                    } else {
                        alert("Error al eliminar el tipo de actuador");
                    }
                })
                .catch(error => console.error("Error:", error));
        }
    });
});

document.getElementById("comportamiento").addEventListener("change", function () {
    let estadosContainer = document.getElementById("estados-container");
    let estadosList = document.getElementById("estados-list");
    let estadosHidden = document.getElementById("estados-hidden");

    if (this.value === "ON_OFF") {
        estadosContainer.style.display = "none";
        estadosHidden.value = "On,Off";
    } else if (this.value === "MULTINIVEL") {
        estadosContainer.style.display = "block";
        estadosList.innerHTML = "";
        estadosHidden.value = "";
    } else {
        estadosContainer.style.display = "none";
    }
});

function agregarEstado() {
    let nuevoEstado = document.getElementById("nuevo-estado").value.trim();
    let estadosList = document.getElementById("estados-list");
    let estadosHidden = document.getElementById("estados-hidden");

    if (nuevoEstado) {
        // Crear el badge (span)
        let span = document.createElement("span");
        span.className = "badge bg-secondary me-2 p-2 position-relative";
        span.textContent = nuevoEstado;
        span.style.paddingRight = "24px"; // Espacio para la X

        // Crear botón de eliminar (X)
        let btnEliminar = document.createElement("button");
        btnEliminar.type = "button";
        btnEliminar.className = "position-absolute top-0 start-100 translate-middle btn btn-danger btn-sm rounded-circle";
        btnEliminar.style.width = "18px";
        btnEliminar.style.height = "18px";
        btnEliminar.style.display = "flex";
        btnEliminar.style.justifyContent = "center";
        btnEliminar.style.alignItems = "center";
        btnEliminar.style.fontSize = "10px";
        btnEliminar.style.lineHeight = "1";
        btnEliminar.textContent = "✕";
        btnEliminar.onclick = function () {
            span.remove(); // Eliminar visualmente
            actualizarEstadosHidden(); // Actualizar el input hidden
        };

        // Agregar el botón dentro del badge
        span.appendChild(btnEliminar);
        estadosList.appendChild(span);

        // Actualizar el input hidden
        actualizarEstadosHidden();
        document.getElementById("nuevo-estado").value = "";
    }
}

// Función para actualizar el input hidden con los estados actuales
function actualizarEstadosHidden() {
    let estadosHidden = document.getElementById("estados-hidden");
    let estados = Array.from(document.querySelectorAll("#estados-list span")).map(span => span.childNodes[0].nodeValue.trim());
    estadosHidden.value = estados.join(",");
}
