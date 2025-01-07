(function () {
    csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');

    let buttonSubmitForCreateNewPerson = document.getElementById("myCreateNewPerson");
    buttonSubmitForCreateNewPerson.addEventListener('click', function (event) {
        event.preventDefault();
        let selectElement = document.getElementById("usersRoles");
        let tableBody = document.getElementById("myTableBody")
        let toAllPersonButton = document.getElementById("nav-home-tab");
        let selectedOptions = Array.from(selectElement.selectedOptions).map(option => ({ id: option.value, persons: null, allowedOperations: null, authority: option.value }));

        person = {
            id: 0,
            username: document.getElementById('username').value,
            yearOfBirth: document.getElementById('yearOfBirth').value,
            password: document.getElementById('password').value,
            roles: selectedOptions
        }
        fetch("/api/person", {
            method: 'POST',
            headers: {
                'X-XSRF-TOKEN': csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(person)
        }).then(response => {
            response.ok ?
                okCreatedNewPerson(toAllPersonButton, tableBody, person, response.json())
                :
                notCreatedNewPerson(response)
        });

    });
}());

function okCreatedNewPerson(toAllPersonButton, tableBody, person, response) {
    document.getElementById('username').value = "";
    document.getElementById('yearOfBirth').value = 0;
    document.getElementById('password').value = "";
    tableBody.insertAdjacentHTML("beforeEnd",
        `<tr>
            <td></td>
            <td>${person.username}</td>
            <td>${person.yearOfBirth}</td>
            <td>${stringOfRoles(person.roles)}</td>
        </tr>`);
    response.then(data => {
        tableBody.lastElementChild.firstElementChild.innerText = data.id;
        tableBody.lastElementChild.insertAdjacentHTML("beforeEnd",
            `<td>
                <button type=\"button\" class=\"btn btn-primary editPerson\" data-id-person=\"${data.id}\" data-bs-toggle=\"modal\" data-bs-target=\"#editUser\">Редактировать</button>
            </td>
            <td>
                <button type=\"button\" class=\"btn btn-danger dltPerson\" data-dlt-person=\"${data.id}\">Удалить</button>
            </td >`
        );
    });
    toAllPersonButton.click();
}

function notCreatedNewPerson(response) {
    response.json().then(function (data) {
        let ansErrors = document.getElementById("ansError");
        ansErrors.insertAdjacentHTML("beforeEnd",
            `<p style="color:red">
            ${data.message}
        </p>`
        );
    })
}