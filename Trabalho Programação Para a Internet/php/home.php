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
    <link rel="stylesheet" href="../style/main.css" />
    <link rel="stylesheet" href="../style/index.css" />
    <link rel="stylesheet" href="../style/navbar.css" />
    <link rel="stylesheet" href="../style/footer.css" />
    <!-- Font Awesome CDN -->
    <script src="https://kit.fontawesome.com/f67e2bd4c4.js" crossorigin="anonymous"></script>
    <title>Dashboard - Desapega</title>
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

        <!-- <?php

                $email = $_SESSION['user'];

                echo "<h1>Bem vindo, $email aqui serão listados os seu anúncios!</h1>";
                ?> -->

        <div class="breadcrumb__wrapper">
            <nav class="breadcrumb">
                <ul>
                    <li class="breadcrumb__link">
                        <a href="#">
                            <i class="fa-solid fa-house"></i>Home</a>
                    </li>
                </ul>
            </nav>
        </div>

        <section class="results">
            <div class="results__header row">
                <div class="column--6">
                    <h3>Meus anúncios</h3>
                </div>

                <div class="column--6">
                    <a href="./criarAnuncio.php" class="button--md link-button--primary button-icon--left"><i class="fa-solid fa-plus"></i>Novo anúncio</a>
                </div>
            </div>


            <div id="results">

            </div>
            <template id="template">
                <div class="card product-card">
                    <a href="/pages/{{prod-id}}.html">
                        <div class="card__img">
                            <img src="/assets/images/{{prod-image}}" alt="">
                        </div>
                        <div class="card__header">
                            <div class="card__title">
                                {{prod-name}}
                            </div>
                        </div>
                        <div class="card__description">{{prod-description}}</div>
                    </a>
                    <div class="card__footer">
                        <div class="card__price">
                            R$ {{prod-price}}
                        </div>

                        <button class="button--sm button--danger button-icon--left" data-product-id="{{prod-id}}">
                            <i class="fa-solid fa-trash"></i>Remover
                        </button>
                    </div>
                </div>

            </template>
        </section>

    </main>

    <footer class="footer">
        <a href="../index.html" class="footer__column">
            <img src="../assets/images/logo/logo-full-white.png" alt="" />
        </a>

        <p class="footer__column footer__copyright">
            &copy 2023 Desapega Ltda. Todos os direitos reservados.
        </p>
    </footer>

    <script src="../js/toggleButtons.js"></script>
</body>

<script>
    var sessionId = <?php echo json_encode($_SESSION['id']); ?>;
    var offset = 0;

    function renderProducts(newProducts) {

        const prodsSection = document.getElementById("results");
        const template = document.getElementById("template");

        for (let product of newProducts) {
            let html = template.innerHTML
                .replace("{{prod-id}}", product.id)
                .replace("{{prod-image}}", product.imagem)
                .replace("{{prod-name}}", product.titulo)
                .replace("{{prod-description}}", product.descricao)
                .replace("{{prod-price}}", product.preco)
                .replace("{{prod-id}}", product.id);

            prodsSection.insertAdjacentHTML("beforeend", html);

        }
        const addButtonList = prodsSection.querySelectorAll(".button--danger");
        addButtonList.forEach(addButton => {
            addButton.addEventListener("click", function() {
                removeProduct(this.parentNode.parentNode);
            });
        });
    }

    function removeProduct(element) {
        if (element && element.parentNode) {
            const productId = element.querySelector(".button--danger").getAttribute("data-product-id");
            const imagePath = element.querySelector("img").getAttribute("src");
            const pagePath = element.querySelector("a").getAttribute("href");
            // Remover o elemento da árvore DOM

            const xhr = new XMLHttpRequest();
            xhr.open("POST", "removeAnuncio.php", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function() {
                if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                    // Lógica adicional após a remoção bem-sucedida do banco de dados
                    element.parentNode.removeChild(element);
                    alert(xhr.responseText);
                }
            };
            xhr.send("productId=" + productId + "&imagePath=" + imagePath + "&pagePath=" + pagePath);
        }

    }

    function iniciaPesquisa() {

        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/php/anunciosExistentes.php", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onload = function() {

            // verifica o código de status retornado pelo servidor
            if (xhr.status != 200) {
                console.error("Falha inesperada: " + xhr.responseText);
                return;
            }

            try {
                // converte a string JSON para objeto JavaScript
                var anuncios = JSON.parse(xhr.responseText);
            } catch (e) {
                console.error("String JSON inválida: " + xhr.responseText);
                return;
            }
            renderProducts(anuncios);
        }

        xhr.onerror = function() {
            console.error("Erro de rede - requisição não finalizada");
        };

        var data = "id=" + encodeURIComponent(sessionId) +
            "&offset=" + encodeURIComponent(offset);

        xhr.send(data);

        offset += 6;

    }
    window.onscroll = function() {
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
            iniciaPesquisa();
        }
    };
    window.onload = function() {
        offset = 0;
        iniciaPesquisa();
        const prodsSection = document.getElementById("results");

    }
</script>

</html>