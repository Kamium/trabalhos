window.onload = function(){

    //Atribuindo o div externo da busca avançada
    const modal = document.querySelector("#buscaAv");
    //Atribuindo o botão que irá ocultar a busca avançada
    const fechar = modal.querySelector(".fechar");

    //adicionando um evento de click ao botão de ocultar a busca avançada 
    fechar.addEventListener("click", function(){
        modal.style.display = 'none';
    });

    //atribuindo o botão para abrir a busca avançada e adicionando o evento
    const abrirModal = document.getElementById("abrirbuscaAv");
    abrirModal.addEventListener("click", function(){
        modal.style.display = 'block';
    })
}
