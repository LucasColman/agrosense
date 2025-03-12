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
                option.selected = idsSeleccionados.includes(option.value);
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
