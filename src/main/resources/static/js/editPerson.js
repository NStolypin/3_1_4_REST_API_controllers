(function () {
    csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');

    let backG = document.getElementById("ansErrorR");
    let buttonSubmitForEditNewPerson = document.getElementById("myEditPerson");
    buttonSubmitForEditNewPerson.addEventListener('click', function (event) {
        event.preventDefault();
        let selectElement = document.getElementById("usersRolesR");
        let tableBody = document.getElementById("myTableBody")
        let selectedOptions = Array.from(selectElement.selectedOptions).map(option => ({ id: option.value, persons: null, allowedOperations: null, authority: option.value }));

        let id = document.getElementById('idPerson').value;
        let username = document.getElementById('usernameR').value;
        let yearOfBirth = document.getElementById('yearOfBirthR').value;
        let password = document.getElementById('passwordR').value;
        person = {
            id: id,
            username: username,
            yearOfBirth: yearOfBirth,
            password: password,
            roles: selectedOptions
        }
        fetch("/api/person", {
            method: 'PATCH',
            headers: {
                'X-XSRF-TOKEN': csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(person)
        }).then(response => (response.ok
            ?
            okEditPerson(backG, tableBody, person, id, username, yearOfBirth, selectedOptions)
            :
            notEditPerson(backG, response)
        ));
    });
}());

function okEditPerson(backG, tableBody, person, id, username, yearOfBirth, selectedOptions) {
    backG.insertAdjacentHTML("beforeEnd",
        `<p style="color:darkgreen">
            Пользователь успешно отредактирован
        </p>`);
    let trArray = Array.from(tableBody.children);
    trArray.forEach(function (item) {
        if (item.firstElementChild.innerText == id) {
            item.firstElementChild.nextElementSibling.innerText = username;
            item.firstElementChild.nextElementSibling.nextElementSibling.innerText = yearOfBirth;
            item.firstElementChild.nextElementSibling.nextElementSibling.nextElementSibling.innerText = stringOfRoles(selectedOptions);
        }
    });
}

function notEditPerson(backG, response) {
    response.json().then(function (data) {
        backG.insertAdjacentHTML("beforeEnd",
            `<p style="color:red">
            ${data.message}
        </p>`
        );
    })
}