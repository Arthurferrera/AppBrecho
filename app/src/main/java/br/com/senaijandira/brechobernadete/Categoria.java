package br.com.senaijandira.brechobernadete;

public class Categoria {

    private int id;
    private String nome;
    private int totalPecas;


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
}
