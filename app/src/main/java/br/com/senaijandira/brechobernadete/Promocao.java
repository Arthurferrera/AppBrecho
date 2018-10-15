package br.com.senaijandira.brechobernadete;

public class Promocao {

    private int id;
    private Double precoNovo;
    private Double precoAntigo;
    private String nomeProduto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrecoNovo() {
        return precoNovo;
    }

    public void setPrecoNovo(Double precoNovo) {
        this.precoNovo = precoNovo;
    }

    public Double getPrecoAntigo() {
        return precoAntigo;
    }

    public void setPrecoAntigo(Double precoAntigo) {
        this.precoAntigo = precoAntigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
