document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".edit-sensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            const nombre = this.getAttribute("data-nombre");
            const modelo = this.getAttribute("data-modelo");
            const latitud = this.getAttribute("data-latitud");
            const longitud = this.getAttribute("data-longitud");
            const descripcion = this.getAttribute("data-descripcion");
            const tiposSensores = this.getAttribute("data-tipos-sensores") || "";


            document.getElementById("sensor-id-edit").value = id;
            document.getElementById("sensor-name-edit").value = nombre;
            document.getElementById("sensor-modelo-edit").value = modelo;
            document.getElementById("sensor-latitud-edit").value = latitud;
            document.getElementById("sensor-longitud-edit").value = longitud;
            document.getElementById("sensor-descripcion-edit").value = descripcion;

            // Asignar múltiples valores al select
            const select = document.getElementById("tipoSensor-edit");
            const idsSeleccionados = tiposSensores.split(","); // Convierte la cadena en un array

            Array.from(select.options).forEach(option => {
                option.selected = idsSeleccionados.includes(option.value);
            });


            // Establecer la acción del formulario
            document.getElementById("editSensorForm").setAttribute("action", `/sensores/edit/ ${id}`)
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    let sensorIdToDelete = null;

    // Capturar el clic en los botones de eliminar y abrir el modal
    document.querySelectorAll(".delete-sensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            sensorIdToDelete = this.getAttribute("data-id");
            document.getElementById("sensor-id-delete").value = sensorIdToDelete;
        });
    });

    // Confirmar eliminación cuando el usuario haga clic en el botón del modal
    document.getElementById("confirm-delete-btn").addEventListener("click", function () {
        if (sensorIdToDelete) {
            fetch(`/sensores/delete/${sensorIdToDelete}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        location.reload(); // Recargar la página si la eliminación es exitosa
                    } else {
                        alert("Error al eliminar el sensor");
                    }
                })
                .catch(error => console.error("Error:", error));
        }
    });
});


async function obtenerSensores() {
    const token = sessionStorage.getItem("authToken");

    try {
        const response = await fetch("/sensores", {
            method: "GET",
        });

        if (!response.ok) {
            throw new Error("Acceso denegado");
        }


    } catch (error) {
        console.error("Error al obtener sensores:", error);
        window.location.href = "/login.html";
    }
}




