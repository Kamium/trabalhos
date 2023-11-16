package com.trabalho.box2dtutorial.model;

public class Inimigo implements Observer {
    private int hp;
    private int atk;
    private int exp;
    @Override
    public void update(Personagem personagem) {
        hp -= personagem.getAtk();
        if(hp <= 0) {
            personagem.addExp(exp);
        }else{
            personagem.setHp(personagem.getHp()-atk);
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}