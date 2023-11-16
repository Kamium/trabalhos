<?php

require "../php/conexaoMysql.php";
require "sessionVerification.php";

session_start();
exitWhenNotLoggedIn();
$pdo = mysqlConnect();

$msgId = intval($_POST['msgId']);

try {
    $pdo->beginTransaction();

    $sql = "DELETE FROM Interesse WHERE Codigo = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$msgId]);

    $pdo->commit();
    http_response_code(200);
    exit("Remoção concluída com sucesso!");
} catch (Exception $e) {
    $pdo->rollBack();
    http_response_code(400);
    exit("Erro ao remover a mensagem: " . $e->getMessage());
}

?>