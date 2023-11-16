<?php

  require "conexaoMysql.php";
  $pdo = mysqlConnect();

  class Anuncio
{
  public $titulo;
  public $descricao;
  public $preco;
  public $imagem;
  public $id;

  function __construct($titulo, $descricao, $preco, $imagem, $id)
  {
    $this->titulo = $titulo;
    $this->descricao = $descricao;
    $this->preco = $preco;
    $this->imagem = $imagem;
    $this->id = $id;
  }
}

$array = $_POST['form'] ?? '';
$categorie = $_POST['categorie'] ?? 'tudo';
$filter = $_POST['filter'] ?? 'title';
$offset = $_POST['offset'] ?? 0;
$max = $_POST['max'] ?? 100000;
$min = $_POST['min'] ?? 0;
$anuncios = array();

$where = array();
$chaves = array();

$termosTotais = explode(" ", $array);
$termos = array_slice($termosTotais, 0, 5);

if($filter=='title'){
  foreach ($termos as $indice => $termo) {
      $where[] = "a.Titulo LIKE ?";
      $chaves[] = "%{$termo}%";
  }
}else{
  foreach ($termos as $indice => $termo) {
      $where[] = "a.Descricao LIKE ?";
      $chaves[] = "%{$termo}%";
  }
}
if($categorie=='tudo'){
  $where[] = "a.CodCategoria = c.Codigo";
}else{
      $where[] = "a.CodCategoria = c.Codigo AND c.Nome = ?";
      $chaves[] = $categorie;
}
$where[] = "a.Preco >= ? AND a.Preco <= ?";
$chaves[] = $min;
$chaves[] = $max;


$chaves[] = $offset;
$sql = "SELECT a.Titulo, a.Descricao, a.Preco ,f.NomeArqFoto, a.Codigo FROM Anuncio AS a, Foto AS f, Categoria AS c";
if (!empty($where)) {
  $sql .= " WHERE  f.CodAnuncio = a.Codigo AND " . implode(" AND ", $where);
}
$sql .= " ORDER BY a.DataHora DESC LIMIT 6 OFFSET ?";
$stmt = $pdo->prepare($sql);
$stmt->execute(array_values($chaves));
if (!$stmt) {
  die('Erro na execução da consulta: ' . $pdo->errorInfo()[2]);
}

while ($row = $stmt->fetch()) {
  $titulo = htmlspecialchars($row['Titulo']);
  $descricao = htmlspecialchars($row['Descricao']);
  $preco = htmlspecialchars($row['Preco']);
  $imagem = htmlspecialchars($row['NomeArqFoto']);
  $id = strval($row['Codigo']);
  $objeto = new Anuncio($titulo, $descricao, $preco, $imagem, $id);
  $anuncios[] = $objeto;
}

header('Content-Type: application/json; charset=utf-8');
echo json_encode($anuncios);