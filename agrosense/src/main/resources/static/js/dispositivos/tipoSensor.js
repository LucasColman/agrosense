document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".edit-tipoSensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            const id = this.getAttribute("data-id");
            const nombre = this.getAttribute("data-nombre");
            const tipoMedida = this.getAttribute("data-tipoMedida");

            document.getElementById("tipoSensor-id-edit").value = id;
            document.getElementById("tipoSensor-nombre-edit").value = nombre;
            document.getElementById("tipoSensor-tipoMedida-edit").value = tipoMedida;

            // Establecer la acción del formulario
            document.getElementById("editTipoSensorForm").setAttribute("action", `/tipos-sensores/edit/${id}`);
        });
    });
});


document.addEventListener("DOMContentLoaded", function () {
    let tipoSensorIdToDelete = null;
    // Capturar el clic en los botones de eliminar y abrir el modal
    document.querySelectorAll(".delete-tipoSensor-btn").forEach(button => {
        button.addEventListener("click", function () {
            tipoSensorIdToDelete = this.getAttribute("data-id");
            document.getElementById("tipoSensor-id-delete").value = tipoSensorIdToDelete;
        });
    });

    // Confirmar eliminación cuando el usuario haga clic en el botón del modal
    document.getElementById("confirm-delete-btn").addEventListener("click", function () {
        if (tipoSensorIdToDelete) {
            fetch(`/tipos-sensores/delete/${tipoSensorIdToDelete}`, {method: "DELETE"})
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





