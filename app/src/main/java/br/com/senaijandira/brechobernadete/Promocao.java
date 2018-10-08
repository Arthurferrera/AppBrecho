package br.com.senaijandira.brechobernadete;

public class Promocao {

    private int id;
    private Float precoNovo;
    private  Float precoAntigo;
    private String nomeProduto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getPrecoNovo() {
        return precoNovo;
    }

    public void setPrecoNovo(Float precoNovo) {
        this.precoNovo = precoNovo;
    }

    public Float getPrecoAntigo() {
        return precoAntigo;
    }

    public void setPrecoAntigo(Float precoAntigo) {
        this.precoAntigo = precoAntigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
