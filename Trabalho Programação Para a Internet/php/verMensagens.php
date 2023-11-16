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

        <!-- <?php

                $email = $_SESSION['user'];

                echo "<h1>Bem vindo, $email aqui serão listadas as mensagens dos seus anúncios!</h1>";
                ?> -->

        <div class="breadcrumb__wrapper">
            <nav class="breadcrumb">
                <ul>
                    <li class="breadcrumb__link">
                        <a href="./home.php">
                            <i class="fa-solid fa-house"></i>Home</a>
                    </li>

                    <li class="breadcrumb__link breadcrumb__link--active">
                        <a href="#">Minhas mensagens</a>
                    </li>
                </ul>
            </nav>
        </div>
        <section class="results">
            <h3>Minhas mensagens</h3>
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
                        <!-- <div class="card__description">{{prod-description}}</div> -->
                        <!-- <div class="card__price">
                            R$ {{prod-price}}
                        </div> -->
                        <div class="card__text">
                            <span class="card__text--dark">Contato:</span> {{prod-contato}}
                        </div>
                        <div class="card__text">
                            <span class="card__text--dark">Mensagem:</span> {{prod-msg}}
                        </div>
                    </a>
                    <div class="card__footer">
                        <button class="button--sm button--danger button-icon--left" data-product-id="{{msg-id}}">
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
                    .replace("{{prod-contato}}", product.contato)
                    .replace("{{prod-msg}}", product.msg)
                    .replace("{{msg-id}}", product.id);

                prodsSection.insertAdjacentHTML("beforeend", html);
            }

            const removeButtonList = prodsSection.querySelectorAll(".button--danger");
            removeButtonList.forEach(removeButton => {
                removeButton.addEventListener("click", function() {
                    removeProduct(this.parentNode.parentNode);
                });
            });
        }

        function removeProduct(element) {
            if (element && element.parentNode) {
                const msgId = element.querySelector(".button--danger").getAttribute("data-product-id");
                // Remover o elemento da árvore DOM
                element.parentNode.removeChild(element);

                const xhr = new XMLHttpRequest();
                xhr.open("POST", "removeMensagem.php", true);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                        alert(xhr.responseText);
                    }
                };
                xhr.send("msgId=" + msgId);
            }
        }

        function iniciaPesquisa() {
            let xhr = new XMLHttpRequest();
            xhr.open("POST", "/php/mensagensExistentes.php", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onload = function() {
                if (xhr.status != 200) {
                    console.error("Falha inesperada: " + xhr.responseText);
                    return;
                }

                try {
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
        }
    </script>

</body>

</html>