<?php

require "conexaoMysql.php";
require "sessionVerification.php";

session_start();
exitWhenNotLoggedIn();

$pdo = mysqlConnect();

?>
<!DOCTYPE html>
<html lang="PT-BR">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Favicon -->
    <link rel="shortcut icon" href="/assets/images/logo/favicon.ico" type="image/x-icon">

    <!-- CSS -->
    <link rel="stylesheet" href="/style/main.css" />
    <link rel="stylesheet" href="/style/criarAnuncio.css" />
    <link rel="stylesheet" href="/style/navbar.css" />
    <link rel="stylesheet" href="/style/footer.css" />
    <!-- Font Awesome CDN -->
    <script src="https://kit.fontawesome.com/f67e2bd4c4.js" crossorigin="anonymous"></script>
    <title>Alterar Cadastro</title>
</head>

<body>
<header>
        <nav class="navbar">
            <a class="navbar__logo" href="#">
                <img src="/assets/images/logo/logo-small.png" alt="Logo Desapega" />
                <span>Desapega</span>
            </a>


            <button onclick="openDropdown()" class="navbar__link--user button--md">
                <?php $email = $_SESSION['user'];
                echo "<span>$email</span>"
                ?>
                <i class="fa-solid fa-chevron-down"></i>
            </button>
            <ul class="navbar__cta--user-logged">
                <li class="navbar__link">
                    <a href="./seusDados.php">Alterar meus dados</a>
                </li>
                <li class="navbar__link">
                    <a href="./criarAnuncio.php">Novo anúncio</a>
                </li>
                <li class="navbar__link">
                    <a href="./verMensagens.php">Minhas mensagens</a>
                </li>

                <li class="navbar__link">
                    <?php echo
                    "<a href='logout.php'>
                    Sair <i class='fa-solid fa-arrow-right-from-bracket'></i>
                    </a>"
                    ?>
                </li>
            </ul>

            <button onclick="openHamburgerMenu()" id="navbar__toggle-btn" class="fab--md navbar__toggle-btn">
                <i class="fa-solid fa-bars"></i>
            </button>

            <ul class="navbar__cta--responsive card">
                <li class="navbar__link">
                    <?php $email = $_SESSION['user'];
                    echo "$email"
                    ?>
                </li>
                <li class="navbar__link">
                    <a href="./seusDados.php">Alterar meus dados</a>
                </li>
                <li class="navbar__link">
                    <a href="./criarAnuncio.php">Novo anúncio</a>
                </li>
                <li class="navbar__link">
                    <a href="./verMensagens.php">Minhas mensagens</a>
                </li>
                <li class="navbar__link">
                    <?php echo
                    "<a href='logout.php'>
                        Sair <i class='fa-solid fa-arrow-right-from-bracket'></i>
                    </a>"
                    ?>
                </li>
            </ul>
        </nav>
    </header>
    <main>
        <div class="breadcrumb__wrapper">
            <nav class="breadcrumb">
                <ul>
                    <li class="breadcrumb__link">
                        <a href="./home.php">
                            <i class="fa-solid fa-house"></i>Home</a>
                    </li>

                    <li class="breadcrumb__link breadcrumb__link--active">
                        <a href="#">Alterar cadastro</a>
                    </li>
                </ul>
            </nav>
        </div>

        <section class="new-announcement__form">
            <h2>Alterar cadastro</h2>
            <form action="alteraCadastro.php" method="POST" id="cadastroForm">
                <div class="form-field">
                    <label for="nome">Nome:</label>
                    <input class="input--md" type="text" id="nome" name="nome" required>
                </div>
                <div class="form-field">
                    <label for="cpf">CPF:</label>
                    <input class="input--md" type="text" id="cpf" name="cpf" required>
                </div>
                <div class="form-field">
                    <label for="senhaAntiga">Senha Atual:</label>
                    <input class="input--md" type="password" id="senhaAntiga" name="senhaAntiga" required>
                </div>
                <div class="form-field">
                    <label for="senha">Nova Senha:</label>
                    <input class="input--md" type="password" id="senha" name="senha" required>
                </div>
                <div class="form-field">
                    <label for="telefone">Telefone:</label>
                    <input class="input--md" type="text" id="telefone" name="telefone" required>
                </div>
                <button class="button--md button--primary w-100" type="submit">Salvar alterações</button>
            </form>
        </section>
    </main>
    <footer class="footer">
        <a href="/index.html" class="footer__column">
            <img src="/assets/images/logo/logo-full-white.png" alt="" />
        </a>

        <p class="footer__column footer__copyright">
            &copy 2023 Desapega Ltda. Todos os direitos reservados.
        </p>
    </footer>

    <script src="../js/toggleButtons.js"></script>
</body>
<script>
    function handleFormSubmission(event) {
        event.preventDefault();

        const form = event.target;
        const formData = new FormData(form);


        const xhr = new XMLHttpRequest();
        const url = 'alteraCadastro.php';

        xhr.open('POST', url, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert('Dados alterados com sucesso');
                    window.location = "../php/home.php";
                } else {
                    alert(xhr.responseText);
                }
            }
        };

        xhr.send(formData);
    }

    const form = document.querySelector('#cadastroForm');
    form.addEventListener('submit', handleFormSubmission);
</script>

</html>