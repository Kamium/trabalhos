package com.trabalho.box2dtutorial.model;

public class Nivel implements Observer {

    @Override
    public void update(Personagem personagem) {
        while(personagem.getExp() >= personagem.getLvl()*100) {
            personagem.setExp(personagem.getExp()-personagem.getLvl()*100);
            personagem.setLvl(personagem.getLvl()+1);
        }
    }

}
