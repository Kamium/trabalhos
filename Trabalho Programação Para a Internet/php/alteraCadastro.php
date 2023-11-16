<?php

require "../php/conexaoMysql.php";
require "sessionVerification.php";
require "criaPagina.php";

session_start();
exitWhenNotLoggedIn();
$pdo = mysqlConnect();


if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Obtém os dados do formulário
    $nome = $_POST['nome'];
    $cpf = $_POST['cpf'];
    $senhaAntiga = $_POST['senhaAntiga'];
    $novaSenha = $_POST['senha'];
    $telefone = $_POST['telefone'];

    // Validação dos dados
    if (
        empty($nome) ||
        empty($cpf) ||
        empty($senhaAntiga) ||
        empty($novaSenha) ||
        empty($telefone)
    ) {
        exit("Por favor, preencha todos os campos.");
    }

    $codigoAnunciante = $_SESSION['id'];
    $sql = "SELECT SenhaHash FROM Anunciante WHERE Codigo = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$codigoAnunciante]);
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    $senhaHash = $row['SenhaHash'];

    if (!password_verify($senhaAntiga, $senhaHash)) {
        http_response_code(400);
        exit("Senha antiga incorreta.");
    }

    $novaSenha = password_hash($novaSenha, PASSWORD_DEFAULT);

    // Inicia a transação
    $pdo->beginTransaction();

    try {
        // Prepara a consulta SQL para atualizar os dados
        $sql = "UPDATE Anunciante
                SET Nome = ?, CPF = ?, SenhaHash = ?, Telefone = ?
                WHERE Codigo = ?";

        $stmt = $pdo->prepare($sql);
        $stmt->execute([$nome, $cpf, $novaSenha, $telefone, $codigoAnunciante]);

        // Confirma a transação
        $pdo->commit();

        exit;
    } catch (PDOException $e) {
        $pdo->rollBack();

        http_response_code(400);
        exit("Erro ao atualizar os dados: " . $e->getMessage());
    }
} else {
    // Caso o formulário não tenha sido submetido, redireciona para a página do formulário
    header("Location: ../php/seusDados.php");
    exit;
}