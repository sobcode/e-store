function checkIfPasswordEquals() {
    document.getElementById("floatingConfirmPassword").setAttribute("pattern", document.getElementById("floatingPassword").value);
}

function showWarning(id) {
    document.getElementById(id).removeAttribute("hidden");
}

function showModal() {
    let myModal = new bootstrap.Modal(document.getElementById('myModal'), {});
    myModal.toggle()
}

window.onload = showModal;