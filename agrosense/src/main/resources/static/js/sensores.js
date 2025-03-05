document.addEventListener("DOMContentLoaded", function () {
    fetch("/sensores")
        .then(response => response.json())
        .then(data => {
            let tableBody = document.getElementById("sensorTableBody");
            tableBody.innerHTML = ""; // Limpiar la tabla antes de agregar nuevos datos

            data.forEach(sensor => {
                let row = `
                        <tr>
                            <td>${sensor.id}</td>
                            <td>${sensor.nombre}</td>
                            <td>${sensor.modelo}</td>
                            <td>${sensor.latitud}</td>
                            <td>${sensor.longitud}</td>
                            <td>${sensor.descripcion}</td>                      
                            <td>
                            <button class="btn btn-sm btn-warning edit-sensor-btn"
                                    data-bs-toggle="modal"
                                    data-bs-target="#editModal">Editar</button>
                            
                             <button class="btn btn-danger btn-sm delete-sensor-btn"
                                    data-bs-toggle="modal"
                                    data-bs-target="#deleteModal">Eliminar</button>     
                            </td>                
                        </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error cargando sensores:", error));


    // Función para manejar el envío del formulario
    document.getElementById('add-form').addEventListener('submit', function(event) {
        event.preventDefault(); // Evitar el envío tradicional del formulario

        // Obtener los valores de los campos del formulario
        const formData = new FormData(event.target);
        const data = {};
        formData.forEach((value, key) => {
            data[key] = value;
        });

        // Hacer la solicitud POST al servidor
        fetch('/sensores/store', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Si la respuesta es exitosa, cerrar el modal y mostrar un mensaje
                    $('#addModal').modal('hide');
                    alert('Registro agregado exitosamente');
                } else {
                    alert('Hubo un error al agregar el registro');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Hubo un error al realizar la solicitud');
            });
    });




});



