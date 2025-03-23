document.addEventListener('DOMContentLoaded', function () {
    // Función de búsqueda en la tabla
    document.getElementById('searchInput').addEventListener('keyup', function () {
        let searchText = this.value.toLowerCase();
        let table = document.getElementById('historialMedTable');
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



