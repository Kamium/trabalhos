<?php

require "../php/conexaoMysql.php";
require "sessionVerification.php";
require "criaPagina.php";

session_start();
exitWhenNotLoggedIn();
$pdo = mysqlConnect();

class Anuncio
{
    public $titulo;
    public $descricao;
    public $preco;
    public $imagem;
    public $msg;
    public $contato;
    public $id;

    function __construct($titulo, $descricao, $preco, $imagem, $msg, $contato, $id)
    {
        $this->titulo = $titulo;
        $this->descricao = $descricao;
        $this->preco = $preco;
        $this->imagem = $imagem;
        $this->msg = $msg;
        $this->contato = $contato;
        $this->id = $id;
    }
}

$id = $_POST['id'];
$offset = $_POST['offset'];

$sql = "SELECT a.Titulo, a.Descricao, a.Preco ,f.NomeArqFoto,i.Codigo,i.Contato,i.Mensagem FROM Anuncio AS a JOIN Foto AS f ON f.CodAnuncio = a.Codigo JOIN Interesse AS i ON i.CodAnuncio = a.Codigo";
$sql .= " WHERE a.CodAnunciante = ?";
$sql .= " ORDER BY i.DataHora DESC LIMIT 6 OFFSET ?";
$stmt = $pdo->prepare($sql);
$stmt->execute([$id,$offset]);
if (!$stmt) {
    die('Erro na execução da consulta: ' . $pdo->errorInfo()[2]);
}

while ($row = $stmt->fetch()) {
    $titulo = htmlspecialchars($row['Titulo']);
    $descricao = htmlspecialchars($row['Descricao']);
    $preco = htmlspecialchars($row['Preco']);
    $imagem = htmlspecialchars($row['NomeArqFoto']);
    $id = strval($row['Codigo']);
    $contato = strval($row['Contato']);
    $msg = strval($row['Mensagem']);
    $objeto = new Anuncio($titulo, $descricao, $preco, $imagem, $msg, $contato, $id);
    $anuncios[] = $objeto;
}

header('Content-Type: application/json; charset=utf-8');
echo json_encode($anuncios);
