package br.com.senaijandira.brechobernadete.model;

public class Status {

    //declarando atributos da classe
    private int id;
    private String nome;


    //metodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
