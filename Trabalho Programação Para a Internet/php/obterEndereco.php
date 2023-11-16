<?php
require "../php/conexaoMysql.php";
require "sessionVerification.php";
require "criaPagina.php";

session_start();
exitWhenNotLoggedIn();
$pdo = mysqlConnect();

$cep = $_GET["cep"];


try {
  $sql = "SELECT Estado, Cidade, Bairro FROM BaseEnderecos WHERE CEP = ?";
  $stmt = $pdo->prepare($sql);
  $stmt->execute([$cep]);
  $row = $stmt->fetch(PDO::FETCH_ASSOC);

  if ($row) {
    $endereco = [
      "estado" => $row["Estado"],
      "cidade" => $row["Cidade"],
      "bairro" => $row["Bairro"]
    ];
    echo json_encode($endereco);
  } else {
    http_response_code(400);
    exit;
  }
} catch (PDOException $e) {
  exit("Erro ao obter o endereço do CEP: " . $e->getMessage());
}
