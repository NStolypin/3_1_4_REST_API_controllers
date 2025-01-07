(async function () {
    let response = await fetch("/api/allpeople");

    if (response.ok) { // если HTTP-статус в диапазоне 200-299
        // получаем тело ответа (см. про этот метод ниже)
        let json = await response.json();
        let tableBody = document.getElementById("myTableBody")
        json.forEach(function (item) {
            tableBody.insertAdjacentHTML("beforeEnd",
                `<tr>
                <td>${item.id}</td>
                <td>${item.username}</td>
                <td>${item.yearOfBirth}</td>
                <td>${stringOfRoles(item.roles)}</td>
                <td>
                    <button type=\"button\" class=\"btn btn-primary editPerson\" data-id-person=\"${item.id}\" data-bs-toggle=\"modal\" data-bs-target=\"#editUser\">Редактировать</button>
                </td>
                <td>
                    <button type=\"button\" class=\"btn btn-danger dltPerson\" data-dlt-person=\"${item.id}\">Удалить</button>
                </td>
            </tr>`);
        });

    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}());

function stringOfRoles(roles) {
    let idsOfRoles = roles.map(item => item.id)
    return idsOfRoles.join(' ');
}