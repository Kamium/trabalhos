package com.trabalho.box2dtutorial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Personagem implements Subject, Serializable {
    private List<Observer> observers = new ArrayList<>();
    private List<Observer> nivel = new ArrayList<>();
    private String nome;
    private String profissao;
    private int forca;
    private int destreza;
    private int constituicao;
    private int hp;
    private int atk;
    private int inteligencia;
    private int exp = 0;
    private int lvl = 1;
    private String cabelo;

    public Personagem(String nome, String profissao,int forca, int destreza, int constituicao, int inteligencia, String cabelo) {
        this.nome = nome;
        this.profissao = profissao;
        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.cabelo = cabelo;
    }

    // Métodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getConstituicao() {
        return constituicao;
    }

    public void setConstituicao(int constituicao) {
        this.constituicao = constituicao;
    }

    public String getCabelo() {
        return cabelo;
    }

    public void setCabelo(String cabelo) {
        this.cabelo = cabelo;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void addExp(int exp){
        this.exp += exp;
        notifyLvlObservers();

    }
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void exibirInfo() {
        System.out.println("Nome: " + nome);
        System.out.println("Profissão: " + profissao);
        System.out.println("Constituição: " + constituicao);
        System.out.println("Destreza: " + destreza);
        System.out.println("Força: " + forca);
        System.out.println("Força: " + inteligencia);
    }

    @Override
    public String toString() {
        return nome + ", " + profissao + " | Vida: " + constituicao*14 + " | Ataque: " + forca*5 + " | Defesa: " + (constituicao+forca)*2;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void attachLvl(Observer observer) {
        nivel.add(observer);
    }

    @Override
    public void detachLvl(Observer observer) {
        nivel.remove(observer);
    }

    @Override
    public void notifyLvlObservers() {
        for (Observer observer : nivel) {
            observer.update(this);
        }
    }

    public void atacar() {
        notifyObservers();
    }
}
