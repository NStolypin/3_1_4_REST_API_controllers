(function () {
    let SpLst = document.getElementById('myTableBody');
    SpLst.onclick = function (event) {
        let td = event.target.closest('.editPerson');
        if (td) {
            if (SpLst.contains(td)) {
                editClick(td);
            };
        };

        let td2 = event.target.closest('.dltPerson');
        if (!td2) return;
        if (!SpLst.contains(td2)) return;
        deleteclick(td2);
    }

}());

function editClick(item) {
    let rowOfPerson = item.parentElement.parentElement;
    document.getElementById("ansErrorR");
    let listOfRoles = document.getElementById("usersRolesR");
    let rolesOfPerson = document.getElementById("yearOfBirthR").value = rowOfPerson.children[3].textContent.split(' ');
    document.getElementById("ansErrorR").innerText = "";
    document.getElementById("idPerson").value = Number(item.getAttribute('data-id-person'));
    document.getElementById("usernameR").value = rowOfPerson.children[1].textContent;
    document.getElementById("yearOfBirthR").value = rowOfPerson.children[2].textContent;
    let optionsOfRolesSelectArray = Array.from(listOfRoles.children);
    optionsOfRolesSelectArray.forEach(function (item2) {
        item2.selected = rolesOfPerson.includes(item2.value) ? true : false;
    });
}


function deleteclick(item) {
    let id = { id: Number(item.getAttribute('data-dlt-person')) }
    fetch("/api/delete", {
        method: 'DELETE',
        headers: {
            'X-XSRF-TOKEN': csrfToken,
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(id)
    }).then(response => response.ok
        ?
        deletePersonOK(item)
        :
        doNotDeletePerson(response)
    );
}

function deletePersonOK(item) {
    item.parentElement.parentElement.remove();
}

function doNotDeletePerson(response) {
    response.json().then(function (data) {
        alert(data.message);
    });
}