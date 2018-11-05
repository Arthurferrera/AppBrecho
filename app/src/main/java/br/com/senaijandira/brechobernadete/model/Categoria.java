package br.com.senaijandira.brechobernadete.model;

public class Categoria {

//    declarando os atributos da classe
    private int id;
    private String nome;
    private int totalPecas;


//    MÉTODOS GETTERS E SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toSring(){
        return nome;
    }

    public int getTotalPecas() {
        return totalPecas;
    }

    public void setTotalPecas(int totalPecas) {
        this.totalPecas = totalPecas;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
