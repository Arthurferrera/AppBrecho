package br.com.senaijandira.brechobernadete;

public class Promocao {

    private int id;
    private int precoNovo;
    private int precoAntigo;
    private String nomeProduto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrecoNovo() {
        return precoNovo;
    }

    public void setPrecoNovo(int precoNovo) {
        this.precoNovo = precoNovo;
    }

    public int getPrecoAntigo() {
        return precoAntigo;
    }

    public void setPrecoAntigo(int precoAntigo) {
        this.precoAntigo = precoAntigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
