<?php

function criaPagina($pageTitle,$pageImg,$pageDate,$pagePrice,$pageDescription,$pageCep,$pageCity,$pageState,$pageId)
{
    $template = file_get_contents("../base.html");

    // Substitui as partes variáveis pelos valores
    $template = str_replace("{PAGE_TITLE}", $pageTitle, $template);
    $template = str_replace("{PAGE_IMG}", $pageImg, $template);
    $template = str_replace("{PAGE_DATE}", $pageDate, $template);
    $template = str_replace("{PAGE_PRICE}", $pagePrice, $template);
    $template = str_replace("{PAGE_DESCRIPTION}", $pageDescription, $template);
    $template = str_replace("{PAGE_CEP}", $pageCep, $template);
    $template = str_replace("{PAGE_CITY}", $pageCity, $template);
    $template = str_replace("{PAGE_STATE}", $pageState, $template);
    $template = str_replace("{PAGE_ID}", $pageId, $template);

    // Define o nome da nova página
    $newPageName = $pageId . ".html";

    // Salva o conteúdo da nova página no servidor
    $file = fopen("../pages/" . $newPageName, "w");
    fwrite($file, $template);
    fclose($file);
}
