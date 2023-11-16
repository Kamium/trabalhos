package com.trabalho.box2dtutorial.model;

import java.util.Random;

public class Builder {

    private Personagem personagem;
    private Random random = new Random();
    private String nome;
    private String profissao;
    private int forca = 1;
    private int destreza = 1;
    private int constituicao = 1;
    private int inteligencia =1;
    private String cabelo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
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


    public void buildAleatorio() {
        forca = random.nextInt(100);
        destreza = random.nextInt(100);
        constituicao = random.nextInt(100);
    }

    public void infoPersonagem(){
        personagem.exibirInfo();
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public Personagem buildPersonagem(){
        personagem = new Personagem(nome,profissao,forca,destreza,constituicao,inteligencia,cabelo);
        return personagem;
    }
}
