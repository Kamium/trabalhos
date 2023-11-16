<?php

require "conexaoMysql.php";

$pdo = mysqlConnect();

// Verifica se o formulário foi submetido
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Obtém os dados do formulário
    $contato = $_POST['contato'];
    $mensagem = $_POST['mensagem'];
    $productId = intval($_POST['productId']);

    // Validação dos dados (exemplo: verificar se estão preenchidos)
    if (empty($contato) || empty($mensagem)) {
        echo '<script>alert("Por favor, preencha todos os campos.");</script>';
        exit;
    }

    try {
        $pdo->beginTransaction();

        // Prepara a consulta SQL para a inserção
        $insertSql = "INSERT INTO Interesse (Mensagem, DataHora, Contato) VALUES (?, NOW(), ?)";
        $insertStmt = $pdo->prepare($insertSql);
        $insertStmt->execute([$mensagem, $contato]);

        $idmsg = $pdo->lastInsertId();

        // Prepara a consulta SQL para a atualização
        $updateSql = "UPDATE Interesse
                      SET CodAnuncio = ?
                      WHERE Codigo = ?";
        $updateStmt = $pdo->prepare($updateSql);
        $updateStmt->execute([$productId, $idmsg]);

        $pdo->commit();

        echo '<script>alert("Mensagem enviada com sucesso!");</script>';
        
        header('Location: /index.html');
        exit();
    } catch (PDOException $e) {
        $pdo->rollBack();
        
        http_response_code(400);
        echo '<script>alert("Erro ao conectar ao banco de dados: ' . $e->getMessage() . '");</script>';
        exit;
    }
} else {
    http_response_code(400);
    echo '<script>alert("O formulário não foi enviado.");</script>';
    exit;
}

?>
