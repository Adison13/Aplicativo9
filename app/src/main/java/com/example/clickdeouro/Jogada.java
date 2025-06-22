package com.example.clickdeouro;

public class Jogada {
    private int id;
    private String data;
    private int pontuacao;
    private int duracao;
    private int foiRecorde;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public int getPontuacao() { return pontuacao; }
    public void setPontuacao(int pontuacao) { this.pontuacao = pontuacao; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public int getFoiRecorde() { return foiRecorde; }
    public void setFoiRecorde(int foiRecorde) { this.foiRecorde = foiRecorde; }
}

