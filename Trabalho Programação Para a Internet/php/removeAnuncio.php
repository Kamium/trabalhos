<?php

require "../php/conexaoMysql.php";
require "sessionVerification.php";

session_start();
exitWhenNotLoggedIn();
$pdo = mysqlConnect();

$productId = intval($_POST['productId']);
$imagePath = ".." . $_POST['imagePath'];
$pagePath = ".." . $_POST['pagePath'];

try {
    $pdo->beginTransaction();

    $sql = "DELETE FROM Anuncio WHERE Codigo = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$productId]);

    // Remover a foto da tabela Foto
    $sql = "DELETE FROM Foto WHERE CodAnuncio = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$productId]);

    // Remover as mensagens de interesse da tabela Interesse
    $sql = "DELETE FROM Interesse WHERE CodAnuncio = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$productId]);

    // Excluir a imagem do servidor
    if (file_exists($imagePath)) {
        unlink($imagePath);
    }

    // Excluir a página do servidor
    if (file_exists($pagePath)) {
        unlink($pagePath);
    }

    $pdo->commit();
    http_response_code(200);
    exit("Remoção concluída com sucesso!");
} catch (Exception $e) {
    $pdo->rollBack();
    http_response_code(400);
    exit("Erro ao remover o anúncio: " . $e->getMessage());
}

?>