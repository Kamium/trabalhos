<?php

require "../php/conexaoMysql.php";
require "sessionVerification.php";
require "criaPagina.php";

session_start();
exitWhenNotLoggedIn();
$pdo = mysqlConnect();


// Função para verificar se um número é válido
function isValidNumber($number)
{
    return is_numeric($number) && $number > 0;
}


// Verifica se todos os campos esperados estão presentes
if (
    isset($_POST["titulo"]) &&
    isset($_POST["descricao"]) &&
    isset($_POST["preco"]) &&
    isset($_POST["cep"]) &&
    isset($_POST["bairro"]) &&
    isset($_POST["cidade"]) &&
    isset($_POST["estado"]) &&
    isset($_POST["categoria"]) &&
    isset($_FILES["foto"])
) {

    // Verifica se todos os campos estão preenchidos
    if (
        !empty($_POST["titulo"]) &&
        !empty($_POST["descricao"]) &&
        !empty($_POST["preco"]) &&
        !empty($_POST["cep"]) &&
        !empty($_POST["bairro"]) &&
        !empty($_POST["cidade"]) &&
        !empty($_POST["estado"]) &&
        !empty($_POST["categoria"]) &&
        $_FILES["foto"]["error"] === UPLOAD_ERR_OK
    ) {

        // Realize as verificações adicionais para cada campo, como validação de formato e restrições específicas
        // Validações adicionais
        $titulo = $_POST["titulo"];
        $descricao = $_POST["descricao"];
        $preco = $_POST["preco"];
        $cep = $_POST["cep"];
        $bairro = $_POST["bairro"];
        $cidade = $_POST["cidade"];
        $estado = $_POST["estado"];
        $categoria = $_POST["categoria"];
        $errors = [];

        // Validação do título
        if (strlen($titulo) < 3 || strlen($titulo) > 100) {
            $errors[] = "O título deve ter entre 3 e 100 caracteres.";
        }

        // Validação da descrição
        if (strlen($descricao) < 10 || strlen($descricao) > 200) {
            $errors[] = "A descrição deve ter entre 10 e 200 caracteres.";
        }

        // Validação do preço
        if (!isValidNumber($preco)) {
            $errors[] = "O preço deve ser um número válido maior que zero.";
        }

        // Validação do CEP
        if (!preg_match("/^\d{5}-\d{3}$/", $cep)) {
            $errors[] = "O CEP informado não é válido.";
        }

        // Validação da categoria
        $categoriasPermitidas = array("Veiculo", "Eletroeletronico", "Imovel", "Movel", "Vestuario", "Outros");
        if (!in_array($categoria, $categoriasPermitidas)) {
            $errors[] = "A categoria selecionada não é válida.";
        }

        // Verifica se houve algum erro de validação
        if (!empty($errors)) {
            // Exibe os erros ao usuário
            foreach ($errors as $error) {
                echo $error . "<br>";
            }
            http_response_code(400);
            exit(); // Interrompe a execução do script
        }
        // Verifique o tipo de arquivo da imagem
        $foto = $_FILES["foto"];
        $allowedExtensions = ["jpg", "jpeg", "png", "gif"];
        $fileExtension = strtolower(pathinfo($foto["name"], PATHINFO_EXTENSION));

        if (!in_array($fileExtension, $allowedExtensions)) {
            exit("Apenas arquivos JPG, JPEG, PNG e GIF são permitidos.");
        }
        $nomeArquivo = $_FILES["foto"]["name"]; // Obtém o nome original do arquivo
        $extensao = pathinfo($nomeArquivo, PATHINFO_EXTENSION); // Obtém a extensão do arquivo
        $userid = $_SESSION['id'];
        $novoNomeArquivo = "products/" . $userid . "_" . date("YmdHis") . "." . $extensao;

        $dataHora = date("Y-m-d H:i:s"); // Obtém a data e hora atual
        $pdo->beginTransaction();

        try {
            // Insere os dados na tabela Anuncio
            $sql = "INSERT INTO Anuncio (Titulo, Descricao, Preco, DataHora, CEP, Bairro, Cidade, Estado)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute([$titulo, $descricao, $preco, $dataHora, $cep, $bairro, $cidade, $estado]);

            $codAnuncio = $pdo->lastInsertId();

            $sql = "SELECT Codigo
                    FROM Categoria
                    WHERE Nome = ?";
            $stmt = $pdo->prepare($sql);
            $stmt->execute([$categoria]);
            $codCategoria = $stmt->fetchColumn();

            $sql = "UPDATE Anuncio 
                    SET CodCategoria = ?
                    WHERE Codigo = ?";
            $stmt = $pdo->prepare($sql);
            $stmt->execute([$codCategoria,$codAnuncio]);

            $sql = "UPDATE Anuncio 
                    SET CodAnunciante = ?
                    WHERE Codigo = ?";
            $stmt = $pdo->prepare($sql);
            $stmt->execute([$userid,$codAnuncio]);

            // Insere o nome do arquivo da foto na tabela Foto 
            $sql = "INSERT INTO Foto (CodAnuncio, NomeArqFoto) VALUES (?, ?)";
            $stmt = $pdo->prepare($sql);
            $stmt->execute([$codAnuncio, $novoNomeArquivo]);

            // Faz o upload do arquivo para o servidor
            $destino = "../assets/images/";
            move_uploaded_file($_FILES["foto"]["tmp_name"], $destino . $novoNomeArquivo);

            // Confirma a transação
            $pdo->commit();
            criaPagina($titulo,$novoNomeArquivo,$dataHora,$preco,$descricao,$cep,$cidade,$estado,$codAnuncio);

            

        } catch (Exception $e) {
            // Ocorreu um erro, desfaz a transação
            $pdo->rollback();
            http_response_code(400);

            echo "Erro ao cadastrar o anúncio: " . $e->getMessage();
            exit();
        }

        exit();
    } else {
        http_response_code(400);
        exit("Por favor, preencha todos os campos obrigatórios.");
    }
} else {
    $camposFaltando = array();

    if (!isset($_POST["titulo"])) {
        $camposFaltando[] = "titulo";
    }

    if (!isset($_POST["descricao"])) {
        $camposFaltando[] = "descricao";
    }

    if (!isset($_POST["preco"])) {
        $camposFaltando[] = "preco";
    }

    if (!isset($_POST["cep"])) {
        $camposFaltando[] = "cep";
    }

    if (!isset($_POST["bairro"])) {
        $camposFaltando[] = "bairro";
    }

    if (!isset($_POST["cidade"])) {
        $camposFaltando[] = "cidade";
    }

    if (!isset($_POST["estado"])) {
        $camposFaltando[] = "estado";
    }

    if (!isset($_POST["categoria"])) {
        $camposFaltando[] = "categoria";
    }

    if (!isset($_FILES["foto"])) {
        $camposFaltando[] = "foto";
    }
    http_response_code(400);
    exit("Alguns campos estão faltando no formulário: " . implode(", ", $camposFaltando));

}
