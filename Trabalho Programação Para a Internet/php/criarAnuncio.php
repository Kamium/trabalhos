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
    <title>Crie o seu anúncio</title>
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
            <ul class="navbar__cta--user-logged card">
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
                        <a href="#">Novo anúncio</a>
                    </li>
                </ul>
            </nav>
        </div>

        <section class="new-announcement__form">
            <h2>Crie o seu anúncio</h2>
            <form id="cadastroForm" action="cadastraAnuncio.php" method="POST" enctype="multipart/form-data">
                <div class="form-field">
                    <label for="titulo">Título:</label>
                    <input class="input--md" type="text" id="titulo" name="titulo" required>
                </div>
                <div class="form-field">
                    <label for="descricao">Descrição:</label>
                    <textarea class="input--md" id="descricao" name="descricao" required></textarea>
                </div>
                <div class="form-field">
                    <label for="preco">Preço:</label>
                    <input class="input--md" type="number" id="preco" name="preco" step="0.01" required>
                </div>
                <div class="form-field">
                    <label for="cep">CEP:</label>
                    <input class="input--md" type="text" id="cep" name="cep" required>
                </div>
                <div class="form-field">
                    <label for="bairro">Bairro:</label>
                    <input class="input--md" type="text" id="bairro" name="bairro" required>
                </div>
                <div class="form-field">
                    <label for="cidade">Cidade:</label>
                    <input class="input--md" type="text" id="cidade" name="cidade" required>
                </div>
                <div class="form-field">
                    <label for="estado">Estado:</label>
                    <input class="input--md" type="text" id="estado" name="estado" required>
                </div>
                <div class="form-field">
                    <label for="categoria">Categoria:</label>
                    <select class="input--md" id="categoria" name="categoria" required>
                        <option value="">Selecione</option>
                        <option value="Veiculo">Veículos</option>
                        <option value="Eletroeletronico">Eletroeletrônicos</option>
                        <option value="Imovel">Imóveis</option>
                        <option value="Movel">Móveis</option>
                        <option value="Vestuario">Vestuário</option>
                        <option value="Outros">Outros</option>
                    </select>
                </div>
                <div class="form-field">
                    <label for="foto">Foto:</label>
                    <input class="input--md" type="file" id="foto" name="foto" multiple required>
                </div>
                <button class="button--md button--primary w-100" type="submit">Cadastrar</button>
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
        const fileInput = form.querySelector('#foto');
        const imageFile = fileInput.files[0];

        if (!imageFile) {
            // Caso o usuário não selecione uma imagem
            alert('Selecione uma imagem.');
            return;
        }
        const xhr = new XMLHttpRequest();
        const url = 'cadastraAnuncio.php';

        xhr.open('POST', url, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert('Anúncio criado com sucesso');
                    window.location = "../php/home.php";
                } else {
                    alert(xhr.responseText);
                }
            }
        };

        xhr.send(formData);
    }
    // Função para preencher os campos "estado" e "cidade" ao digitar o CEP
    function buscaCidade(cep) {
        if (cep.length != 9) return;
        // Realizar requisição AJAX para obter os dados do CEP
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "obterEndereco.php?cep=" + cep, true);
        xhr.onload = function() {
            if (xhr.status === 200) {
                var endereco = JSON.parse(xhr.responseText);
                if (endereco) {
                    document.getElementById("estado").value = endereco.estado;
                    document.getElementById("cidade").value = endereco.cidade;
                    document.getElementById("bairro").value = endereco.bairro;
                }
            } else {
                document.getElementById("estado").value = '';
                document.getElementById("cidade").value = '';
                document.getElementById("bairro").value = '';
                console.log(xhr.responseText);
            }
        };
        xhr.send();

    }

    window.onload = function() {
        const inputCep = document.querySelector("#cep");
        inputCep.onkeyup = () => buscaCidade(inputCep.value);
        const form = document.querySelector('#cadastroForm');
        form.addEventListener('submit', handleFormSubmission);
    }
</script>

</html>