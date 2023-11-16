window.onload = function () {
    for (let form of document.forms)
        form.onsubmit = validateForm;
}

async function validateForm(e) {
    e.preventDefault(); // Impede a submissão do formulário de forma normal
    let form = e.target;
    let formIsValid = true;
    const spanEmail = form.email.nextElementSibling;
    const spanPassword = form.password.nextElementSibling;

    spanEmail.textContent = "";
    spanPassword.textContent = "";

    if (form.email.value === "") {
        spanEmail.textContent = "Informe um e-mail válido.";
        spanEmail.style.display = "block";
        formIsValid = false;
    }
    if (form.password.value === "") {
        spanPassword.textContent = "Campo obrigatório. Informe sua senha.";
        spanPassword.style.display = "block";
        formIsValid = false
    }
    if (!formIsValid) {
        return;
    } else {
        try {
            let response = await fetch("./php/login.php", { method: 'post', body: new FormData(form) });
            if (!response.ok) throw new Error(response.statusText);
            var result = await response.json();

            if (result.success)
                window.location = result.detail;
            else {
                spanEmail.textContent = "Usuário inválido. Verifique seu email e senha.";
                form.password.value = "";
                form.password.focus();
            }
        }
        catch (e) {
            console.error(e);
        }
    }


}
